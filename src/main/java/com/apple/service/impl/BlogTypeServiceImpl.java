package com.apple.service.impl;

import com.apple.dao.BlogTypeDao;
import com.apple.model.BlogType;
import com.apple.service.BlogTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @program: Blog
*
* @description: 博客类型业务
*
* @author: Apple
*
* @create: 2019-02-13 22:58
**/
@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

    @Resource
    private BlogTypeDao blogTypeDao;
    public List<BlogType> countList() {
        return blogTypeDao.countList();
    }

    @Override
    public List<BlogType> list(Map<String, Object> map) {
        return blogTypeDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogTypeDao.getTotal(map);
    }

    @Override
    public Integer save(BlogType blogType) {
        return blogTypeDao.save(blogType);
    }

    @Override
    public Integer update(BlogType blogType) {
        return blogTypeDao.update(blogType);
    }

    @Override
    public Integer delete(Integer id) {
        return blogTypeDao.delete(id);
    }
}
