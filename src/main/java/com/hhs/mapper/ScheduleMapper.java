package com.hhs.mapper;

import com.hhs.entity.Schedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleMapper {
    int deleteByPrimaryKey(Long scheduleId);
    Integer delScheduleRemain(long schedule_id);

    int insert(Schedule record);

    int insertSelective(Schedule record);
    List<Schedule> findScheduleByCinemaAndMovie(@Param("cinema_id")Long cinema_id, @Param("movie_id")Long movie_id);

    Schedule selectByPrimaryKey(Long scheduleId);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);

    List<Schedule> findAllScheduleByState(int schedule_state);

    List<Schedule> findAllSchedule();
    List<Schedule> findScheduleByMovieName(String movie_name);

    List<Schedule> findOffScheduleByMovieName(String movie_name);
    List<Schedule> findScheduleByCinemaAndMovieAndHall(@Param("hall_id")long hall_id,@Param("cinema_id")long cinema_id,@Param("movie_id")long movie_id);
}