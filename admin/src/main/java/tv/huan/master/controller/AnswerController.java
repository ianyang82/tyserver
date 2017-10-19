package tv.huan.master.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.entity.BaseEntity;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.entity.Answer;
import tv.huan.master.entity.Answerlike;
import tv.huan.master.entity.ClientUser;
import tv.huan.master.entity.Question;
import tv.huan.master.service.AnswerLikeService;
import tv.huan.master.service.CommentService;
import tv.huan.master.service.IWeixinService;
import tv.huan.master.service.QuestionService;

@Controller
@RequestMapping(path = "/answer")
public class AnswerController extends BaseCRUDController<Answer> {
	@Autowired
	CommentService commentService;
	@Value("${file.url.path}")
	String fileurl;
	@Value("${file.dir.path}")
	String filedir;
	@Autowired
	IWeixinService weixinService;
	@Autowired
	QuestionService questionService;
	@Autowired
	AnswerLikeService answerLikeService;

	@RequestMapping("pagelist")
	@ResponseBody
	public List<Answer> pagelist(@RequestParam(defaultValue = "0",name = "qid") long questionid,
			@RequestParam(defaultValue = "0", name = "f") long from,
			@RequestParam(defaultValue = "0", name = "e") long end,
			@RequestParam(defaultValue = "10", name = "s") int size,
			@RequestParam(defaultValue = "0",name = "uid") long userid,
			@RequestParam(defaultValue = "0", name = "c") int c,
			@RequestParam(required = false, name = "code") String code) {
		Map<String, String> con = new HashMap<String, String>();
		if(questionid!=0)
		con.put("questionid_eq", questionid + "");
		con.put("delFlag_eq", "0");
		if (end != 0)
			con.put("id_lt", end + "");
		else
			con.put("id_gt", from + "");
		if(userid!=0)
			con.put("userid_eq", userid+"");
		if(c!=0)
			con.put("status_ne", c+"");
		ClientUser user = null;
		if (StringUtils.isNotEmpty(code)) {
			user = weixinService.saveSessionUser(code);
		}
		List<Answer> list = null;
		list=baseService.findRange(0, size, con);
		for (Answer a : list) {
			con = new HashMap<String, String>();
			con.put("delFlag_eq", "0");
			con.put("answerid_eq", a.getId().toString());
			if(user!=null)
				a.setLike(answerLikeService.find(a.getId(), user.getId())!=null);
			a.setComments(commentService.findRange(0, 5, con));
		}
		return list;
	}
	@RequestMapping("toplist")
	@ResponseBody
	public List<Answer> toplist(@RequestParam(defaultValue = "0",name = "t") int type) {
		Map<String, String> con = new HashMap<String, String>(); 
		con.put("delFlag_eq", "0");
		String order=null;
		if(type==1)
		{
			Calendar c=Calendar.getInstance();
			c.add(Calendar.DATE, -7);
			con.put("createDate_gt",new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
			order="lcount";
		}else if(type==2)
		{
			con.put("top_eq","1");
		}
		List<Answer> list = baseService.findRange(0, 10, con,order);
		return list;
	}
	@RequestMapping("top")
	@ResponseBody
	public Answer top(@RequestParam(required=true) long id,@RequestParam() String code) {
		ClientUser user = weixinService.saveSessionUser(code);
		if(user.getType()==ClientUser.TYPE_TEACHER||user.getType()==ClientUser.TYPE_ADMIN)
		{
			Answer a=baseService.findById(id);
			a.setTop(a.getTop()==null?true:!a.getTop());
			baseService.save(a);
			HashMap<String, String> con = new HashMap<String, String>();
			con.put("delFlag_eq", "0");
			con.put("answerid_eq", a.getId().toString());
			a.setComments(commentService.findRange(0, 5, con));
			return a;
		}
		return null;
	}
	@RequestMapping("find")
	@ResponseBody
	public Answer find(@RequestParam(required=true) long id,@RequestParam(required = false, name = "code") String code) {
		Answer a=baseService.findById(id);
		ClientUser user=null;
		if (StringUtils.isNotEmpty(code)) {
			user = weixinService.saveSessionUser(code);
		}
		if(a!=null)
		{
			HashMap<String, String> con = new HashMap<String, String>();
			con.put("delFlag_eq", "0");
			con.put("answerid_eq", a.getId().toString());
			a.setComments(commentService.findRange(0, 5, con));
			if(user!=null)
				a.setLike(answerLikeService.find(a.getId(), user.getId())!=null);
		}
		return a;
	}
	@RequestMapping(method = RequestMethod.POST, path = "upload")
	@ResponseBody
	public Answer upload(@RequestParam(required = false, name = "vfile") CommonsMultipartFile file,
			@RequestParam(required = false, name = "qid") Long qid,
			@RequestParam(required = false, name = "code") String code) throws IOException {
		String filename = new Date().getTime()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String path = filedir + filename;
		String url = fileurl + filename;
		File newFile = new File(path);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(newFile);
		Answer a = new Answer();
		a.setVurl(url);
		a.setQuestionid(qid);
		if (code != null && code.trim().length() > 0) {
			ClientUser user = weixinService.saveSessionUser(code);
			a.setUserid(user.getId());
			a.setHeadurl(user.getHeadurl());
			a.setUsernickname( StringUtils.isEmpty(user.getName())?user.getNickname():user.getName());
		} else {
			a.setUserid(2l);
			a.setHeadurl("/static/jquery-easyui/demo-mobile/images/scanner.png");
			a.setUsernickname("测试用户");
		}
		baseService.save(a);
		return a;
	}

	@RequestMapping(path = "downwx")
	@ResponseBody
	public Answer downwx(@RequestParam(required = false) String serverid,
			@RequestParam(required = false, name = "qid") Long qid,
			@RequestParam(required = false, name = "code") String code) throws IOException {
		String url = weixinService.saveFile(serverid);
		Answer a = new Answer();
		a.setVurl(url);
		a.setQuestionid(qid);
		if (code != null && code.trim().length() > 0) {
			ClientUser user = weixinService.saveSessionUser(code);
			a.setUserid(user.getId());
			a.setHeadurl(user.getHeadurl());
			a.setUsernickname(StringUtils.isEmpty(user.getName())?user.getNickname():user.getName());
		} else {
			a.setUserid(2l);
			a.setHeadurl("/static/jquery-easyui/demo-mobile/images/scanner.png");
			a.setUsernickname("测试用户");
		}

		baseService.save(a);
		return a;
	}

	@RequestMapping("answer")
	@ResponseBody
	public Answer upload(@RequestParam("file") MultipartFile file, @RequestParam("code") String code,
			@RequestParam("serverid") String serverid, @RequestParam("qid") Long qid) {
		if (code != null && code.trim().length() > 0) {
			ClientUser user = weixinService.saveSessionUser(code);
			if(user!=null)
			{
				String path = weixinService.saveFile(file, serverid);
				Answer a = new Answer();
				a.setVurl(path);
				a.setQuestionid(qid);
				a.setUserid(user.getId());
				a.setHeadurl(user.getHeadurl());
				a.setUsernickname(StringUtils.isEmpty(user.getName())?user.getNickname():user.getName());
				baseService.save(a);
				return a;
			}
		} 
		return null;
	}

	@RequestMapping(path = "remove")
	@ResponseBody
	public MyResponse remove(@RequestParam Long id, @RequestParam(required = false, name = "code") String code) {
		Answer a = baseService.findById(id);
		if (StringUtils.isNotEmpty(code) && a != null) {
			ClientUser user = weixinService.saveSessionUser(code);
			if (user != null && a.getUserid().equals(user.getId())) {
				a.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
				baseService.save(a);
			}
		}
		return new MyResponse();
	}
	@RequestMapping(path = "paly")
	@ResponseBody
	public MyResponse paly(@RequestParam Long id) {
		Answer a = baseService.findById(id);
		if ( a != null) {
			a.setPalycount(a.getPalycount()==null?1:a.getPalycount()+1);
			baseService.save(a);
		}
		return new MyResponse();
	}

	@RequestMapping(path = "like")
	@ResponseBody
	public Answer like(@RequestParam Long id, @RequestParam(required = false, name = "code") String code) {
		Answer a = baseService.findById(id);
		if (StringUtils.isNotEmpty(code) && a != null) {
			ClientUser user = weixinService.saveSessionUser(code);
			if (user != null) {
				a.setLike(answerLikeService.updatelike(a.getId(), user.getId()));
				if(a.isLike())
					a.setLcount(a.getLcount()==null?1:a.getLcount()+1);
				else
					a.setLcount(a.getLcount()==null||a.getLcount()<1?0:a.getLcount()-1);
				baseService.save(a);
				HashMap<String, String> con = new HashMap<String, String>();
				con.put("delFlag_eq", "0");
				con.put("answerid_eq", a.getId().toString());
				a.setComments(commentService.findRange(0, 5, con));
			}
		}
		return a;
	}
}
