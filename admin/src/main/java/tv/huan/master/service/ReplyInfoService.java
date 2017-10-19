package tv.huan.master.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.NewsItem;
import tv.huan.master.entity.ReplyInfo;
import tv.huan.master.entity.ReplyMessage;
import tv.huan.master.util.HttpClientUtil;
@Service
public class ReplyInfoService extends BaseService<ReplyInfo>{
	private static final Logger LOG=LoggerFactory.getLogger(ReplyInfoService.class);
	@Value("${wx.username}")
	private String username;
	@Autowired
	IWeixinService weixinService;
	ClientUserService clientUserService;
	@Autowired
	ReplyMessageService replyMessageService;
	public String callbackMsg(String wxid,ReplyInfo msg)
	{
		Document doc=DocumentHelper.createDocument();
		Element rootel=doc.addElement("xml");
		Element el=null;
		rootel.addElement("ToUserName").setText(wxid);
		rootel.addElement("FromUserName").setText(username);
		rootel.addElement("CreateTime").setText(Long.toString(System.currentTimeMillis()/1000));
		rootel.addElement("MsgType").setText(msg.getType());
		switch (msg.getType()) {
		case "text":
			rootel.addElement("Content").setText(msg.getNews_info().get(0).getContent());
			break;
		case "img":
			rootel.addElement("Image").addElement("MediaId").setText(msg.getNews_info().get(0).getContent());
			break;
		case "voice":
			rootel.addElement("Voice").addElement("MediaId").setText(msg.getNews_info().get(0).getContent());
			break;
		case "video":
			el=rootel.addElement("Video");
			el.addElement("MediaId").setText(msg.getNews_info().get(0).getContent());
			el.addElement("Title").setText(msg.getNews_info().get(0).getTitle());
			el.addElement("Description").setText(msg.getNews_info().get(0).getDigest());
			break;
//		case "music":
//			el=rootel.addElement("Music");
//			el.addElement("MusicUrl").setText(msg.getContent());
//			el.addElement("Title").setText(msg.getTitle());
//			el.addElement("Description").setText(msg.getDescription());
//			break;
		case "news":
			rootel.addElement("ArticleCount").setText(msg.getNews_info().size()+"");
			el=rootel.addElement("Articles");
			if("reply_all".equals(msg.getReply_mode()))
			for(NewsItem n: msg.getNews_info())
			{
				Element item= el.addElement("item");
				item.addElement("Title").setText(n.getTitle());
				item.addElement("Description").setText(n.getDigest());
				item.addElement("Url").setText(n.getContent_url());
				item.addElement("PicUrl").setText(n.getContent_url());
			}else{
				NewsItem n=msg.getNews_info().get(new Random().nextInt(msg.getNews_info().size()));
				Element item= el.addElement("item");
				item.addElement("Title").setText(n.getTitle());
				item.addElement("Description").setText(n.getDigest());
				item.addElement("Url").setText(n.getContent_url());
				item.addElement("PicUrl").setText(n.getContent_url());
			}
			break;
		}
		return doc.asXML();
	}
	public String reply(String msg)
	{
		LOG.info(msg);
		try {
			Document document = DocumentHelper.parseText(msg);
			Element root=document.getRootElement();  
            String wxid = root.elementText("FromUserName");
            if(root.elementText("MsgType").equals("text"))
            {
            	String r= findReply( wxid,root.elementText("Content"));
            	LOG.info(r);
            	return r;
            }else if(root.elementText("MsgType").equals("subscribe"))
            {
            	ReplyInfo info= findOneByRulename(ReplyInfo.RULE_NAME_ADD);
            	if(info!=null)
            		return callbackMsg(wxid,info);
            }
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	public ReplyInfo findOneByRulename(String rule_name)
	{
		DetachedCriteria crit = DetachedCriteria.forClass(ReplyInfo.class);
		crit.add(Restrictions.eq("rule_name", rule_name));
		return (ReplyInfo)baseDAO.findUnique(crit);
	}
	
	public String findReply(String wxid,String txt)
	{
		List<ReplyMessage> ls=replyMessageService.findAll();
		ReplyInfo automes=findOneByRulename(ReplyInfo.RULE_NAME_DEF);
		for(ReplyMessage rm:ls)
		{
			if((rm.getMatch_mode().equals("contain")&&txt.indexOf(rm.getContent())>0)
					||txt.equals(rm.getContent()))
			{
				ReplyInfo rs= rm.getReplyinfo();
				if(rs!=null)
					return callbackMsg(wxid,rs);
			}
		}
		if(automes!=null)
			return callbackMsg(wxid,automes);
		return "";
	}
	 public JSONObject getServer()
	 {
	    	String url="https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token="+weixinService.refreshAccesstoken();
	    	try {
				JSONObject jo= JSON.parseObject(HttpClientUtil.doGet(url, null)) ;
				LOG.info(jo.toJSONString());
				return jo;
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
	    	return null;
	}
	public void save(JSONObject json)
	{
		JSONObject ajson=json.getJSONObject(ReplyInfo.RULE_NAME_ADD);
		JSONObject djson=json.getJSONObject(ReplyInfo.RULE_NAME_DEF);
		JSONObject kjson=json.getJSONObject(ReplyInfo.RULE_NAME_KEY);
		if(ajson!=null)
		{
			ReplyInfo info=new ReplyInfo();
			info.setRule_name(ReplyInfo.RULE_NAME_ADD);
			info.setType(ajson.getString("type"));
			NewsItem rm=new NewsItem();
			rm.setContent(ajson.getString("content"));
			info.setNews_info(new ArrayList<NewsItem>());
			info.getNews_info().add(rm);
			save(info);
		}
		if(djson!=null)
		{
			ReplyInfo info=new ReplyInfo();
			info.setRule_name(ReplyInfo.RULE_NAME_DEF);
			info.setType(djson.getString("type"));
			NewsItem rm=new NewsItem();
			rm.setContent(djson.getString("content"));
			info.setNews_info(new ArrayList<NewsItem>());
			info.getNews_info().add(rm);
			save(info);
		}
		if(kjson!=null)
		{
			for(int i=0;i<kjson.getJSONArray("list").size();i++)
			{
				JSONObject rj= kjson.getJSONArray("list").getJSONObject(i);
				ReplyInfo info=new ReplyInfo();
				info.setRule_name(rj.getString("rule_name"));
				info.setReply_mode(rj.getString("reply_mode"));
				info.setKeywords_info(JSON.parseArray(rj.getJSONArray("keyword_list_info").toJSONString(), ReplyMessage.class));
				info.setNews_info(new ArrayList<>());
				for(int j=0;j<rj.getJSONArray("reply_list_info").size();j++)
				{
					JSONObject joo=rj.getJSONArray("reply_list_info").getJSONObject(j);
					info.setType(joo.getString("type"));
					if(joo.getJSONObject("news_info")==null)
						info.getNews_info().add(JSON.parseObject(joo.toJSONString(), NewsItem.class));
					else
						info.getNews_info().addAll(JSON.parseArray(joo.getJSONObject("news_info").getJSONArray("list").toJSONString(), NewsItem.class));
				}
				save(info);
			}
		}
		
	}
	public void updateServerToLocal()
	{
		baseDAO.delete(baseClass());
    	save(getServer());
	}
}
