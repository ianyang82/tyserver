package tv.huan.master.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tv.huan.master.common.entity.BaseEntity;
@Entity
@Table(name="replymessage")
public class ReplyMessage extends BaseEntity{
	private String match_mode;
	private String name;
	private String content;
	@ManyToOne()
	@JoinColumn(name = "rid", referencedColumnName="id",nullable=true)
	private ReplyInfo replyinfo;
	public String getMatch_mode() {
		return match_mode;
	}
	public void setMatch_mode(String match_mode) {
		this.match_mode = match_mode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ReplyInfo getReplyinfo() {
		return replyinfo;
	}
	public void setReplyinfo(ReplyInfo replyinfo) {
		this.replyinfo = replyinfo;
	}
	
}
