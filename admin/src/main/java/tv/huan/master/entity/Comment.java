package tv.huan.master.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;

@Entity
@Table(name="comment")
@DynamicInsert
@DynamicUpdate
public class Comment extends BaseEntity{
	private String usernickname;
	private String headurl;
	private Long userid;
	private Long answerid;
	private String content;
	private String vurl;
	private Integer type;
	@Transient
	private String uniqueid;
	
	public String getUniqueid() {
		uniqueid="comment"+getId().toString();
		return uniqueid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsernickname() {
		return usernickname;
	}
	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getAnswerid() {
		return answerid;
	}
	public void setAnswerid(Long answerid) {
		this.answerid = answerid;
	}
	public String getVurl() {
		return vurl;
	}
	public void setVurl(String vurl) {
		this.vurl = vurl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
