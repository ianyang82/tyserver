package tv.huan.master.vo;

import java.util.Date;

public class Ticket {
	private String ticket;
	private Date expire;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Date getExpire() {
		return expire;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
}
