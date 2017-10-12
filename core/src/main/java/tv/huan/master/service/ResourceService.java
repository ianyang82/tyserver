package tv.huan.master.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;
import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Resource;
import tv.huan.master.entity.Role;
import tv.huan.master.entity.User;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Resource: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/9
 * Time: 11:33
 * To change this template use File | Settings | File Templates
 */
@Service
public class ResourceService extends BaseService<Resource> {
    @Autowired
    BaseDAO<Resource> baseDAO;

    /**
     * 查找一级分类
     *
     * @return
     */
    public List<Resource> findToSelect() {
        DetachedCriteria crit = DetachedCriteria.forClass(Resource.class);
        crit.add(Restrictions.eq("parentId", 0));
        return baseDAO.find(crit);
    }

    /**
     * 按照用户查找资源
     *
     * @param user
     * @return
     */
    public List<Resource> findResourceList(User user) {
        Set<Long> set = new HashSet<Long>();
        List<Resource> newList = new ArrayList<Resource>();
        for (Role role : user.getRoleList()) {
            for (Resource resource : role.getResourceList()) {
                if (!set.contains(resource.getId())) {
                    set.add(resource.getId());
                    newList.add(resource);
                }
            }
        }
        return newList;
    }

    public List<Role> findRoleList(String url) {
        DetachedCriteria crit = DetachedCriteria.forClass(Resource.class);
        crit.add(Restrictions.eq("url", url));
        List<Resource> resourceList = baseDAO.find(crit);
        if (resourceList.size() == 0) {
            return null;
        } else {
            return resourceList.get(0).getRoleList();
        }
    }

    public Map<String, Collection<ConfigAttribute>> findResourceRole() {
        List<Resource> list = this.findAll();
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        for (Resource resource : list) {
            List<Role> roleList = resource.getRoleList();
            Collection<ConfigAttribute> roles = new ArrayList<ConfigAttribute>();
            for (Role role : roleList) {
                roles.add(role);
            }
            resourceMap.put(resource.getUrl(), roles);
        }
        return resourceMap;
    }

    @Override
    protected Class baseClass() {
        return Resource.class;
    }
}
