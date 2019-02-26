package com.apple.service.impl;

import com.apple.dao.BlogDao;
import com.apple.model.Blog;
import com.apple.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @program: Blog
*
* @description: 博客业务类
*
* @author: Apple
*
* @create: 2019-02-13 23:00
**/
@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogDao blogDao;

    public List<Blog> countList() {
        return blogDao.countList();
    }

    public List<Blog> list(Map<String, Object> map) {
        return blogDao.list(map);
    }

    public Long getTotal(Map<String, Object> map) {
        return blogDao.getTotal(map);
    }

    public Blog findById(Integer id) {
        return blogDao.findById(id);
    }

    public Integer update(Blog blog) {
        return blogDao.update(blog);
    }

    public Blog getLastBlog(Integer id) {
        return blogDao.getLastBlog(id);
    }

    public Blog getNextBlog(Integer id) {
        return blogDao.getNextBlog(id);
    }

    public Integer add(Blog blog) {
        return blogDao.add(blog);
    }

    @Override
    public Integer delete(Integer id) {
        return blogDao.delete(id);
    }

    @Override
    public Integer getBlogByTypeId(Integer typeId) {
        return blogDao.getBlogByTypeId(typeId);
    }
}
