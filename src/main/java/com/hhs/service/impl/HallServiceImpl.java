package com.hhs.service.impl;

import com.hhs.entity.Hall;
import com.hhs.mapper.HallMapper;
import com.hhs.service.HallService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 14:28
 * @description：TODO
 */
@Service
public class HallServiceImpl implements HallService {
    @Resource
    HallMapper hallMapper;
    @Override
    public int deleteByPrimaryKey(Long hallId) {
        return 0;
    }

    @Override
    public int insert(Hall record) {
        return 0;
    }

    @Override
    public int insertSelective(Hall record) {
        return 0;
    }

    @Override
    public Hall findHallByCinemaAndHallName(String cinema_name, String hall_name) {
        return hallMapper.findHallByCinemaAndHallName(cinema_name, hall_name);
    }

    @Override
    public Hall selectByPrimaryKey(Long hallId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Hall record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Hall record) {
        return 0;
    }

    @Override
    public List<Hall> findHallByCinemaId(Long cinema_id) {
        return hallMapper.findHallByCinemaId(cinema_id);
    }
}
