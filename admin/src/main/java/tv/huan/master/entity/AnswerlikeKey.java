package tv.huan.master.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AnswerlikeKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227529990595645410L;
	private long answerid;
	private long userid;
	
	public long getAnswerid() {
		return answerid;
	}
	public void setAnswerid(long answerid) {
		this.answerid = answerid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public AnswerlikeKey(long answerid, long userid) {
		super();
		this.answerid = answerid;
		this.userid = userid;
	}
	public AnswerlikeKey() {
		super();
	}
	
}
