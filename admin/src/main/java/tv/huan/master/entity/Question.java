package tv.huan.master.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;
@Entity
@Table(name="quetion")
@DynamicInsert
@DynamicUpdate
public class Question extends BaseEntity {
	public static final int TYPE_RKL=0;
	public static final int TYPE_SW=1;
	public static final int TYPE_SC=2;
	public static final int TYPE_GS=3;
	public static final int TYPE_XW=4;
	public static final int TYPE_JD=5;
	public static final int TYPE_OT=9;
	private String title;
	private String content;
	private String note;
	private Date starttime;
	private Date endtime;
	private Integer readcount;
	private Integer answercount;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	private String uniqueid;
	
	public String getUniqueid() {
		uniqueid="quetion"+getId().toString();
		return uniqueid;
	}
	public Integer getAnswercount() {
		return answercount;
	}
	public void setAnswercount(Integer answercount) {
		this.answercount = answercount;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Integer getReadcount() {
		return readcount;
	}
	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}
	
}
