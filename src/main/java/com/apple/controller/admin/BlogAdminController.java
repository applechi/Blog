package com.apple.controller.admin;

import com.apple.lucene.BlogIndex;
import com.apple.model.Blog;
import com.apple.model.PageBean;
import com.apple.service.BlogService;
import com.apple.util.ResponseUtil;
import com.apple.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	private BlogService blogService;
	private BlogIndex blogIndex=new BlogIndex();
	/**
	 * 添加或者修改博客信息
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Blog blog, HttpServletResponse response)throws Exception{
		int resultTotal=0;
		if(blog.getId()==null){
			resultTotal=blogService.add(blog);
            //String content = blog.getContent();
            //StringBuffer buffer = new StringBuffer(content);
            //if (buffer.toString().contains("img")){
            //   int index=buffer.indexOf("")
            //}
            blogIndex.addIndex(blog);
		}else{
            resultTotal=blogService.update(blog);
            blogIndex.updateIndex(blog);
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}

    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page, @RequestParam(value="rows",required=false)String rows, Blog s_blog, HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<>();
        map.put("size", pageBean.getPageSize());
        map.put("title", StringUtil.formatLike(s_blog.getTitle()));
        map.put("start", pageBean.getStart());
        List<Blog> blogList = blogService.list(map);
        Long total=blogService.getTotal(map);
        JSONObject result=new JSONObject();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray= JSONArray.fromObject(blogList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids",required=false)String ids, @RequestParam(value="rows",required=false)String rows, Blog s_blog, HttpServletResponse response)throws Exception{
        String []idStr=ids.split(",");
        for (String id : idStr) {
            blogService.delete(Integer.parseInt(id));
            blogIndex.deleteIndex(id);
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/findById")
    public String findById(@RequestParam(value="id")String id, HttpServletResponse response)throws Exception{
        Blog       blog  = blogService.findById(Integer.parseInt(id));
        JSONObject result=JSONObject.fromObject(blog);
        ResponseUtil.write(response, result);
        return null;
    }
}
