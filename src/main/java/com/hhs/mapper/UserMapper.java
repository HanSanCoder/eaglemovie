package com.hhs.mapper;

import com.hhs.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    User selectByUserPwd(@Param("username")String username, @Param("password")String password);

    List<User> selectByUserName(String username);
    List<User> findAllUser();
    List<User> findUserByLikeName(String keyword);
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}