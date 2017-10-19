package tv.huan.master.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;
@Table(name="order")
@Entity
@DynamicInsert
@DynamicUpdate
public class Order extends BaseEntity{
	private float cash;
	private long cid;
	private String cname;
	private Integer classnum;
	private String note;
	
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}
	public Integer getClassnum() {
		return classnum;
	}
	public void setClassnum(Integer classnum) {
		this.classnum = classnum;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
