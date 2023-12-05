package com.hhs.service;

import com.github.pagehelper.PageInfo;
import com.hhs.entity.Order;

import java.util.List;

public interface OrderService {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    List<Order> findOrderByScheduleId(Long schedule_id);

    PageInfo<Order> findOrderByUserName(Integer page, Integer limit, String username);
    List<Order> findRefundOrderByUserName(String username);

    List<Order> findAllOrders();
    PageInfo<Order> findAllOrdersBySplitPage(Integer page, Integer limit, String keyword);

    PageInfo<Order> findOrdersByState(Integer page, Integer limit, String order_state);
    Integer updateOrderStateToRefund(String order_id);//申请退票
    Integer updateOrderStateToRefunded(String order_id);//同意退票
}
