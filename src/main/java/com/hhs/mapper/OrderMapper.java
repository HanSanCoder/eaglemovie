package com.hhs.mapper;

import com.hhs.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    List<Order> findOrderByScheduleId(Long schedule_id);
    List<Order> findOrderByUserName(String username);

    List<Order> findRefundOrderByUserName(String username);
    List<Order> findAllOrders();
    List<Order> findOrdersByState(String order_state);

    Integer updateOrderStateToRefund(String order_id);//申请退票
    Integer updateOrderStateToRefunded(String order_id);//同意退票
}