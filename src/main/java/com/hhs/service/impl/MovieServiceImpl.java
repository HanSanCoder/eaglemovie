package com.hhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hhs.entity.Movie;
import com.hhs.mapper.MovieMapper;
import com.hhs.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 14:30
 * @description：电影数据控制层
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieMapper movieMapper;
    @Override
    public int deleteByPrimaryKey(Long movieId) {
        return deleteByPrimaryKey(movieId);
    }

    @Override
    public int insert(Movie record) {
        return 0;
    }

    @Override
    public int insertSelective(Movie record) {
        return movieMapper.insertSelective(record);
    }

    @Override
    public Movie findMovieByName(String movie_name) {
        return movieMapper.findMovieByName(movie_name);
    }

    @Override
    public Movie selectByPrimaryKey(Long movieId) {
        return movieMapper.selectByPrimaryKey(movieId);
    }

    @Override
    public int updateByPrimaryKeySelective(Movie record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Movie record) {
        return 0;
    }

    @Override
    public List<Movie> findMoviesByName(String name) {
        return movieMapper.findMoviesByName(name);
    }

    @Override
    public List<Movie> findMovieLikeType(String type) {
        return movieMapper.findMovieLikeType(type);
    }

    @Override
    public List<Movie> findMovieByDate() {
        return movieMapper.findMovieByDate();
    }

    @Override
    public List<Movie> findMovieByCount() {
        return movieMapper.findMovieByCount();
    }

    @Override
    public List<Movie> findMovieByScore() {
        return movieMapper.findMovieByScore();
    }
    @Override
    public List<Movie> sortMovieByBoxOffice() {
        return movieMapper.sortMovieByBoxOffice();
    };

    @Override
    public List<Movie> findAllMovie(int movie_state) {
        return movieMapper.findAllMovie(movie_state);
    }

    @Override
    public int addMovieCommentCount(long movie_id) {
        return movieMapper.addMovieCommentCount(movie_id);
    }

    @Override
    public int deleteMovieCommentCount(Long movie_id) {
        return movieMapper.deleteMovieCommentCount(movie_id);
    }

    @Override
    public Integer changeMovieBoxOffice(float movie_boxOffice, long movie_id) {
        return movieMapper.changeMovieBoxOffice(movie_boxOffice, movie_id);
    }
}
