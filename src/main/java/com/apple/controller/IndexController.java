package com.apple.controller;

import com.apple.model.Blog;
import com.apple.model.PageBean;
import com.apple.service.BlogService;
import com.apple.util.PageUtil;
import com.apple.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Blog
 * @description: 首页界面展示控制器
 * @author: Apple
 * @create: 2019-02-14 11:01
 **/
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private BlogService blogService;

    /**
     * 请求主页
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value="page",required=false)String page,@RequestParam(value="typeId",required=false)String typeId,@RequestParam(value="releaseDateStr",required=false)String releaseDateStr,HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        PageBean pageBean=new PageBean(Integer.parseInt(page), 10);
        Map<String,Object>map=new HashMap<String, Object>();
        map.put("size", pageBean.getPageSize());
        map.put("start", pageBean.getStart());
        map.put("typeId", typeId);
        map.put("releaseDateStr", releaseDateStr);
        List<Blog> blogList = blogService.list(map);
        for (Blog blog : blogList) {
            List<String> imageList =blog.getImageList();
            String       content   = blog.getContent();
            Document     document  = Jsoup.parse(content);
            Elements     jpgs      =document.select("img[src$=.jpg]");
            for (int i = 0; i < jpgs.size(); i++) {
                Element jpg=jpgs.get(i);
                imageList.add(jpg.toString());
                if (i==2){
                    break;
                }
            }

        }
        StringBuffer param=new StringBuffer();
        if(StringUtil.isNotEmpty(typeId)){
            param.append("typeId="+typeId+"&");
        }
        if(StringUtil.isNotEmpty(releaseDateStr)){
            param.append("releaseDateStr="+releaseDateStr+"&");
        }
        long totalNum= blogService.getTotal(map);
        mv.addObject("pageCode", PageUtil.genPagination(request.getContextPath()+"/index.html",    blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
        mv.addObject("blogList",blogList);
        mv.addObject("pageTitle", "java开源博客系统");
        mv.addObject("mainPage", "foreground/blog/list.jsp");
        mv.setViewName("mainTemp");
        return mv;
    }

    /**
     * 源码下载
     * @return
     * @throws Exception
     */
    @RequestMapping("/download")
    public ModelAndView download()throws Exception{
        ModelAndView mav=new ModelAndView();
        mav.addObject("pageTitle", "本站源码下载页面_java开源博客系统");
        mav.addObject("mainPage", "foreground/system/download.jsp");
        mav.setViewName("mainTemp");
        return mav;
    }
}
