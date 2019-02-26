package com.apple.controller.admin;

import com.apple.model.BlogType;
import com.apple.model.PageBean;
import com.apple.service.BlogService;
import com.apple.service.BlogTypeService;
import com.apple.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员博客类别Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

	@Resource
	private BlogTypeService blogTypeService;
	@Resource
    private BlogService blogService;


    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page, @RequestParam(value="rows",required=false)String rows,HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<>();
        map.put("size", pageBean.getPageSize());
        map.put("start", pageBean.getStart());
        List<BlogType> blogTypeList   = blogTypeService.list(map);
        Long           total      =blogTypeService.getTotal(map);
        JSONObject     result     =new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(blogTypeList);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/save")
    public String save(BlogType blogType,HttpServletResponse response)throws Exception {
        int totalReuslt=0;
        if (blogType.getId()==null){
            totalReuslt=blogTypeService.save(blogType);
        }else{
            totalReuslt=blogTypeService.update(blogType);
        }
        JSONObject result=new JSONObject();
        if (totalReuslt>0){
            result.put("success",true);
        }else{
            result.put("success",false);
        }
        ResponseUtil.write(response, result);
        return null;

    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids",required=false)String ids,HttpServletResponse response)throws Exception {
        String []idStr=ids.split(",");
        JSONObject result=new JSONObject();
        for (String id : idStr) {
            int totalResult=blogService.getBlogByTypeId(Integer.parseInt(id));
            if(totalResult>0){
                result.put("exist", "博客类别下有博客，不能删除！");
                result.put("success", true);
                ResponseUtil.write(response, result);
                return null;
            }else{
                blogTypeService.delete(Integer.parseInt(id));
            }
        }
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;

    }


}
