package tv.huan.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.Question;
import tv.huan.master.entity.Type;
import tv.huan.master.service.QuestionService;
@Controller
@RequestMapping(path="/question")
public class QuestionController extends BaseCRUDController<Question>{
	@Autowired
	QuestionService questionService;
	@RequestMapping(path = "next", method = {RequestMethod.GET})
	@ResponseBody
    public Question next(@RequestParam(defaultValue="0", name="id") Long id,@RequestParam(defaultValue="0", name="next") Integer next) {
		Question q=questionService.find(id,next);
		/*if(q!=null){
			if(q.getReadcount()==null)
				q.setReadcount(0);
			q.setReadcount(q.getReadcount()+1);
			questionService.saveBase(q);
		}*/
        return q;
    }
	@RequestMapping("pagelist")
	@ResponseBody
	public List<Question> pagelist(@RequestParam(defaultValue="0",name="f") int from,@RequestParam(required=false,name="t") Integer type,@RequestParam(defaultValue="10",name="s") int size,@RequestParam(defaultValue = "0", name = "e") long end){
		Map<String,String> con=new HashMap<String,String>();
		if (end != 0)
			con.put("id_lt", end + "");
		else
			con.put("id_gt", from + "");
		if(type!=null)
			con.put("type_eq", type.toString());
		con.put("delFlag_eq", "0");
		return baseService.findRange(0, size, con);
	}
	@RequestMapping("types")
	@ResponseBody
	public List<Type> types(){
		List<Type> types=new ArrayList<>();
		types.add(new Type(0, "绕口令"));
		types.add(new Type(1, "散文"));
		types.add(new Type(2, "诗词"));
		types.add(new Type(3, "故事"));
		types.add(new Type(4, "新闻"));
		types.add(new Type(5, "经典"));
		types.add(new Type(9, "其他"));
		return types;
	}
}
