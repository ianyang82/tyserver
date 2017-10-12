package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.entity.Resource;
import tv.huan.master.entity.Role;
import tv.huan.master.security.UrlServiceImpl;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Role: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/11
 * Time: 13:54
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseCRUDController<Role> {
    @Autowired
    RoleService roleService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    UrlServiceImpl urlService;

    @RequestMapping(value = "getResourceList", method = {RequestMethod.POST})
    @ResponseBody
    public List<Resource> getResourceList(String id) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(id);

        if(isNum.matches() && !id.trim().equals("")){
            Role role = roleService.findById(Long.parseLong(id));
            List<Resource> resourceList =role.getResourceList();
            List<Resource> newResourceList = new ArrayList<Resource>();
            for (Resource resource:resourceList){
                if(resource.getParentId()!=0)      {
                    newResourceList.add(resource);
                }
            }
            return newResourceList;
        }else{
            return null;
        }
    }

    @Override
    public MyResponse save(@Valid @ModelAttribute("m") Role role, BindingResult bindingResult, HttpServletRequest request) {
        MyResponse<FieldError> myResponse = new MyResponse<>();
        if (bindingResult.hasErrors()) {
            myResponse.setError(1);
            myResponse.setMsg("数据验证失败");
            myResponse.setData(bindingResult.getFieldErrors());
            return myResponse;
        }

        roleService.save(role);
        urlService.loadResourceDefine();
        return myResponse;
    }
}
