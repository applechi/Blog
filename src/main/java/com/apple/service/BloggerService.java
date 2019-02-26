package com.apple.service;

import com.apple.model.Blogger;

public interface BloggerService {
    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    Blogger getByUserName( String userName);

    /**
     * 查询博主信息
     * @return
     */
    public Blogger find();

    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    public Integer update(Blogger blogger);
}
