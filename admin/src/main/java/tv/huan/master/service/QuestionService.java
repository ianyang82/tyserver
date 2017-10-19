package tv.huan.master.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Question;
@Service
public class QuestionService extends BaseService<Question>{
	public Question find(Long id,int next)
	{

		if(id==null||id==0)
			return (Question)baseDAO.findUnique(DetachedCriteria.forClass(Question.class).addOrder(Order.desc("id")));
		else if(next==0)
		{
			return (Question)baseDAO.load(Question.class, id);
		}else if(next==1)
			return (Question)baseDAO.findUnique(DetachedCriteria.forClass(Question.class).add(Restrictions.gt("id", id)).addOrder(Order.asc("id")));
		else
			return (Question)baseDAO.findUnique(DetachedCriteria.forClass(Question.class).add(Restrictions.lt("id", id)).addOrder(Order.desc("id")));
	}
	@Override
	public Question save(Question base) {
		if(base.getId()==null)
		{
			base.setAnswercount(0);
			base.setReadcount(0);
		}
		return super.save(base);
	}
}
