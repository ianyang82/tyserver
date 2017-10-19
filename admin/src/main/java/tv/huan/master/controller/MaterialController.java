package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.Material;
import tv.huan.master.service.MaterialService;
@Controller
@RequestMapping("material")
public class MaterialController extends BaseCRUDController<Material>{
	@Autowired
	MaterialService materialService;
	@RequestMapping("snycserver")
	@ResponseBody
	public String snycserver(@RequestParam(required=false) String type)
	{
		if(type!=null&&type.trim().length()>0)
			materialService.updateServer(type);
		else
			materialService.updateServer();
		return "ok";
	}
	@RequestMapping("image/{media_id}")
	@ResponseBody
	public String getimage(@PathVariable String media_id){
		Material m= materialService.findOneByMediaid(media_id);
		return m==null?"":m.getUrl();
	}
	 @RequestMapping(value = "newslist", method = {RequestMethod.GET})
	 public String newslist() {
	        return viewName("newslist");
	 }
}
