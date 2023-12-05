package com.hhs.service;

import com.hhs.entity.Hall;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HallService {
    int deleteByPrimaryKey(Long hallId);

    int insert(Hall record);

    int insertSelective(Hall record);

    Hall selectByPrimaryKey(Long hallId);

    int updateByPrimaryKeySelective(Hall record);

    int updateByPrimaryKey(Hall record);

    List<Hall> findHallByCinemaId(Long cinema_id);
    Hall findHallByCinemaAndHallName(String cinema_name, String hall_name);
}
