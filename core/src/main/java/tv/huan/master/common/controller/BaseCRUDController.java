package tv.huan.master.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tv.huan.master.common.Constants;
import tv.huan.master.common.entity.BaseEntity;
import tv.huan.master.common.model.EasyUiGridResponse;
import tv.huan.master.common.model.EasyUiPageRequest;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/27
 * Time: 14:10
 * To change this template use File | Settings | File Templates
 */
public class BaseCRUDController<M extends BaseEntity> extends BaseController {
    @Autowired
    protected BaseService<M> baseService;

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public String showListPage() {
        return viewName("list");
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public EasyUiGridResponse showListPage(EasyUiPageRequest easyUiPageRequest, Searchable searchable) {
        return baseService.find(easyUiPageRequest, searchable);
    }

    @RequestMapping(value = "create", method = {RequestMethod.GET})
    public String showCreateForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "create");
        return viewName("editForm");
    }

    @RequestMapping(value = "update", method = {RequestMethod.GET})
    public String showUpdateForm(@RequestParam(defaultValue="0",name="id") Long id,Model model) {
        model.addAttribute(Constants.OP_NAME, "update");
        if(id!=null&&id!=0)
        	 model.addAttribute("m", baseService.findById(id));
        return viewName("editForm");
    }

    @RequestMapping(value = "view", method = {RequestMethod.GET})
    public String view(@RequestParam("id") Long id, Model model) {
        model.addAttribute(Constants.OP_NAME, "view");
        model.addAttribute("m", baseService.findById(id));
        return viewName("view");
    }

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    @ResponseBody
    public MyResponse save(@Valid @ModelAttribute("m") M m, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            MyResponse<FieldError> myResponse = new MyResponse<FieldError>();
            myResponse.setError(1);
            myResponse.setMsg("数据验证失败");
            myResponse.setData(bindingResult.getFieldErrors());
            return myResponse;
        }else{
        	MyResponse<M> myResponse = new MyResponse<M>();
        	myResponse.getData().add(baseService.save(m));
	        return myResponse;
        }
    }

    @RequestMapping(value = "del", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public MyResponse del(String ids) {
        MyResponse myResponse = new MyResponse();
        baseService.del_logic(ids);
        return myResponse;
    }
    protected User getLoginuser(HttpServletRequest request) {
        HttpSession session = request.getSession();
    	return (User)session.getAttribute(Constants.SESSION_USER);
	}
}
