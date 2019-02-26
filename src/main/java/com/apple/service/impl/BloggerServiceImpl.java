package com.apple.service.impl;

import com.apple.dao.BloggerDao;
import com.apple.model.Blogger;
import com.apple.service.BloggerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {
    @Resource
    private BloggerDao bloggerDao;

    public Blogger getByUserName(String userName) {
        return bloggerDao.getByUserName(userName);
    }

    public Blogger find() {
        return bloggerDao.find();
    }

    @Override
    public Integer update(Blogger blogger) {
        return bloggerDao.update(blogger);
    }
}
