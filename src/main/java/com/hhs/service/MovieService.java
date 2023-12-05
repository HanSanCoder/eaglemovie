package com.hhs.service;

import com.hhs.entity.Movie;

import java.util.List;

public interface MovieService {
    int deleteByPrimaryKey(Long movieId);
    int deleteMovieCommentCount(Long movie_id);
    int insert(Movie record);

    int insertSelective(Movie record);
    Movie findMovieByName(String movie_name);


    Movie selectByPrimaryKey(Long movieId);
    int addMovieCommentCount(long movie_id);

    int updateByPrimaryKeySelective(Movie record);

    int updateByPrimaryKey(Movie record);
    List<Movie> findMoviesByName(String name);

    List<Movie> findMovieLikeType(String type);
    List<Movie> findMovieByDate();

    List<Movie> findMovieByCount();
    List<Movie> findMovieByScore();
    List<Movie> sortMovieByBoxOffice();
    List<Movie> findAllMovie(int movie_state);
    Integer changeMovieBoxOffice(float movie_boxOffice, long movie_id);
}
