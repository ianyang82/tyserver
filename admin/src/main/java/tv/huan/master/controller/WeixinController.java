package tv.huan.master.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tv.huan.master.entity.ClientUser;
import tv.huan.master.service.IWeixinService;
import tv.huan.master.service.ReplyInfoService;

@Controller
@RequestMapping("/wx")
public class WeixinController {
	private static final Logger LOG=LoggerFactory.getLogger(WeixinController.class);
	@Autowired
	IWeixinService weixinService;
	@Autowired
	ReplyInfoService replyInfoService;
	@Value("${baseurl.path}")
	String baseurl;
	@RequestMapping("questionview")
	public String question(HttpServletRequest request,Model model)
	{
		String t=weixinService.getTicket();
		String noncestr=UUID.randomUUID().toString();
		String url=baseurl+"/wx/question"+(StringUtils.isBlank(request.getQueryString())?"":("?"+request.getQueryString()));
		long timestamp=System.currentTimeMillis()/1000;
		model.addAttribute("appid", weixinService.getAppid());
		model.addAttribute("jsapi_ticket", t);
		model.addAttribute("noncestr", noncestr);
		model.addAttribute("timestamp", timestamp);
		LOG.info(url);
		model.addAttribute("signature", weixinService.SHA1Encode("jsapi_ticket=" + t + "&noncestr=" + noncestr +"&timestamp=" + timestamp +"&url=" + url));
		if(StringUtils.isNotEmpty(request.getParameter("code")))
			model.addAttribute("user",weixinService.saveSessionUser(request.getParameter("code")));
		return "/wx/question";
	}
	@RequestMapping("getaccesstoken")
	@ResponseBody
	public String getaccesstoken(){
		return weixinService.refreshAccesstoken(); 
	}
	@RequestMapping("question")
	public String question(Model model)
	{
		return "/wx/questionlist";
	}

	@RequestMapping("callback")
	@ResponseBody
	public String callback(HttpServletRequest request)
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
	    	String postStr;
			try {
				postStr = this.readStreamParameter(request.getInputStream());
		    	return replyInfoService.reply(postStr);
			} catch (IOException e) {
				LOG.error(e.getMessage(),e);
			}
	    }
	    return "success";
	}
	@RequestMapping("updateuser")
	@ResponseBody
	public ClientUser updateUserinfo(@RequestParam String code,@RequestParam(required=false) String name,@RequestParam(required=false) String nick,@RequestParam(required=false) String headurl,@RequestParam(required=false) String tel,@RequestParam(required=false,defaultValue="0") int type)
	{
		ClientUser cu=new ClientUser();
		if(headurl!=null)
		cu.setHeadurl(headurl);
		if(name!=null)
		cu.setName(name);
		if(tel!=null)
		cu.setTel(tel);
		if(type!=0)
			cu.setType(type);
		if(nick!=null)
			cu.setNickname(nick);
		cu=weixinService.updateUser(code, cu);
		return cu;
	}
	
	 private String readStreamParameter(ServletInputStream in){  
	        StringBuilder buffer = new StringBuilder();  
	        BufferedReader reader=null;  
	        try{  
	            reader = new BufferedReader(new InputStreamReader(in));  
	            String line=null;  
	            while((line = reader.readLine())!=null){  
	                buffer.append(line);  
	            }  
	        }catch(Exception e){  
	        	LOG.error(e.getMessage(),e);
	        }finally{  
	            if(null!=reader){  
	                try {  
	                    reader.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	        return buffer.toString();  
	    }
}
