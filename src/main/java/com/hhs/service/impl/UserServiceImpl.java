package com.hhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhs.entity.User;
import com.hhs.mapper.UserMapper;
import com.hhs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/24 15:03
 * @description：用户数据控制
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int deleteByPrimaryKey(Long userId) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        String md5PWD = DigestUtils.md5DigestAsHex(record.getUser_pwd().getBytes());
        record.setUser_pwd(md5PWD);
        return userMapper.insertSelective(record);
    }
    // 通过用户密码查找
    @Override
    public User selectByUserPwd(String username, String password) {
        return userMapper.selectByUserPwd(username, password);
    }

    @Override
    public List<User> selectByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public PageInfo<User> findUserBySplitPage(Integer page, Integer limit, String keyword) {
        List<User> list = new ArrayList<>();
        PageHelper.startPage(page, limit);
        if(keyword != null && !keyword.trim().equals("")) {
            list = userMapper.findUserByLikeName(keyword);
        }else {
            list = userMapper.findAllUser();
        }
        PageInfo<User> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
