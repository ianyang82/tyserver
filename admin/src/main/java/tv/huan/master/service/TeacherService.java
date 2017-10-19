package tv.huan.master.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Teacher;
@Service
public class TeacherService extends BaseService<Teacher>{
	public Teacher findByUserid(Long userid){
		DetachedCriteria crit = DetachedCriteria.forClass(Teacher.class);
		crit.add(Restrictions.eq("userid", userid));
		return (Teacher)baseDAO.findUnique(crit);
	}
}
