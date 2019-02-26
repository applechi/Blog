package com.apple.service.impl;

import com.apple.model.Blog;
import com.apple.model.BlogType;
import com.apple.model.Blogger;
import com.apple.model.Link;
import com.apple.service.BlogService;
import com.apple.service.BlogTypeService;
import com.apple.service.BloggerService;
import com.apple.service.LinkService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    //@Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        //博主信息
        BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
        Blogger        blogger        = bloggerService.find(); // 获取博主信息
        blogger.setPassword(null);
        application.setAttribute("blogger", blogger);
        //友情链接信息
        LinkService linkService = (LinkService) applicationContext.getBean("linkService");
        List<Link>  linkList    = linkService.list(null); // 查询所有的友情链接信息
        application.setAttribute("linkList", linkList);
        //日志类别归档博客信息
        BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
        List<BlogType>  blogTypeList    = blogTypeService.countList();
        application.setAttribute("blogTypeCountList", blogTypeList);
        //日志日期归档博客信息
        BlogService blogService = (BlogService) applicationContext.getBean("blogService");
        List<Blog>  blogList    = blogService.countList();
        application.setAttribute("blogCountList", blogList);

    }

    //@Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

    //@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
