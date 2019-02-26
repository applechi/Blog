package com.apple.service.impl;

import com.apple.dao.CommentDao;
import com.apple.model.Comment;
import com.apple.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 评论Service实现类
 * @author Administrator
 *
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentDao commentDao;

	public List<Comment> list(Map<String, Object> map) {
		return commentDao.list(map);
	}

	public int add(Comment comment) {
		return commentDao.add(comment);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return commentDao.getTotal(map);
	}

	@Override
	public int update(Comment comment) {
		return commentDao.update(comment);
	}

	@Override
	public Integer delete(Integer id) {
		return commentDao.delete(id);
	}

}
