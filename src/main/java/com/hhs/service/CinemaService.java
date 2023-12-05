package com.hhs.service;

import com.hhs.entity.Cinema;

import java.util.List;

public interface CinemaService {

    int deleteByPrimaryKey(Long cinemaId);

    int insert(Cinema record);

    int insertSelective(Cinema record);

    Cinema selectByPrimaryKey(Long cinemaId);
    List<Cinema> findAllCinema();

    int updateByPrimaryKeySelective(Cinema record);

    int updateByPrimaryKey(Cinema record);
    List<Cinema> findCinemaByMovieId(Long movie_id);

}
