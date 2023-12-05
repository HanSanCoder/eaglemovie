package com.hhs.mapper;

import com.hhs.entity.Hall;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallMapper {
    int deleteByPrimaryKey(Long hallId);

    int insert(Hall record);

    int insertSelective(Hall record);
    Hall findHallByCinemaAndHallName(@Param("cinema_name")String cinema_name, @Param("hall_name") String hall_name);

    Hall selectByPrimaryKey(Long hallId);

    int updateByPrimaryKeySelective(Hall record);

    int updateByPrimaryKey(Hall record);

    List<Hall> findHallByCinemaId(Long cinema_id);
}