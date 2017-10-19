package tv.huan.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Message;
@Service
public class MessageService extends BaseService<Message>{
	@Autowired
	IWeixinService weixinService;
	@Scheduled(fixedDelay=1000*60*5)
	public void replayMessage(){
		List<Message> list=baseDAO.find("from Message where status=1 and delFlag=0");
		if(list==null||list.size()==0)
			return;
		for(Message m:list)
		{
			if(weixinService.sendMsg(m.getOpenid(), m.getReply()))
			{
				m.setStatus(2);
				baseDAO.update(m);
			}
		}
	}
}
