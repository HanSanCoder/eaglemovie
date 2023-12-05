package com.hhs.service;

import com.github.pagehelper.PageInfo;
import com.hhs.entity.User;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserPwd(String username, String password);

    List<User> selectByUserName(String username);
    List<User> findAllUser();
    PageInfo<User> findUserBySplitPage(Integer page, Integer limit, String keyword);
}
