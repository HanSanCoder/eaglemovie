package com.hhs.service.impl;

import com.hhs.entity.Cinema;
import com.hhs.entity.Hall;
import com.hhs.mapper.CinemaMapper;
import com.hhs.mapper.HallMapper;
import com.hhs.mapper.ScheduleMapper;
import com.hhs.service.CinemaService;
import com.hhs.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 14:26
 * @description：TODO
 */
@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaMapper cinemaMapper;

    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Override
    public int deleteByPrimaryKey(Long cinemaId) {
        return 0;
    }

    @Override
    public int insert(Cinema record) {
        return 0;
    }

    @Override
    public int insertSelective(Cinema record) {
        return 0;
    }

    @Override
    public Cinema selectByPrimaryKey(Long cinemaId) {
        return cinemaMapper.selectByPrimaryKey(cinemaId);
    }

    @Override
    public List<Cinema> findAllCinema() {
        return cinemaMapper.findAllCinema();
    }

    @Override
    public int updateByPrimaryKeySelective(Cinema record) {
        return cinemaMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Cinema record) {
        return 0;
    }

    @Override
    public List<Cinema> findCinemaByMovieId(Long movie_id) {
        List<Cinema> cinemaList = cinemaMapper.findCinemaByMovieId(movie_id);
        for(Cinema cinema : cinemaList) {
            List<Hall> hallList = hallMapper.findHallByCinemaId(cinema.getCinema_id());
            for (Hall hall : hallList) {
                hall.setScheduleList(scheduleMapper.findScheduleByCinemaAndMovieAndHall(hall.getHall_id(), hall.getCinema_id(), movie_id));
            }
            cinema.setHallList(hallList);
        }
        return cinemaList;
    }
}
