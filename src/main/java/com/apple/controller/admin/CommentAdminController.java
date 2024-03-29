package com.apple.controller.admin;

import com.apple.model.Comment;
import com.apple.model.PageBean;
import com.apple.service.CommentService;
import com.apple.util.ResponseUtil;
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
 * 评论审核Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

	@Resource
	private CommentService commentService;

    /**
     * 分页查询评论信息
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page, @RequestParam(value="rows",required=false)String rows,@RequestParam(value="state",required=false)String state ,HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<>();
        map.put("size", pageBean.getPageSize());
        map.put("state", state); //评论的状态
        map.put("start", pageBean.getStart());
        List<Comment> commentList = commentService.list(map);
        Long          total       =commentService.getTotal(map);
        JSONObject    result      =new JSONObject();
        JsonConfig    jsonConfig  =new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray =JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 评论审核
     * @param ids
     * @param state
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/review")
    public String review(@RequestParam(value="ids",required=false)String ids,@RequestParam(value="state",required=false)Integer state,HttpServletResponse response)throws Exception {
        String []idsStr=ids.split(",");
        for (String id : idsStr) {
            Comment comment=new Comment();
            comment.setId(Integer.parseInt(id));
            comment.setState(state);
            commentService.update(comment);

        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids",required=false)String ids,HttpServletResponse response)throws Exception {
        String []idsStr=ids.split(",");
        JSONObject result=new JSONObject();
        for(int i=0;i<idsStr.length;i++){
            commentService.delete(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
