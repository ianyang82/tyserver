package tv.huan.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tv.huan.master.common.Constants;
import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.model.EasyUiMenuModel;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.entity.Resource;
import tv.huan.master.service.ResourceService;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/16
 * Time: 13:22
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseCRUDController<Resource> {
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "findMenuList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<EasyUiMenuModel> findMenuList(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<EasyUiMenuModel> menuList = (List<EasyUiMenuModel>) session.getAttribute(Constants.SESSION_MENUS);
        return menuList;
    }

    @RequestMapping(value = "resList", method = {RequestMethod.POST})
    @ResponseBody
    public List showResList() {
        List<Resource> resources = resourceService.findAll();
        List parentList = new ArrayList();
        for (Resource resource : resources) {
            if (resource.getParentId() == 0) {
                Map<String, Object> parentMap = new HashMap<String, Object>();
                parentMap.put("id", resource.getId());
                parentMap.put("text", resource.getName());

                // 递归创建子节点
                List childList = createResourceTreeChildren(resources, resource.getId());

                parentMap.put("children", childList);
                parentList.add(parentMap);
            }
        }
        return parentList;
    }

    @RequestMapping(value = "findParentList", method = {RequestMethod.POST})
    @ResponseBody
    public List findParentList(Searchable searchable) {
        return resourceService.find(searchable.getMap());
    }

    @RequestMapping(value = "findResourceById")
    @ResponseBody
    public Resource findResourceById(Long id) {
        return resourceService.findById(id);
    }

    /**
     * 递归设置Resource树
     *
     * @param list
     * @param fid
     * @return
     */
    private List<Map<String, Object>> createResourceTreeChildren(List<Resource> list, Long fid) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = null;
            Resource treeChild = (Resource) list.get(i);
            if (treeChild.getParentId() == fid) {
                map = new HashMap<String, Object>();
                //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text
                map.put("id", list.get(i).getId());
                map.put("text", list.get(i).getName());
                map.put("children", createResourceTreeChildren(list, treeChild.getId()));
            }

            if (map != null) {
                childList.add(map);
            }
        }
        return childList;
    }
}
