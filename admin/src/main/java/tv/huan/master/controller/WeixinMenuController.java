package tv.huan.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.entity.WeixinMenu;
import tv.huan.master.service.WeixinMenuService;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/16
 * Time: 13:22
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/wxmenu")
public class WeixinMenuController extends BaseCRUDController<WeixinMenu> {
    @Autowired
    WeixinMenuService weixinMenuService;


    @RequestMapping(value = "resList", method = {RequestMethod.POST})
    @ResponseBody
    public List showResList() {
        List<WeixinMenu> resources = weixinMenuService.findAll();
        List parentList = new ArrayList();
        for (WeixinMenu resource : resources) {
            if (resource.getParentId() == null) {
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
        return weixinMenuService.find(searchable.getMap());
    }

    @RequestMapping(value = "findwxmenuById")
    @ResponseBody
    public WeixinMenu findwxmenuById(Long id) {
        return weixinMenuService.findById(id);
    }

    /**
     * 递归设置Resource树
     *
     * @param list
     * @param fid
     * @return
     */
    private List<Map<String, Object>> createResourceTreeChildren(List<WeixinMenu> list, Long fid) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = null;
            WeixinMenu treeChild = (WeixinMenu) list.get(i);
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
    @RequestMapping(value = "synctodb")
    @ResponseBody
    public String synctodb()
    {
    	weixinMenuService.updateServerToLocal();
    	return "ok";
    }
    @RequestMapping(value = "synctoserver")
    @ResponseBody
    public String synctoserver()
    {
    	JSONObject js=new JSONObject();
    	js.put("button", weixinMenuService.getLocalWeixinMenu());
    	return weixinMenuService.synctoserver(js.toJSONString());
//    	return weixinMenuService.synctoserver(new JSONObject().put("button", weixinMenuService.getLocalWeixinMenu()).toString());
    }
}
