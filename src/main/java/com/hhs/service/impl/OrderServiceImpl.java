package com.hhs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhs.entity.Hall;
import com.hhs.entity.Order;
import com.hhs.entity.Schedule;
import com.hhs.mapper.*;
import com.hhs.service.MovieService;
import com.hhs.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 14:31
 * @description：TODO
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private HallMapper hallMapper;
    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private MovieService movieService;
    @Override
    public int deleteByPrimaryKey(String orderId) {
        return orderMapper.deleteByPrimaryKey(orderId);
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public PageInfo<Order> findOrderByUserName(Integer page, Integer limit, String username) {
        PageHelper.startPage(page, limit);
        List<Order> list = orderMapper.findOrderByUserName(username);
        for(Order order: list) {
            order.setOrder_user(userMapper.selectByPrimaryKey(order.getUser_id()));
            Schedule schedule = scheduleMapper.selectByPrimaryKey(order.getSchedule_id());
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieService.selectByPrimaryKey(schedule.getMovie_id()));
            order.setOrder_schedule(schedule);
        }
        PageInfo<Order> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public int insert(Order record) {
        return 0;
    }

    @Override
    public int insertSelective(Order record) {
        return orderMapper.insertSelective(record);
    }
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Order selectByPrimaryKey(String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order != null) {
            order.setOrder_user(userMapper.selectByPrimaryKey(order.getUser_id()));
            Schedule schedule = scheduleMapper.selectByPrimaryKey(order.getSchedule_id());
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieService.selectByPrimaryKey(schedule.getMovie_id()));
            order.setOrder_schedule(schedule);
        }
        return order;
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return 0;
    }

    @Override
    public List<Order> findOrderByScheduleId(Long schedule_id) {
        return orderMapper.findOrderByScheduleId(schedule_id);
    }
    @Override
    public List<Order> findRefundOrderByUserName(String username) {
        List<Order> list = orderMapper.findRefundOrderByUserName(username);
        for (Order order: list) {
            order.setOrder_user(userMapper.selectByPrimaryKey(order.getUser_id()));
            Schedule schedule = scheduleMapper.selectByPrimaryKey(order.getSchedule_id());
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieService.selectByPrimaryKey(schedule.getMovie_id()));
            order.setOrder_schedule(schedule);
        }
        return list;
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> list = orderMapper.findAllOrders();
        for (Order order: list) {
            order.setOrder_user(userMapper.selectByPrimaryKey(order.getUser_id()));
            Schedule schedule = scheduleMapper.selectByPrimaryKey(order.getSchedule_id());
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieService.selectByPrimaryKey(schedule.getMovie_id()));
            order.setOrder_schedule(schedule);
        }
        return list;
    }

    @Override
    public PageInfo<Order> findAllOrdersBySplitPage(Integer page, Integer limit, String keyword) {
        PageHelper.startPage(page, limit);
        List<Order> list = new ArrayList<>();
        if (keyword != null && !keyword.trim().equals("")) {
            list = orderMapper.findRefundOrderByUserName(keyword);
        }else {
            list = orderMapper.findAllOrders();
        }
        for (Order order: list) {
            order.setOrder_user(userMapper.selectByPrimaryKey(order.getUser_id()));
            Schedule schedule = scheduleMapper.selectByPrimaryKey(order.getSchedule_id());
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieService.selectByPrimaryKey(schedule.getMovie_id()));
            order.setOrder_schedule(schedule);
        }
        PageInfo<Order> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public PageInfo<Order> findOrdersByState(Integer page, Integer limit, String order_state) {
        PageHelper.startPage(page, limit);
        List<Order> list = orderMapper.findOrdersByState(order_state);
        for (Order order: list) {
            order.setOrder_user(userMapper.selectByPrimaryKey(order.getUser_id()));
            Schedule schedule = scheduleMapper.selectByPrimaryKey(order.getSchedule_id());
            Hall hall = hallMapper.selectByPrimaryKey(schedule.getHall_id());
            hall.setHall_cinema(cinemaMapper.selectByPrimaryKey(hall.getCinema_id()));
            schedule.setSchedule_hall(hall);
            schedule.setSchedule_movie(movieService.selectByPrimaryKey(schedule.getMovie_id()));
            order.setOrder_schedule(schedule);
        }
        PageInfo<Order> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public Integer updateOrderStateToRefund(String order_id) {
        return orderMapper.updateOrderStateToRefund(order_id);
    }

    @Override
    public Integer updateOrderStateToRefunded(String order_id) {
        return orderMapper.updateOrderStateToRefunded(order_id);
    }

}
