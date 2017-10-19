package tv.huan.master.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;
@Entity
@Table(name="classrecord")
@DynamicInsert
@DynamicUpdate
public class ClassRecord extends BaseEntity{
	private long crid;
	private String crname;
	private String teachername;
	@Temporal(TemporalType.DATE)
	private Date classdate;
	@OneToMany
	private Set<SignRecord> signRecords;
	public long getCrid() {
		return crid;
	}
	public void setCrid(long crid) {
		this.crid = crid;
	}
	public String getCrname() {
		return crname;
	}
	public void setCrname(String crname) {
		this.crname = crname;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public Set<SignRecord> getSignRecords() {
		return signRecords;
	}
	public void setSignRecords(Set<SignRecord> signRecords) {
		this.signRecords = signRecords;
	}
	public Date getClassdate() {
		return classdate;
	}
	public void setClassdate(Date classdate) {
		this.classdate = classdate;
	}
	
}
