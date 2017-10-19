package tv.huan.master.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.ClientUser;
import tv.huan.master.entity.Teacher;
import tv.huan.master.service.IWeixinService;
import tv.huan.master.service.TeacherService;
@Controller
@RequestMapping(path = "/teacher")
public class TeacherController extends BaseCRUDController<Teacher>{
	@Autowired
	private IWeixinService weixinService;
	@RequestMapping("apply")
	@ResponseBody
	public Teacher applyteacher(@RequestParam("file") MultipartFile file,@RequestParam("serverid") String serverid,@RequestParam String code,@RequestParam(required=false) String name,@RequestParam(required=false) String title,@RequestParam(required=false) String detail)
	{
		ClientUser user= weixinService.saveSessionUser(code);
		Teacher t=((TeacherService)baseService).findByUserid(user.getId());
		if(t==null)
		{
			t=new Teacher();
			t.setUserid(user.getId());
		}
		if(StringUtils.isNoneEmpty(detail))
			t.setDetail(detail);
		if(StringUtils.isNoneEmpty(name))
			t.setName(name);
		if(StringUtils.isNoneEmpty(title))
			t.setTitle(title);
		if(file!=null)
		{
			t.setImageurl(weixinService.saveFile(file, serverid));
		}
		
		return baseService.save(t);
	}
	@RequestMapping("all")
	@ResponseBody
	public List<Teacher> all()
	{
		Map<String,String> com=new HashMap<>();
		com.put("delFlag_eq", "0");
		com.put("status_eq", "1");
		return baseService.findRange(0, 10, com, "sort");
	}
}
