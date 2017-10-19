package tv.huan.master.service;

import org.springframework.web.multipart.MultipartFile;

import tv.huan.master.entity.ClientUser;

public interface IWeixinService {
	public String getWxidByCode(String code);
	public String getTicket();
	public boolean checkSignature(String signature,String timestamp,String nonce);
	public String saveFile(String serverid );
	public String getAppid();
	public void autoLogoutUser();
	public ClientUser saveSessionUser(String k);
	public String SHA1Encode(String sourceString);
	public String refreshAccesstoken();
	public String refreshAccesstoken(boolean f);
	public ClientUser updateUser(String code,ClientUser us);
	public String saveFile(MultipartFile file,String serverid);
	public boolean sendMsg(String openid,String reply);
}
