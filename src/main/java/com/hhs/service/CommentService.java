package com.hhs.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hhs.entity.Comment;

import java.util.List;

public interface CommentService {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> findAllComments();
    PageInfo<Comment> findCommentByUserName(int page, int limit, String username);
    PageInfo<Comment> findAllCommentsBySplitPage(Integer page,Integer limit,String keyword);
}
