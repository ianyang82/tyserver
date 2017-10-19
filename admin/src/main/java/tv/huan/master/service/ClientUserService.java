package tv.huan.master.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.ClientUser;
@Service
public class ClientUserService extends BaseService<ClientUser>{
	public ClientUser findOneByWxid(String wxid){
		return (ClientUser)baseDAO.findUnique(DetachedCriteria.forClass(ClientUser.class).add(Restrictions.eq("wxid", wxid)));
	}
}
