package tv.huan.master.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;
@Entity
@Table(name="classroom")
@DynamicInsert
@DynamicUpdate
public class ClassRoom extends BaseEntity  {
	private String location;
	private String name;
	private Date startdate;
	private String note;
	@OneToMany
	private Set<Classmate> classmates;
	private String weeks;
	public Set<Classmate> getClassmates() {
		return classmates;
	}
	public void setClassmates(Set<Classmate> classmates) {
		this.classmates = classmates;
	}
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
