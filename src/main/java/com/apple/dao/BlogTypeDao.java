package com.apple.dao;

import com.apple.model.Blog;
import com.apple.model.BlogType;

import java.util.List;
import java.util.Map;

/**
 * 博客类型Dao
 */
public interface BlogTypeDao {
    /**
     * 查询所有博客类型，以及对应的博客数量
     * @return
     */
    public List<BlogType> countList();

    /**
     * 通过id查找博客类型实体
     * @param id
     * @return
     */
    public BlogType findById(Integer id);

    /**
     * 分页查询博客类别
     *
     * @param map
     * @return
     */
    public List<BlogType> list(Map<String, Object> map);

    /**
     * 获取总记录数类别
     *
     * @param map
     * @return
     */
    public Long getTotal(Map<String, Object> map);

    /**
     * 保存博客类型
     * @param blogType
     * @return
     */
    public Integer save(BlogType blogType);
    /**
     * 更新博客类型
     * @param blogType
     * @return
     */
    public Integer update(BlogType blogType);

    /**
     * 删除博客类型
     * @param blogType
     * @return
     */
    public Integer delete(Integer id);

}
