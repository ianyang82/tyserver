package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tv.huan.master.common.controller.BaseController;
import tv.huan.master.entity.ReplyInfo;
import tv.huan.master.service.ReplyInfoService;
@Controller
@RequestMapping("/replyinfo")
public class ReplyInfoController extends BaseController<ReplyInfo>{
	@Autowired
	ReplyInfoService replyInfoService;
	@RequestMapping(value = "synctodb")
    @ResponseBody
    public String synctodb()
    {
		replyInfoService.updateServerToLocal();
    	return "ok";
    }
}
