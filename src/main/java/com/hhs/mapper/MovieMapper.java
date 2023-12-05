package com.hhs.mapper;

import com.hhs.entity.Movie;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieMapper {
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

    List<Movie> findAllMovie(int movie_state);

    List<Movie> sortMovieByBoxOffice();
    Integer changeMovieBoxOffice(@Param("movie_boxOffice")float movie_boxOffice, @Param("movie_id")long movie_id);
}