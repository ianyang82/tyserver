package tv.huan.master.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.entity.Answer;
import tv.huan.master.entity.ClientUser;
import tv.huan.master.entity.Comment;
import tv.huan.master.entity.User;
import tv.huan.master.service.AnswerService;
import tv.huan.master.service.IWeixinService;
@Controller
@RequestMapping(path="/comment")
public class CommentController extends BaseCRUDController<Comment>{
	public static final Logger LOG=Logger.getLogger(CommentController.class);
	@Autowired
	IWeixinService weixinService;
	@Autowired
	AnswerService answerService;
	
	@RequestMapping(value = "save", method = {RequestMethod.POST})
	@ResponseBody
	public MyResponse<Comment> save(@Valid @ModelAttribute("m") Comment m, BindingResult bindingResult, HttpServletRequest request) {
		String code=request.getParameter("code");
		User user=getLoginuser(request);
		if(code!=null&&code.trim().length()>0)
	    {
		    ClientUser cuser= weixinService.saveSessionUser(code);
		    m.setUserid(cuser.getId());
		    m.setHeadurl(cuser.getHeadurl());
		    m.setUsernickname(StringUtils.isEmpty(cuser.getName())?cuser.getNickname():cuser.getName()); 
			if(cuser.getType()==ClientUser.TYPE_TEACHER)
			{
				m.setType(cuser.getType());
				Answer a= answerService.findById(m.getAnswerid());
				a.setStatus(1);
				answerService.save(a);
			}
	    }else if(user!=null)
		{
			m.setUserid(user.getId());
			m.setUsernickname(user.getRealName());
		}else
		{
			m.setUserid(2l);
			m.setUsernickname("测试用户");
		}
		
	    return super.save(m, bindingResult, request);
	 }
	@RequestMapping("pagelist")
	@ResponseBody
	public List<Comment> pagelist(@RequestParam(defaultValue = "0",name = "qid") long answerid,
			@RequestParam(defaultValue = "0", name = "f") long from,
			@RequestParam(defaultValue = "0", name = "e") long end,
			@RequestParam(defaultValue = "10", name = "s") int size) {
		Map<String, String> con = new HashMap<String, String>();
		if(answerid!=0)
		con.put("answerid_eq", answerid + "");
		con.put("delFlag_eq", "0");
		if (end != 0)
			con.put("id_lt", end + "");
		else
			con.put("id_gt", from + "");
		List<Comment> list = null;
		list=baseService.findRange(0, size, con);
		return list;
	}
	@RequestMapping("sound")
	@ResponseBody
	public Comment upload(@RequestParam("file") MultipartFile file, @RequestParam("code") String code,
			@RequestParam("serverid") String serverid, @RequestParam("aid") Long aid) {
		LOG.info("aid:::::"+aid);
		if (code != null && code.trim().length() > 0) {
			ClientUser user = weixinService.saveSessionUser(code);
			if(user!=null)
			{
				String path = weixinService.saveFile(file, serverid);
				Comment m=new Comment();
				m.setUserid(user.getId());
				m.setHeadurl(user.getHeadurl());
				m.setUsernickname(StringUtils.isEmpty(user.getName())?user.getNickname():user.getName());
				m.setVurl(path);
				m.setAnswerid(aid);
				if(user.getType()==ClientUser.TYPE_TEACHER)
				{
					m.setType(user.getType());
					Answer a= answerService.findById(m.getAnswerid());
					a.setStatus(1);
					answerService.save(a);
				}
				baseService.save(m);
				return m;
			}
		} 
		return null;
	}
}
