package com.hhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhs.entity.Hall;
import com.hhs.entity.Order;
import com.hhs.entity.Schedule;
import com.hhs.mapper.*;
import com.hhs.service.ScheduleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 14:32
 * @description：TODO
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private HallMapper hallMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private CinemaMapper cinemaMapper;
    @Override
    public int deleteByPrimaryKey(Long scheduleId) {
        return scheduleMapper.deleteByPrimaryKey(scheduleId);
    }

    @Override
    public Integer delScheduleRemain(long schedule_id) {
        return scheduleMapper.delScheduleRemain(schedule_id);
    }

    @Override
    public int insert(Schedule record) {
        return 0;
    }

    @Override
    public int insertSelective(Schedule record) {
        return scheduleMapper.insertSelective(record);
    }

    @Override
    public Schedule selectByPrimaryKey(Long scheduleId) {
        Schedule schedule = scheduleMapper.selectByPrimaryKey(scheduleId);
        Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
        hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
        schedule.setSchedule_hall(hall);
        schedule.setSchedule_movie(movieMapper.selectByPrimaryKey(schedule.getMovie_id()));
        List<Order> list = orderMapper.findOrderByScheduleId(scheduleId);
        schedule.setOrderList(list);
        return schedule;
    }

    @Override
    public int updateByPrimaryKeySelective(Schedule record) {
        return scheduleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Schedule record) {
        return scheduleMapper.updateByPrimaryKeySelective(record);
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public PageInfo<Schedule> findAllScheduleByState(Integer page, Integer limit, int schedule_state) {
        PageHelper.startPage(page, limit);
        List<Schedule> list = scheduleMapper.findAllScheduleByState(schedule_state);
        for (Schedule schedule: list) {
            Hall hall =hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieMapper.selectByPrimaryKey(schedule.getMovie_id()));
            List<Order> listOrder = orderMapper.findOrderByScheduleId(schedule.getSchedule_id());
            schedule.setOrderList(listOrder);
        }
        PageInfo<Schedule> info = new PageInfo<>(list);
        return info;
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public PageInfo<Schedule> findAllSchedule(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Schedule> list = scheduleMapper.findAllSchedule();
        for (Schedule schedule: list) {
            Hall hall =hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieMapper.selectByPrimaryKey(schedule.getMovie_id()));
            List<Order> listOrder = orderMapper.findOrderByScheduleId(schedule.getSchedule_id());
            schedule.setOrderList(listOrder);
        }
        PageInfo<Schedule> info = new PageInfo<>(list);
        return info;
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public PageInfo<Schedule> findScheduleByMovieName(Integer page, Integer limit, String movie_name) {
        PageHelper.startPage(page, limit);
        List<Schedule> list = scheduleMapper.findScheduleByMovieName(movie_name);
        for (Schedule schedule: list) {
            Hall hall =hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieMapper.selectByPrimaryKey(schedule.getMovie_id()));
            List<Order> listOrder = orderMapper.findOrderByScheduleId(schedule.getSchedule_id());
            schedule.setOrderList(listOrder);
        }
        PageInfo<Schedule> info = new PageInfo<>(list);
        return info;
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public PageInfo<Schedule> findOffScheduleByMovieName(Integer page, Integer limit, String movie_name) {
        PageHelper.startPage(page, limit);
        List<Schedule> list = scheduleMapper.findOffScheduleByMovieName(movie_name);
        for (Schedule schedule: list) {
            Hall hall =hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieMapper.selectByPrimaryKey(schedule.getMovie_id()));
            List<Order> listOrder = orderMapper.findOrderByScheduleId(schedule.getSchedule_id());
            schedule.setOrderList(listOrder);
        }
        PageInfo<Schedule> info = new PageInfo<>(list);
        return info;
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public List<Schedule> findScheduleByCinemaAndMovie(Long cinema_id, Long movie_id) {
        return scheduleMapper.findScheduleByCinemaAndMovie(cinema_id, movie_id);
    }

    /**
     * selectSeat界面提供接口
     * @param cinema_id
     * @param movie_id
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public List<Schedule> findScheduleByCinemaIdAndMovieId(long cinema_id, long movie_id) {
        List<Schedule> list = scheduleMapper.findScheduleByCinemaAndMovie(cinema_id, movie_id);
        for(Schedule schedule: list) {
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieMapper.selectByPrimaryKey(schedule.getMovie_id()));
        }
        return list;
    }
}
