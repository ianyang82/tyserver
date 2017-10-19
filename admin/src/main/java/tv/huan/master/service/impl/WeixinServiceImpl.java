package tv.huan.master.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import tv.huan.master.entity.ClientUser;
import tv.huan.master.exception.AppException;
import tv.huan.master.service.ClientUserService;
import tv.huan.master.service.IWeixinService;
import tv.huan.master.util.HttpClientUtil;
import tv.huan.master.utils.FileParseUtil;
import tv.huan.master.vo.Ticket;

@Service("weixinService")
public class WeixinServiceImpl implements IWeixinService{
	private static final Logger LOG=Logger.getLogger(WeixinServiceImpl.class);
	@Value("${wx.appid}")
	private String appid;
	@Value("${wx.appsecret}")
	private String appsecret;
	@Value("${converter.path}")
	private String converterpath;
	private Map<String,ClientUser> sessionUser=new ConcurrentHashMap<String, ClientUser>();
	private String accesstoken=null;
	private Ticket ticket;
	@Value("${file.dir.path}")
	private String dirpath;
	@Value("${file.url.path}")
	private String dirurl;
	private final String TOKEN="weixin";
	@Autowired
	private ClientUserService clientUserService;


	public String refreshAccesstoken()
	{
		return refreshAccesstoken(false);
	}
	public String refreshAccesstoken(boolean f)
	{
		if(f)
			accesstoken=null;
		if(accesstoken==null||accesstoken.trim().length()==0)
		{
			String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
			try {
				JSONObject jo=JSON.parseObject(HttpClientUtil.doGet(url, null));
				accesstoken=jo.getString("access_token");
			} catch (JSONException e) {
				LOG.error(e);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		LOG.debug("accesstoken::::::::::::::::::"+accesstoken);
		return accesstoken;
	}

	public String getWxidByCode(String code) {
		String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
	        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
	        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
	        HttpGet get = new HttpGet(url);
	        HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
				JSONObject jo=JSON.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
				LOG.info(jo.toJSONString());
				return jo.getString("openid");
			}
		} catch (IOException e) {
			LOG.error(e);
		} catch (JSONException e) {
			LOG.error(e);
		}
		return null;
	}
	public ClientUser getwxuser(String openid)
	{
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+refreshAccesstoken()+"&openid="+openid+"&lang=zh_CN";
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
						accesstoken=null;
						return getwxuser(openid);
					}
					return null;
				}else{
				ClientUser user=new ClientUser();
				user.setNickname(jo.getString("nickname"));
				user.setWxid(jo.getString("openid"));
				user.setHeadurl(jo.getString("headimgurl").substring(0,jo.getString("headimgurl").length()-1)+64);
				return user;
				}
			}
		} catch (ClientProtocolException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		return null;
	}
	public String getTicket()
	{
		if(ticket!=null&&ticket.getExpire().after(new Date()))
			return ticket.getTicket();
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+refreshAccesstoken()+"&type=jsapi";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
	        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
	        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
	        HttpGet get = new HttpGet(url);
	        HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
			JSONObject jo=JSON.parseObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
			if(jo.getIntValue("errcode")!=0)
			{
				if(jo.getIntValue("errcode")==40001||jo.getIntValue("errcode")==40014||jo.getIntValue("errcode")==42001)
				{
					accesstoken=null;
					return getTicket();
				}
			}else
			{
				ticket=new Ticket();
				Calendar c=Calendar.getInstance(); 
				c.add(Calendar.SECOND, jo.getIntValue("expires_in")-60);
				ticket.setExpire(c.getTime());
				ticket.setTicket(jo.getString("ticket"));
				LOG.info("ticket:"+ticket.getTicket());
				return ticket.getTicket();
			}
			
			}
		} catch (IOException e) {
			LOG.error(e);
		} catch (JSONException e) {
			LOG.error(e);
		}
		return null;
	}
	
	    //微信接口验证   
	    public boolean checkSignature(String signature,String timestamp,String nonce){
	        String token=TOKEN;  
	        String[] tmpArr={token,timestamp,nonce};  
	        Arrays.sort(tmpArr);  
	        String tmpStr=this.ArrayToString(tmpArr);  
	        tmpStr=this.SHA1Encode(tmpStr);  
	        if(tmpStr.equalsIgnoreCase(signature)){  
	            return true;  
	        }else{  
	            return false;  
	        }  
	    }  
	    //向请求端发送返回数据   
	  
	    //数组转字符串   
	    private String ArrayToString(String [] arr){  
	        StringBuffer bf = new StringBuffer();  
	        for(int i = 0; i < arr.length; i++){  
	         bf.append(arr[i]);  
	        }  
	        return bf.toString();  
	    }  
	    //sha1加密   
	    public String SHA1Encode(String sourceString) {  
	        String resultString = null;  
	        try {  
	           resultString = new String(sourceString);  
	           MessageDigest md = MessageDigest.getInstance("SHA-1");  
	           resultString = byte2hexString(md.digest(resultString.getBytes()));  
	        } catch (Exception ex) {  
	        }  
	        return resultString;  
	    }  
	    private final String byte2hexString(byte[] bytes) {  
	        StringBuffer buf = new StringBuffer(bytes.length * 2);  
	        for (int i = 0; i < bytes.length; i++) {  
	            if (((int) bytes[i] & 0xff) < 0x10) {  
	                buf.append("0");  
	            }  
	            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
	        }  
	        return buf.toString().toUpperCase();  
	    }
	    private String getFileexpandedName(String contentType) {
	    	LOG.info(contentType);
	        String fileEndWitsh = "";
	        if ("image/gif".equals(contentType))
		          fileEndWitsh = ".gif";
	        else if ("image/png".equals(contentType))
	          fileEndWitsh = ".png";
	        else if ("image/jpeg".equals(contentType)||"image/jpg".equals(contentType))
		          fileEndWitsh = ".jpg";
	        else if ("audio/mpeg".equals(contentType))
	          fileEndWitsh = ".mp3";
	        else if ("audio/amr".equals(contentType))
	          fileEndWitsh = ".amr";
	        else if ("video/mp4".equals(contentType))
	          fileEndWitsh = ".mp4";
	        else if ("video/mpeg4".equals(contentType))
	          fileEndWitsh = ".mp4";
	        else if ("audio/silk".equals(contentType))
		          fileEndWitsh = ".slk";
	        return fileEndWitsh;
	      }
	    public String saveFile(MultipartFile file,String serverid)
	    {
	    	try{ 
	    		return saveFile(file.getInputStream(),serverid,file.getContentType());
	    	}catch (IOException e) {
	    		LOG.error(e);
			}
	    	return null;
	    }
	    public String saveFile(InputStream in ,String serverid,String type)
	    {
	    	String savePath =dirpath+File.separator;
	    	if (!savePath.endsWith("/")) {
	    	        savePath += "/";
	    	      }
	    	String fileExt = getFileexpandedName(type);
	    	String  filePath =  serverid + fileExt;
	    	if(fileExt.trim().length()>0)
	    	{
	  	      	byte[] buf = new byte[8096];
	  	      	int size=0;
	  	      	File f=new File(savePath +filePath);
	  	      	if(!f.getParentFile().exists())
	  	      		f.getParentFile().mkdirs();
				try(FileOutputStream fos = new FileOutputStream(f)) {
					while ((size = in.read(buf)) != -1)
		  	      		fos.write(buf, 0, size);
				} catch (FileNotFoundException e) {
					LOG.error(e);
				} catch (IOException e1) {
					LOG.error(e1);
				}finally {
					try {
						if(in!=null)
							in.close();
					} catch (IOException e) {
						LOG.error(e);
					}
				}
				LOG.info("下载媒体文件成功，filePath=" + filePath);
	    	}else
	    	{
	    		BufferedReader reader =new BufferedReader(new InputStreamReader(in));
	    		fileExt=".webm";
	    		filePath =  serverid + fileExt;
	    		char[] buf = new char[8096];
	    		File f=new File(savePath +filePath);
	  	      	if(!f.getParentFile().exists())
	  	      		f.getParentFile().mkdirs();
	    		try(FileOutputStream fos = new FileOutputStream(f)) {
	    			StringBuilder sb=new StringBuilder();
	    			int len=0;
		    		while((len=reader.read(buf))!=-1)
		    		{
		    			sb.append(buf,0,len);
		    		}
		    		fos.write(Base64.getDecoder().decode(sb.substring(sb.indexOf("base64,")+7)));
				} catch (FileNotFoundException e) {
					LOG.error(e);
				} catch (IOException e1) {
					LOG.error(e1);
				}finally {
					try {
						if(in!=null)
							in.close();
					} catch (IOException e) {
						LOG.error(e);
					}
				}
	    			    		
	    	}
  	      	if(fileExt.equalsIgnoreCase(".amr")||fileExt.equalsIgnoreCase(".slk")||fileExt.equalsIgnoreCase(".webm"))
  	      	{
  	      		FileParseUtil.encodToMP3(converterpath,new File(savePath +filePath));
  	      		filePath=serverid +".mp3";
  	     	}
  	      return dirurl +filePath;
	    }
	    public String saveFile(String serverid )
	    {
	    	String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+refreshAccesstoken()+"&media_id="+serverid;
	    	HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) new URL(requestUrl).openConnection();
		    	conn.setDoInput(true);
		    	conn.setRequestMethod("GET");
		    	return saveFile(conn.getInputStream(),serverid,conn.getHeaderField("Content-Type"));
			} catch (MalformedURLException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}
	    	return null;    
	    }
		public String getAppid() {
			// TODO Auto-generated method stub
			return appid;
		}
		@Scheduled(cron="0 30 1 * * ?")
		public void autoLogoutUser()
		{
			Set<String> ks=sessionUser.keySet();
			for(String k:ks)
			{
				if(System.currentTimeMillis()-sessionUser.get(k).getLoginTime().getTime()>24*60*60*1000l)
				{
					sessionUser.remove(k);
				}
			}
		}
		public ClientUser saveSessionUser(String code)
		{
			ClientUser u=sessionUser.get(code);
			if(u==null)
			{
				String wxid=getWxidByCode(code);
				if(wxid==null)
					throw new AppException(401,"微信用户授权失败，请重新登录");
				u= clientUserService.findOneByWxid(wxid);
				if(u==null)
				{
					u=new ClientUser();
					u.setWxid(wxid);
					u.setNickname("微信网友");
					u.setHeadurl("https://mmbiz.qpic.cn/mmbiz_png/9ozSA9lZC5gnjEpvdJ8DjWnBoIAHBIGQEd7EmyDlxzibjYqN856E0IBSshrXbtUiaIBaSDUvx17Fl0W9vbcUqNRw/0?wx_fmt=png");
					u.setType(ClientUser.TYPE_USER);
					u.setLoginTime(new Date());
					clientUserService.save(u);
				}
				sessionUser.put(code, u);
				/*u=getwxuser(wxid);
				if(u!=null)
				{
					ClientUser tu=clientUserService.findOneByWxid(wxid);
					if(tu!=null)
					{
						tu.setHeadurl(u.getHeadurl());
						tu.setNickname(u.getNickname());
						tu.setLoginTime(new Date());
						tu=clientUserService.save(tu);
						sessionUser.put(code, tu);
						return tu;
					}else
					{
						u.setType(ClientUser.TYPE_USER);
						u.setLoginTime(new Date());
						clientUserService.save(u);
						sessionUser.put(code, u);
					}
				}*/
			}
			return u;
		}
		public ClientUser updateUser(String code,ClientUser us)
		{
			ClientUser u= saveSessionUser(code);
			if(StringUtils.isNotEmpty(us.getHeadurl()))
			u.setHeadurl(us.getHeadurl());
			u.setLoginTime(new Date());
			if(StringUtils.isNotEmpty(us.getNickname()))
				u.setNickname(us.getNickname());
			if(StringUtils.isNotEmpty(us.getTel()))
				u.setTel(us.getTel());
			if(StringUtils.isNotEmpty(us.getName()))
				u.setName(us.getName());
			if(us.getType()!=null&&us.getType()!=0)
				u.setType(us.getType());
			clientUserService.save(u);
			sessionUser.put(code, u);
			return u;
		}
		public boolean sendMsg(String openid,String reply)
		{
			JSONObject json=new JSONObject();
			json.put("touser", openid);
			json.put("text", new JSONObject().fluentPut("content", reply));
			json.put("msgtype", "text");
			LOG.info(json.toJSONString());
			try {
				LOG.info(HttpClientUtil.doPostByJson("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+refreshAccesstoken(), json.toJSONString()));
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
}
