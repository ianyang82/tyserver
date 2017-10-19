package tv.huan.master.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;
@Table(name="signrecord")
@Entity
@DynamicInsert
@DynamicUpdate
public class SignRecord extends BaseEntity{
	private long cid;
	private String cname;
	private String signature;
	private long crid;
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
	public long getCrid() {
		return crid;
	}
	public void setCrid(long crid) {
		this.crid = crid;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
		
}
