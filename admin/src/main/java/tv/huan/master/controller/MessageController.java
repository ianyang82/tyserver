package tv.huan.master.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.Message;
import tv.huan.master.service.IWeixinService;
@Controller
@RequestMapping(path = "/message")
public class MessageController extends BaseCRUDController<Message>{
	private static final Logger LOG=Logger.getLogger(MessageController.class);
	@Autowired
	private IWeixinService weixinService;
	@Value("${wx.autoreply}")
	private String autoreply;
	@RequestMapping("record")
	@ResponseBody
	public String record(HttpServletRequest request )throws Exception
	{
		String echostr=request.getParameter("echostr");  
	    if(StringUtils.isNoneEmpty(echostr)){
			if(weixinService.checkSignature(request.getParameter("signature"),request.getParameter("timestamp"),request.getParameter("nonce"))){  
				return echostr;  
	        }else{  
	            return "error";                                                                                                                                                                                                                                                                                                                                           
	        }
	    }else
	    {
			String msg=readStreamParameter(request.getInputStream());
			LOG.debug(msg);
			Message m=new Message();
			try {
				Document document = DocumentHelper.parseText(msg);
				Element root=document.getRootElement();  
	            m.setOpenid(root.elementText("FromUserName"));
	            m.setMsgid(root.elementText("MsgId"));
	            String type=root.elementText("MsgType");
	            if(type.equals("text"))
	            {
	            	m.setContent(root.elementText("Content"));
	            }else if(type.equals("image"))
	            {
	            	m.setContent(root.elementText("PicUrl"));
	            	m.setMediaId(root.elementText("MediaId"));
	            }else if(type.equals("event"))
	            {
	            	weixinService.sendMsg(m.getOpenid(), autoreply);
	            	return "success";
	            }
	        	m.setType(type);
	    		baseService.save(m);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return "success";
	}
	@RequestMapping("reply")
	@ResponseBody
	public Message reply(@RequestParam long id,String reply){
		Message m=baseService.findById(id);
		if(m!=null)
		{
			m.setReply(reply);
			m.setStatus(1);
			return baseService.save(m);
		}
		return null;
	}
}
