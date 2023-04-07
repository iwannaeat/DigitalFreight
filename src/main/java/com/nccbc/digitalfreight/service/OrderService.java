package com.nccbc.digitalfreight.service;

import com.nccbc.digitalfreight.dao.OrderDao;
import com.nccbc.digitalfreight.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @program: DigitalFreight
 * @description: OrderDao Impl
 * @author: Haochen Ren
 * @create: 2023-03-03 21:29
 **/

@Service("OrderDaoImpl")
public class OrderService implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate; //jdbc连接工具类

    @Override
    public List<Order> getAll() {
        String sql = "select * from tb_order";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
    }

    @Override
    public List<Order> findByCompanyId(String uuid) {
        String sql = "select * from tb_order where company_uuid='" + uuid + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
    }

    @Override
    public List<Order> findByDriverId(String uuid) {
        String sql = "select * from tb_order where driver_uuid ='" + uuid + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
    }

    @Override
    public boolean addOrder(Order order) {
        String sql = "insert into tb_order(uuid, status, is_reimbursed, is_approved, original, " +
                "destination, company_uuid, driver_uuid, create_time," +
                "distance, estimate_cost, truck_type, gas_bill, bill_uploaded, deadline, block_height, block_hash, tx_hash)values(?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {order.getUuid(), order.getStatus(), order.isIs_reimbursed(),
                order.isIs_approved(), order.getOriginal(), order.getDestination(), order.getCompany_uuid(),
                order.getDriver_uuid(), order.getCreate_time(), order.getDistance(), order.getEstimate_cost(),
                order.getTruck_type(), order.getGas_bill(), order.isBill_uploaded(), order.getDeadline(),
        order.getBlock_height(), order.getBlock_hash(), order.getTx_hash()};
        return jdbcTemplate.update(sql, params)>0;
    }

    @Override
    public boolean updateByStatus(String id, String status) {
        String sql = "update tb_order set status=? where uuid=?";
        Object[] params = {status, id};
        return jdbcTemplate.update(sql, params)>0;
    }

    @Override
    public boolean updateByGasBill(String id) {
        String sql = "select * from gas_bill where order_uuid=?";
        return false;
    }

    @Override
    public boolean setReimbursed(String id, boolean isReimbursed) {
        String sql = "update tb_order set is_reimbursed=? where uuid=?";
        Object[] params = {isReimbursed, id};
        return jdbcTemplate.update(sql, params)>0;
    }

    @Override
    public boolean setApproved(String id, boolean isApproved) {
        String sql = "update tb_order set is_approved=? where uuid=?";
        Object[] params = {isApproved, id};
        return jdbcTemplate.update(sql, params)>0;
    }

    @Override
    public boolean setUploaded(String id, boolean isUploaded) {
        String sql = "update tb_order set bill_uploaded=? where uuid=?";
        Object[] params = {isUploaded, id};
        return jdbcTemplate.update(sql, params)>0;
    }

    @Override
    public boolean setOrderBegin(String id, String timestamp) {
        String sql = "update tb_order set start_time=? where uuid=?";
        Object[] params = {timestamp, id};
        return jdbcTemplate.update(sql, params)>0;
    }
}
