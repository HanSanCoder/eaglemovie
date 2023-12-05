package com.hhs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhs.constant.CodeType;
import com.hhs.entity.Cinema;
import com.hhs.entity.Movie;
import com.hhs.mapper.CinemaMapper;
import com.hhs.mapper.MovieMapper;
import com.hhs.service.CinemaService;
import com.hhs.service.MovieService;
import com.hhs.utils.UUIDUtil;
import com.zaxxer.hikari.metrics.dropwizard.CodahaleHealthChecker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 15:01
 * @description：TODO
 */
@Controller
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    CinemaService cinemaService;
    // 1.通过电影名和电影类型查找
    // 2.实现增、删、改功能
    @RequestMapping("/findAllMovie")
    @ResponseBody
    public JSONObject findAllMovie() {
        JSONObject obj = new JSONObject();
        return obj;
    }

    @RequestMapping("/findMovieById")
    @ResponseBody
    public JSONObject findMovieById(@RequestParam("movie_id") long movie_id) {
        JSONObject obj = new JSONObject();
        Movie movie = movieService.selectByPrimaryKey(movie_id);
        List<Cinema> list = cinemaService.findCinemaByMovieId(movie_id);
        obj.put("code", 0);
        obj.put("data", movie);
        obj.put("cinemaList", list);
        obj.put("cinemaCount", list.size());
        return obj;
    }

    @RequestMapping("/findMoviesByName")
    @ResponseBody
    public JSONObject findMoviesByName(@RequestParam("name") String name) {
        JSONObject obj = new JSONObject();
        List<Movie> list = movieService.findMoviesByName(name);
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("data", list);
        obj.put("count", list.size());
        return obj;
    }

    @RequestMapping("/findMoviesByType")
    @ResponseBody
    public JSONObject findMoviesByType(@RequestParam("type") String type) {
        JSONObject obj = new JSONObject();
        List<Movie> list = movieService.findMovieLikeType(type);
        obj.put("code", 0);
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }

    @RequestMapping("/sortAllMovies")
    @ResponseBody
    public JSONObject sortAllMovies(@RequestParam("order") String order){
        JSONObject obj = new JSONObject();
        List<Movie> list = new ArrayList<>();
        switch(order) {
            case "热门":
                list = movieService.findMovieByCount();
                break;
            case "时间":
                list = movieService.findMovieByDate();
                break;
            case "评价":
                list = movieService.findMovieByScore();
                break;
        }
        obj.put("code", 0);
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }

    @RequestMapping("/deleteMovie")
    @ResponseBody
    public JSONObject deleteMovie(@RequestParam("movie_id") long movie_id) {
        JSONObject obj = new JSONObject();
        Integer res = movieService.deleteByPrimaryKey(movie_id);
        if (res > 0) {
            obj.put("code", 0);
            obj.put("msg", "下架成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "下架失败~");
        }
        return obj;
    }

    @RequestMapping("/addMovie")
    @ResponseBody
    public JSONObject addMovie(@RequestParam(value = "file", required = false)MultipartFile file, Movie movie, HttpServletRequest request) throws IOException {
        String str = file.getOriginalFilename();
        System.out.println("file:" + str);
        String name = UUIDUtil.getUUID() + str.substring(str.lastIndexOf("."));
        System.out.println("name:" + name);
        String path = request.getServletContext().getRealPath("/upload/movies") + "/" + name;
        System.out.println("path:" + path);
        String filePath = "../upload/movies" + name;
        movie.setMovie_picture(filePath);
        Date date = new Date();
        java.sql.Date releaseDate = new java.sql.Date(date.getYear(), date.getMonth(), date.getDay());
        float score = 2.5F;
        movie.setMovie_score(score);
        movie.setReleaseDate(releaseDate);
        Integer res = movieService.insertSelective(movie);
        JSONObject obj = new JSONObject();
        if (res > 0) {
            file.transferTo(new File(path));
            obj.put("code", 0);
            obj.put("msg", "添加成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "添加失败~杂鱼");
        }
        return obj;
    }

    @RequestMapping("/updateMovie")
    @ResponseBody
    public JSONObject updateMovie(@RequestParam(value = "file", required = false)MultipartFile file, Movie movie, HttpServletRequest request) throws IOException {
        JSONObject obj = new JSONObject();
        if (file != null) {
            String str = file.getOriginalFilename();
            System.out.println("file:" + file);
            String name = UUIDUtil.getUUID() + str.substring(str.lastIndexOf("."));
            System.out.println("name:" + name);
            String path = request.getServletContext().getRealPath("/upload/movies") + "/" + name;
            System.out.println("path:" + path);
            String filePath = "../upload/movies" + name;
            file.transferTo(new File(path));
            System.out.println("文件写入成功，Path" + path);
            movie.setMovie_picture(filePath);
        }else {
            System.out.println("What are you doing 何汉叁");
        }
        Integer res = movieService.updateByPrimaryKeySelective(movie);
        if (res > 0) {
            obj.put("code", 0);
            obj.put("msg", "修改成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "修改失败！杂鱼");
        }
        return obj;
    }

    @RequestMapping("/findAllMovies")
    @ResponseBody
    public JSONObject findAllMovies() {
        JSONObject obj = new JSONObject();
        List<Movie> list = movieService.findAllMovie(1);
        List<Movie> upcomingList = movieService.findAllMovie(0);
        List<Movie> offList = movieService.sortMovieByBoxOffice();
        String type[] = {"喜剧","动作","爱情","动画","科幻","惊悚","冒险","犯罪","悬疑"};
        ArrayList<Object> typeArr = new ArrayList<Object>();
        for(int i = 0;i < type.length;i++) {
            List<Movie> movieList = this.movieService.findMovieLikeType(type[i]);
            float boxOffice = 0;
            for(int j = 0; j < movieList.size();j++) {
                boxOffice += movieList.get(j).getMovie_boxOffice();
            }
            JSONObject typeJson = new JSONObject();
            typeJson.put(type[i], boxOffice);
            typeArr.add(typeJson);
        }
        obj.put("code", 0);
        obj.put("count", list.size());
        obj.put("upcomingCount",upcomingList.size());
        obj.put("data", list);
        obj.put("data1", upcomingList);
        obj.put("sort", offList);
        obj.put("type", typeArr);
        return obj;
    }
}
