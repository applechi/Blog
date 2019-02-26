package com.apple.dao;

import com.apple.model.Blogger;
import org.apache.ibatis.annotations.Param;

public interface BloggerDao {

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    Blogger getByUserName(@Param("userName") String userName);

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
