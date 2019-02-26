package com.apple.service.impl;

import com.apple.dao.LinkDao;
import com.apple.model.Link;
import com.apple.service.LinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @program: Blog
*
* @description: 友情链接业务
*
* @author: Apple
*
* @create: 2019-02-13 18:28
**/
@Service("linkService")
public class LinkServiceImpl implements LinkService {
    @Resource
    private LinkDao linkDao;

    public List<Link> list(Map<String, Object> map) {
        return linkDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return linkDao.getTotal(map);
    }

    @Override
    public Integer add(Link link) {
        return linkDao.add(link);
    }

    @Override
    public Integer update(Link link) {
        return linkDao.update(link);
    }

    @Override
    public Integer delete(Integer id) {
        return linkDao.delete(id);
    }
}
