package tv.huan.master.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.entity.Answerlike;
import tv.huan.master.entity.AnswerlikeKey;
@Service
public class AnswerLikeService {
	@Autowired
    protected BaseDAO<Answerlike> baseDAO;
	public List<Answerlike> findByAnswerid(long id){
		DetachedCriteria crit = DetachedCriteria.forClass(Answerlike.class);
		crit.add(Restrictions.eq("answerlikeKey.answerid", id));
		return baseDAO.find(crit);
	}
	public Answerlike find(long id,long userid){
		return baseDAO.load(Answerlike.class, new AnswerlikeKey(id, userid));
	}
	public boolean updatelike(long answerid,long userid)
	{
		Answerlike a= baseDAO.load(Answerlike.class, new AnswerlikeKey(answerid, userid));
		if(a==null)
		{
			baseDAO.save(new Answerlike(new AnswerlikeKey(answerid, userid)));
			return true;
		}else
		{
			baseDAO.delete(a);
			return false;
		}
	}
}
