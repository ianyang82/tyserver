package tv.huan.master.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;

@Entity
@Table(name = "answer")
@DynamicInsert
@DynamicUpdate
public class Answer extends BaseEntity {
	private Integer type;
	private String vurl;
	private String content;
	private Integer lcount;
	private String usernickname;
	private String headurl;
	private Long userid;
	private Long questionid;
	private Boolean top; 
	@Transient
	private boolean like;
	@Transient
	private Integer likes;
	@Transient
	private List<Comment> comments;
	@Transient
	private String uniqueid;
	private Integer palycount;
	private Integer status=0;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPalycount() {
		return palycount;
	}
	public void setPalycount(Integer palycount) {
		this.palycount = palycount;
	}
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getTop() {
		return top;
	}
	public void setTop(Boolean top) {
		this.top = top;
	}
	public String getUniqueid() {
		uniqueid="answer"+getId().toString();
		return uniqueid;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getVurl() {
		return vurl;
	}
	public void setVurl(String vurl) {
		this.vurl = vurl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getLcount() {
		return lcount;
	}
	public void setLcount(Integer lcount) {
		this.lcount = lcount;
	}
	public String getUsernickname() {
		return usernickname;
	}
	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Integer getLikes() {
		return getLcount();
	}
	public boolean isLike() {
		return like;
	}
	public void setLike(boolean like) {
		this.like = like;
	}
	
}
