package tv.huan.master.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Material;
import tv.huan.master.util.HttpClientUtil;
import tv.huan.master.utils.FileParseUtil;
@Service
public class MaterialService extends BaseService<Material>{
	@Value("${file.dir.path}")
	private String dirpath;
	@Value("${file.url.path}")
	private String dirurl;
	private static Logger LOG= LoggerFactory.getLogger(MaterialService.class);
	@Autowired
	IWeixinService weixinService;
	public void updateServer(@RequestParam String type){
		baseDAO.getCurrentSession().createQuery("delete from Material where type= :type").setParameter("type", type).executeUpdate();
		String url="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+weixinService.refreshAccesstoken();
		int offset=0;
		while(true)
		{
			JSONObject js=new JSONObject();
			js.put("type", type);
			js.put("offset", offset);
			js.put("count", 20);
			JSONObject json=null;
			try {
				json=JSON.parseObject(HttpClientUtil.doPostByJson(url, js.toJSONString()));
				LOG.info(json.toJSONString());
				JSONArray ja= json.getJSONArray("item");
				if(ja!=null)
				{
					offset=offset+20;
					for(int i=0;i<ja.size();i++)
					{
						Material m=new Material();
						m.setType(type);
						m.setMedia_id(ja.getJSONObject(i).getString("media_id"));
						m.setContent(ja.getJSONObject(i).getString("content"));
						m.setName(ja.getJSONObject(i).getString("name"));
						m.setUrl(ja.getJSONObject(i).getString("url"));
						if("image".equals(type))
						{
							String filepath="wxmaterial"+File.separator+"image"+File.separator+m.getMedia_id()+"."+m.getUrl().substring(m.getUrl().lastIndexOf("=")+1);
							FileParseUtil.downloadFile(m.getUrl(), new File(dirpath+filepath));
							m.setUrl(dirurl+filepath);
						}
						baseDAO.save(m);
					}
					if(ja.size()<20)
						break;
				}else
				{
					LOG.error(json.toJSONString());
					break;
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
	}
	public void updateServer()
	{
		Arrays.asList(new String[]{"image","video","voice","news"}).forEach((data)->{
			updateServer(data);
		});
	}
	public Material findOneByMediaid(String media_id)
	{
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("media_id_eq", media_id);
		List<Material>  ls=this.find(map);
		return ls==null?null:ls.get(0);
	}
	public List<Material> findByType(String type)
	{
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("type", type);
		return this.find(map);
	}
}
