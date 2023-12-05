package com.hhs.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hhs.constant.CodeType;
import com.hhs.entity.*;
import com.hhs.service.CinemaService;
import com.hhs.service.HallService;
import com.hhs.service.MovieService;
import com.hhs.service.ScheduleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/30 12:55
 * @description：场次控制作为连接影院座位和电影的重要实体
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private MovieService movieService;
    @Resource
    private CinemaService cinemaService;
    @Resource
    private HallService hallService;

    @RequestMapping("/findScheduleById")
    public JSONObject findScheduleById(@RequestParam("schedule_id") long schedule_id) {
        JSONObject obj = new JSONObject();
        Schedule schedule = scheduleService.selectByPrimaryKey(schedule_id);
        obj.put("code", 0);
        obj.put("data", schedule);
        return obj;
    }

    @RequestMapping("/findAllScheduleByState")
    public JSONObject findAllScheduleByState(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit, @RequestParam("schedule_state")int schedule_state) {
        JSONObject obj = new JSONObject();
        PageInfo<Schedule> info = scheduleService.findAllScheduleByState(page, limit, schedule_state);
        ArrayList<Integer> incomeArr = new ArrayList<>();
        for (int i = 0; i < info.getList().size(); i++) {
            List<Order> orderList = info.getList().get(i).getOrderList();
            int income = 0;
            for (int j = 0; j < orderList.size(); j++) {
                income += orderList.get(j).getOrder_price();
            }
            incomeArr.add(income);
        }
        obj.put("code", 0);
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        obj.put("income", incomeArr);
        return obj;
    }

    @RequestMapping("/findAllSchedule")
    public JSONObject findAllSchedule(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit) {
        JSONObject obj = new JSONObject();
        PageInfo<Schedule> info = scheduleService.findAllSchedule(page, limit);
        List<Movie> movieList = movieService.findAllMovie(1);
        List<Cinema> cinemaList = cinemaService.findAllCinema();
        ArrayList<String> movieArr = new ArrayList<String>();
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int j = 0;j < info.getList().size();j++) {
            List<Order> orderList = info.getList().get(j).getOrderList();
            int income = 0;
            for(int i = 0;i < orderList.size();i++) {
                income += orderList.get(i).getOrder_price();
            }
            incomeArr.add(income);
        }
        for(int i = 0;i < movieList.size();i++) {
            movieArr.add(movieList.get(i).getMovie_cn_name());
        }
        ArrayList<Object> cinema = new ArrayList<>();
        for(int i = 0;i < cinemaList.size();i++) {
            JSONObject cinemaObj = new JSONObject();
            List<Hall> hallList = hallService.findHallByCinemaId(cinemaList.get(i).getCinema_id());
            ArrayList<String> hallArr = new ArrayList<String>();
            for(int j = 0;j < hallList.size();j++) {
                hallArr.add(hallList.get(j).getHall_name());
            }
            cinemaObj.put(cinemaList.get(i).getCinema_name(), hallList);
            cinema.add(cinemaObj);
        }
        obj.put("code", 0);
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        obj.put("movieName", movieArr);
        obj.put("cinema", cinema);
        obj.put("income", incomeArr);
        return obj;
    }

    @RequestMapping("/findScheduleByMovieName")
    public JSONObject findScheduleByMovieName(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit, @RequestParam("movie_name")String movie_name) {
        JSONObject obj = new JSONObject();
        PageInfo<Schedule> info = scheduleService.findScheduleByMovieName(page,limit,movie_name);
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int j = 0;j < info.getList().size();j++) {
            List<Order> orderList = info.getList().get(j).getOrderList();
            int income = 0;
            for(int i = 0;i < orderList.size();i++) {
                income += orderList.get(i).getOrder_price();
            }
            incomeArr.add(income);
        }
        obj.put("code", 0);
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        obj.put("income", incomeArr);
        return obj;
    }
    @RequestMapping("/findOffScheduleByMovieName")
    public JSONObject findOffScheduleByMovieName(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit, @RequestParam("movie_name")String movie_name) {
        JSONObject obj = new JSONObject();
        PageInfo<Schedule> info = scheduleService.findOffScheduleByMovieName(page,limit,movie_name);
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int j = 0;j < info.getList().size();j++) {
            List<Order> orderList = info.getList().get(j).getOrderList();
            int income = 0;
            for(int i = 0;i < orderList.size();i++) {
                income += orderList.get(i).getOrder_price();
            }
            incomeArr.add(income);
        }
        obj.put("code", 0);
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        obj.put("income", incomeArr);
        return obj;
    }

    @RequestMapping("/findScheduleByCinemaAndMovie")
    public JSONObject findScheduleByCinemaAndMovie(@RequestParam("cinema_id")long cinema_id, @RequestParam("movie_id")long movie_id){
        JSONObject obj = new JSONObject();
        List<Schedule> schedule = scheduleService.findScheduleByCinemaIdAndMovieId(cinema_id, movie_id);
        obj.put("code", 0);
        obj.put("data", schedule);
        return obj;
    }
    @RequestMapping("/addSchedule")
    public JSONObject addSchedule(@RequestParam("movie_name")String movie_name, @RequestParam("hall_name")String hall_name, @RequestParam("cinema_name")String cinema_name,
                                  @RequestParam("schedule_price")int schedule_price, @RequestParam("schedule_startTime")String schedule_startTime) {
        JSONObject obj = new JSONObject();
        Schedule schedule = new Schedule();
        Hall hall = hallService.findHallByCinemaAndHallName(cinema_name, hall_name);
        schedule.setMovie_id(movieService.findMovieByName(movie_name).getMovie_id());
        schedule.setHall_id(hall.getHall_id());
        schedule.setHall_id(hall.getHall_id());
        schedule.setSchedule_price(schedule_price);
        schedule.setSchedule_startTime(schedule_startTime);
        schedule.setSchedule_remain(hall.getHall_capacity());
        Integer res = scheduleService.insertSelective(schedule);
        if (res > 0) {
            obj.put("code", 0);
            obj.put("msg", "增加成功~");
        }else {

            obj.put("code", 200);
            obj.put("msg", "增加失败~");
        }
        return obj;
    }

    @RequestMapping("/updateSchedulePrice")
    public JSONObject updateSchedulePrice(@RequestParam("schedule_id")long schedule_id, @RequestParam("schedule_price")int schedule_price) {
        JSONObject obj = new JSONObject();
        Schedule schedule = new Schedule();
        schedule.setSchedule_id(schedule_id);
        schedule.setSchedule_price(schedule_price);
        Integer res = scheduleService.updateByPrimaryKeySelective(schedule);
        if (res > 0) {
            obj.put("code", 0);
            obj.put("msg", "修改成功~");
        }else {

            obj.put("code", 0);
            obj.put("msg", "修改失败~");
        }
        return obj;
    }
    @RequestMapping("/offlineSchedule")
    public JSONObject deleteSchedule(@RequestParam("schedule_id")long schedule_id) {
        JSONObject obj = new JSONObject();
        Integer res = scheduleService.deleteByPrimaryKey(schedule_id);
        if(res > 0) {
            obj.put("code", 0);
            obj.put("msg", "下架成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "下架失败~");
        }
        return obj;
    }

}
