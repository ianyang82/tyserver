package tv.huan.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.ClientUser;
@RequestMapping("wxuser")
@Controller
public class ClientUserController extends BaseCRUDController<ClientUser>{
}
