package com.hhs.service;

import com.github.pagehelper.PageInfo;
import com.hhs.entity.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleService {
    int deleteByPrimaryKey(Long scheduleId);
    Integer delScheduleRemain(long schedule_id);
    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(Long scheduleId);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);
    PageInfo<Schedule> findAllScheduleByState(Integer page, Integer limit, int schedule_state);
    PageInfo<Schedule> findAllSchedule(Integer page, Integer limit);

    PageInfo<Schedule> findScheduleByMovieName(Integer page, Integer limit, String movie_name);
    PageInfo<Schedule> findOffScheduleByMovieName(Integer page, Integer limit, String movie_name);
    List<Schedule> findScheduleByCinemaAndMovie(Long cinema_id, Long movie_id);
    List<Schedule> findScheduleByCinemaIdAndMovieId(long cinema_id, long movie_id);
}

