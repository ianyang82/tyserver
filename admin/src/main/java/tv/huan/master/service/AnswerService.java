package tv.huan.master.service;

import org.springframework.stereotype.Service;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Answer;
import tv.huan.master.entity.Question;
@Service
public class AnswerService extends BaseService<Answer>{
	@Override
	public Answer save(Answer base) {
		if(base.getId()==null)
		{
			Answer a=super.save(base);
			Question q= (Question)baseDAO.load(Question.class, a.getQuestionid());
		    if(q!=null)
		    {
		    	a.setTitle(q.getTitle());
		    	if(q.getAnswercount()==null)
		    		q.setAnswercount(1);
		    	else
		    		q.setAnswercount(q.getAnswercount()+1);
		    	baseDAO.save(q);
		    }
		    return a;
		}
		else
			return super.save(base);
	}
}
