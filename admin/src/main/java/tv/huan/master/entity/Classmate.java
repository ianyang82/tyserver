package tv.huan.master.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;
@Table(name="classmate")
@Entity
@DynamicInsert
@DynamicUpdate
public class Classmate extends BaseEntity{
	private String name;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	private String parent;
	private String mobile;
	private Integer remainder;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getRemainder() {
		return remainder;
	}
	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}
	
}
