package com.hhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhs.entity.Comment;
import com.hhs.mapper.CommentMapper;
import com.hhs.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 14:27
 * @description：TODO
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Override
    public int deleteByPrimaryKey(Long commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public int insert(Comment record) {
        return commentMapper.insertSelective(record);
    }

    @Override
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    @Override
    public Comment selectByPrimaryKey(Long commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Comment record) {
        return 0;
    }
    @Override
    public List<Comment> findAllComments() {
        return commentMapper.findAllComments();
    }

    @Override
    public PageInfo<Comment> findCommentByUserName(int page, int limit, String username) {
        PageHelper.startPage(page, limit);
        List<Comment> list = commentMapper.findCommentsByUserName(username);
        PageInfo<Comment> info = new PageInfo<>(list);
        return info;
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public PageInfo<Comment> findAllCommentsBySplitPage(Integer page, Integer limit, String keyword) {
        PageHelper.startPage(page, limit);
        List<Comment> list = new ArrayList<Comment>();
        if(keyword != null && !keyword.trim().equals("")) {
            System.out.println("keyword:"+keyword);
            list = this.commentMapper.findCommentsByUserName(keyword);
        }else {
            //System.out.println("keyword:"+keyword);
            list = this.commentMapper.findAllComments();
        }
        PageInfo<Comment> info = new PageInfo<Comment>(list);
        return info;
    }
}
