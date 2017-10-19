package tv.huan.master.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;

import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.WeixinMenu;
import tv.huan.master.service.impl.WeixinServiceImpl;
import tv.huan.master.util.HttpClientUtil;

/**
 * Created with IntelliJ IDEA.
 * WeixinMenu: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/9
 * Time: 11:33
 * To change this template use File | Settings | File Templates
 */
@Service
public class WeixinMenuService extends BaseService<WeixinMenu> {
	private static final Logger LOG=LoggerFactory.getLogger(WeixinServiceImpl.class);
    @Autowired
    BaseDAO<WeixinMenu> baseDAO;
    @Autowired
    IWeixinService weixinService;

    public List<WeixinMenu> findToSelect() {
        DetachedCriteria crit = DetachedCriteria.forClass(WeixinMenu.class);
        crit.add(Restrictions.eq("delFlag", "0")).addOrder(Order.asc("onum")).addOrder(Order.asc("id"));
        return baseDAO.find(crit);
    }
    
    public void deleServer()
    {
    	String url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+weixinService.refreshAccesstoken();
		DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpGet get = new HttpGet(url);
        HttpResponse response;
		try {
			response = client.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
				JSONObject jo=JSON.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
				LOG.info(jo.toJSONString());
				if(jo.getIntValue("errcode")!=0)
				{
					if(jo.getIntValue("errcode")==40001||jo.getIntValue("errcode")==40014||jo.getIntValue("errcode")==42001)
					{
						weixinService.refreshAccesstoken(true);
						deleServer();
					}
				}
			}
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage(),e);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}
    }
    public void createServer(String menustr)
    {
    	String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+weixinService.refreshAccesstoken();
    	try {
			JSONObject jo= JSON.parseObject(HttpClientUtil.doPostByJson(url, menustr)) ;
			LOG.info(jo.toJSONString());
			if(jo.getIntValue("errcode")!=0)
			{
				if(jo.getIntValue("errcode")==40001||jo.getIntValue("errcode")==40014||jo.getIntValue("errcode")==42001)
				{
					weixinService.refreshAccesstoken(true);
					createServer(menustr);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
    }
    public JSONObject getServer()
    {
    	String url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+weixinService.refreshAccesstoken(true);
    	try {
			JSONObject jo= JSON.parseObject(HttpClientUtil.doGet(url, null)) ;
//        	String s="{\"menu\": {\"button\":[{\"name\":\"海淘值得买\",\"sub_button\":[],\"type\":\"click\",\"key\":\"haitaobuy\"},{\"name\":\"公告\",\"sub_button\":[{\"name\":\"普通公告\",\"sub_button\":[],\"type\":\"view\",\"url\":\"http://kkhaitao.com/content_102.html#cv\"},{\"name\":\"价格方案\",\"sub_button\":[],\"type\":\"view\",\"url\":\"http://kkhaitao.com/content_87.html#cv\"},{\"name\":\"客户须知\",\"sub_button\":[],\"type\":\"view\",\"url\":\"http://kkhaitao.com/content_20.html#cv\"},{\"name\":\"留言咨询\",\"sub_button\":[],\"type\":\"click\",\"key\":\"helpq\"}]},{\"name\":\"用户中心\",\"sub_button\":[{\"name\":\"我的KK海淘\",\"sub_button\":[],\"type\":\"view\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1861894baa1a62d3&redirect_uri=http%3a%2f%2fkkhaitao.com%2fwx%2fbind.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"}]}]}}";
//    		JSONObject jo= JSON.parseObject(s);
			LOG.info(jo.toJSONString());
			return jo;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
    	return null;
    }
    public void save(JSONArray ja,Long praentid)
    {
    	for(int i=0;i<ja.size();i++)
    	{
    		JSONObject joo=ja.getJSONObject(i);
	    	WeixinMenu wm=new WeixinMenu();
	    	wm.setType(joo.getString("type"));
	    	wm.setName(joo.getString("name"));
	    	if(wm.getType()!=null)
	    	{
		    	switch (wm.getType()) {
				case "click":
					wm.setUrl(joo.getString("key"));
					break;
				case "view":
					wm.setUrl(joo.getString("url"));
					break;
				case "media_id":
					wm.setUrl(joo.getString("media_id"));
					break;
				case "view_limited":
					wm.setUrl(joo.getString("media_id"));
					break;
				case "pic_sysphoto":
					wm.setUrl(joo.getString("key"));
					break;
				case "pic_photo_or_album":
					wm.setUrl(joo.getString("key"));
					break;
				case "pic_weixin":
					wm.setUrl(joo.getString("key"));
					break;
				case "location_select":
					wm.setUrl(joo.getString("key"));
					break;
				default:
					break;
				}
	    	}
	    	wm.setParentId(praentid);
	    	baseDAO.save(wm);
	    	save(joo.getJSONArray("sub_button"),wm.getId());
    	}
    	
    }
    public void updateServerToLocal()
    {
    	baseDAO.delete(baseClass());
    	save(getServer().getJSONObject("menu").getJSONArray("button"),null);
    }
    public void getLocalWeixinMenu(List<WeixinMenu> list,Long pid,List<Object> ml)
    {
    	for(int i=0;i<list.size();)
    	{
    		if(pid==null?list.get(i).getParentId()==null:pid.equals(list.get(i).getParentId()))
    		{
    			Long id=list.get(i).getId();
    			Map<String,Object> map=new HashMap<String,Object>();
    			map.put("name", list.get(i).getName());
    			if(list.get(i).getType()!=null){
    			map.put("type", list.get(i).getType());
    			switch (list.get(i).getType()) {
    			case "click":
    				map.put("key",list.get(i).getUrl());
    				break;
    			case "view":
    				map.put("url",list.get(i).getUrl());
    				break;
    			case "media_id":
    				map.put("media_id",list.get(i).getUrl());
    				break;
    			case "view_limited":
    				map.put("media_id",list.get(i).getUrl());
    				break;
    			case "pic_sysphoto":
    				map.put("key",list.get(i).getUrl());
    				break;
    			case "pic_photo_or_album":
    				map.put("key",list.get(i).getUrl());
    				break;
    			case "pic_weixin":
    				map.put("key",list.get(i).getUrl());
    				break;
    			case "location_select":
    				map.put("key",list.get(i).getUrl());
    				break;
    			default:
    				break;
    			}
    			}
    			List<Object> ls=new ArrayList<Object>();
    			list.remove(i);
    			getLocalWeixinMenu(list,id,ls);
    			map.put("sub_button", ls);
    			ml.add(map);
    		}else
    			i++;
    	}
    }
    public List<Object> getLocalWeixinMenu()
    {
    	List<WeixinMenu> list=findToSelect();
    	List<Object> lm =new ArrayList<Object>();
    	getLocalWeixinMenu(list,null,lm);
    	return lm;
    }
    public String synctoserver(String json){
    	LOG.debug(json);
    	try {
    		JSONObject jo=JSON.parseObject(HttpClientUtil.doPostByJson("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+weixinService.refreshAccesstoken(), json));
    		if(jo.getIntValue("errcode")==40001||jo.getIntValue("errcode")==40014||jo.getIntValue("errcode")==42001)
			{
    			weixinService.refreshAccesstoken(true);
				return synctoserver(json);
			}else
				return jo.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    public static void main(String[] args) {
//    	List<Integer> ls=new ArrayList<Integer>();
//    	for(int i=0;i<200;i++)
//    		ls.add(i);
//    	for(int j=0;j<ls.size();)
//    	{
//    		if(ls.get(j)%7==0)
//    		{
//    			System.out.println(ls.get(j));
//    			ls.remove(j);
//    		}else
//    			j++;
//    		
//    	}
//    	System.out.println(ls.size());
    	String s="{\"button\":[{\"name\":\"海淘值得买\",\"sub_button\":[],\"type\":\"click\",\"key\":\"haitaobuy\"},{\"name\":\"公告\",\"sub_button\":[{\"name\":\"普通公告\",\"sub_button\":[],\"type\":\"view\",\"url\":\"http://kkhaitao.com/content_102.html#cv\"},{\"name\":\"价格方案\",\"sub_button\":[],\"type\":\"view\",\"url\":\"http://kkhaitao.com/content_87.html#cv\"},{\"name\":\"客户须知\",\"sub_button\":[],\"type\":\"view\",\"url\":\"http://kkhaitao.com/content_20.html#cv\"},{\"name\":\"留言咨询\",\"sub_button\":[],\"type\":\"click\",\"key\":\"helpq\"}]},{\"name\":\"用户中心\",\"sub_button\":[{\"name\":\"我的KK海淘\",\"sub_button\":[],\"type\":\"view\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1861894baa1a62d3&redirect_uri=http%3a%2f%2fkkhaitao.com%2fwx%2fbind.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"}]}]}";
    	System.out.println(s);
	}
}
