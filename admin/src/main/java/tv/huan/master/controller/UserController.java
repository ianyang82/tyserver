package tv.huan.master.controller;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.Role;
import tv.huan.master.entity.User;
import tv.huan.master.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA. User: warriorr Mail: warriorr@163.com Date:
 * 2014/8/11 Time: 14:32 To change this template use File | Settings | File
 * Templates
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseCRUDController<User> {
    @Autowired
	UserService userService;
    
    @RequestMapping(value = "getRoleList", method = {RequestMethod.POST})
    @ResponseBody
    public List<Role> getRoleList(String id) {
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(id);
		
		if(isNum.matches() && !id.trim().equals("")){
			User user = userService.findById(Long.parseLong(id));
	    	return user.getRoleList();
		}else{
			return null;
		}
    }
}
