package tv.huan.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tv.huan.master.common.controller.BaseController;
import tv.huan.master.entity.ReplyMessage;
@Controller
@RequestMapping("/replymsg")
public class ReplyMessageController extends BaseController<ReplyMessage> {

}
