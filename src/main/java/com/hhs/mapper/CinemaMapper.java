package com.hhs.mapper;

import com.hhs.entity.Cinema;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CinemaMapper {
    int deleteByPrimaryKey(Long cinemaId);

    int insert(Cinema record);

    int insertSelective(Cinema record);

    Cinema selectByPrimaryKey(Long cinemaId);
    List<Cinema> findAllCinema();
    List<Cinema> findCinemaByMovieId(Long movie_id);
    int updateByPrimaryKeySelective(Cinema record);

    int updateByPrimaryKey(Cinema record);
}