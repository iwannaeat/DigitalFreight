package com.nccbc.digitalfreight.dao;

import com.nccbc.digitalfreight.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderDao {
    List<Order> getAll();
    List<Order> findByCompanyId(String id);
    List<Order> findByDriverId(String id);
    boolean addOrder(Order order);
    boolean updateByStatus(String id, String status);
    boolean updateByGasBill(String id);
    boolean setReimbursed(String id, boolean isReimbursed);
    boolean setApproved(String id, boolean isApproved);
    boolean setUploaded(String id, boolean isUploaded);
    boolean setOrderBegin(String id, String time);
}
