package com.hhs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.github.pagehelper.PageInfo;
import com.hhs.constant.CodeType;
import com.hhs.entity.Order;
import com.hhs.entity.User;
import com.hhs.service.MovieService;
import com.hhs.service.OrderService;
import com.hhs.service.ScheduleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/12/1 20:28
 * @description：TODO
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private ScheduleService scheduleService;//支付 退票 座位—+
    @Resource
    private MovieService movieService; //支付 退票 票房+-

    @RequestMapping("/findOrderById")
    public JSONObject findOrderById(@RequestParam("order_id")String order_id) {
        JSONObject obj = new JSONObject();
        Order order = orderService.selectByPrimaryKey(order_id);
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("data", order);
        return obj;
    }

    @RequestMapping("/findOrderByUserName")
    public JSONObject findOrderByUserName(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit, @RequestParam("user_name")String username) {
        PageInfo<Order> info = orderService.findOrderByUserName(page, limit, username);
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @RequestMapping("/findRefundOrderByUser")
    public JSONObject findRefundOrderByUser(@RequestParam("user_name")String username) {
        JSONObject obj = new JSONObject();
        List<Order> list = orderService.findRefundOrderByUserName(username);
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }
    @RequestMapping("/findAllOrders")
    public JSONObject findAllOrders() {
        JSONObject obj = new JSONObject();
        List<Order> list = orderService.findAllOrders();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }
    @RequestMapping("/findAllOrdersPage")
    public JSONObject findAllOrdersPage(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit, String keyword) {
        PageInfo<Order> info = orderService.findAllOrdersBySplitPage(page, limit, keyword);
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @RequestMapping("/findAllRefundOrders")
    public JSONObject findAllRefundOrders(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit", defaultValue = "10")Integer limit, String order_state) {
        JSONObject obj = new JSONObject();
        PageInfo<Order> info = orderService.findOrdersByState(page, limit, order_state);
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @RequestMapping("/buyTickets")
    public JSONObject buyTicket(@RequestParam("schedule_id")long schedule_id, @RequestParam("position[]")String[] position, @RequestParam("price")int price, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        JSONObject obj = new JSONObject();
        if(user == null) {
            obj.put("code", 200);
            obj.put("msg", "您未登录,登录后才可购票~");
        }else {
            int done = 0;
            int order_price = price / position.length;
            String user_id = "";
            switch(String.valueOf(user.getUser_id()).length()) {
                case 1: user_id = "000" + String.valueOf(user.getUser_id()); break;
                case 2: user_id = "00" + String.valueOf(user.getUser_id()); break;
                case 3: user_id = "0" + String.valueOf(user.getUser_id()); break;
                case 4: user_id = String.valueOf(user.getUser_id()); break;
            }
            for (int i = 0; i< position.length; i++) {
                Order order = new Order();
                String order_id = "";
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
                order_id += dateFormat.format(date);
                order_id += user_id;
                String index = "";
                switch(position[i].length()) {
                    case 4:
                        index = "0" + position[i].replaceAll("排", "0");
                        index = index.replaceAll("座", "");
                        break;
                    case 5:
                        if(position[i].charAt(2) >= 48 && position[i].charAt(2) <= 57) {
                            index = "0" + position[i].replaceAll("排", "");
                            index = index.replaceAll("座", "");
                        }else {
                            index = position[i].replaceAll("排", "0");
                            index = index.replaceAll("座", "");
                        }
                        break;
                    case 6:
                        index = position[i].replaceAll("排", "");
                        index = index.replaceAll("座", "");
                        break;
                }
                order_id += index;
                order.setOrder_id(order_id);
                order.setOrder_position(position[i]);
                order.setSchedule_id(schedule_id);
                order.setUser_id(user.getUser_id());
                order.setOrder_price(order_price);
                order.setOrder_time(new Date());
                Integer rs = orderService.insertSelective(order);
                Integer rs1 = scheduleService.delScheduleRemain(schedule_id);
                done++;
            }
            if (done == position.length) {
                float sum = (float)price/10000;
                Integer rs2 = this.movieService.changeMovieBoxOffice(sum, scheduleService.selectByPrimaryKey(schedule_id).getMovie_id());
                obj.put("code",0);
                obj.put("msg", "购票成功~");
            }
            else {
                obj.put("code",200);
                obj.put("msg", "购票失败~");
            }
        }
        return obj;
    }

    @RequestMapping("/applyForRefund")
    public JSONObject applyForRefund(@RequestParam("order_id")String order_id) {
        JSONObject obj = new JSONObject();
        Integer rs = orderService.updateOrderStateToRefund(order_id);
        if(rs > 0) {
            obj.put("code", 0);
            obj.put("msg", "退票申请已发送~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "操作失败~");
        }
        return obj;
    }

    @RequestMapping("/agreeForRefund")
    public JSONObject agreeForRefund(@RequestParam("order_id")String order_id) {
        JSONObject obj = new JSONObject();
        Integer res = orderService.updateOrderStateToRefunded(order_id);
        if(res > 0) {
            Order order = orderService.selectByPrimaryKey(order_id);
            int price = order.getOrder_price();
            long movie_id = order.getOrder_schedule().getMovie_id();
            Integer res2 = movieService.changeMovieBoxOffice((float) price / 10000, movie_id);
            obj.put("code", 0);
            obj.put("msg", "退票成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "退票失败~");
        }
        return obj;
    }
}
