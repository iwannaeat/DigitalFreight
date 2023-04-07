package com.nccbc.digitalfreight.service;

import com.nccbc.digitalfreight.dao.GasBillDao;
import com.nccbc.digitalfreight.model.GasBill;
import com.nccbc.digitalfreight.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: DigitalFreight
 * @description: GasBillDao Impl
 * @author: Haochen Ren
 * @create: 2023-03-06 13:50
 **/
@Service("GasBillDaoImpl")
public class GasBillService implements GasBillDao {
    @Autowired
    private JdbcTemplate jdbcTemplate; //jdbc连接工具类

    @Override
    public List<GasBill> findByOrderId(String uuid) {
        String sql = "select * from gas_bill where order_uuid='" + uuid + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<GasBill>(GasBill.class));
    }

    @Override
    public boolean addGasBill(GasBill gasBill) {
        String sql = "insert into gas_bill(uuid, cost, `type`, `timestamp`, order_uuid, block_height, block_hash, tx_hash)values(?,?,?,?,?,?,?,?)";
        Object[] params = {gasBill.getUuid(), gasBill.getCost(), gasBill.getType(), gasBill.getTimestamp(),
        gasBill.getOrder_uuid(), gasBill.getBlock_height(), gasBill.getBlock_hash(), gasBill.getTx_hash()};
        boolean res = jdbcTemplate.update(sql, params)>0;
        String sql1 = "update tb_order set bill_uploaded=? where uuid=?";
        Object[] param1 = {true, gasBill.getOrder_uuid()};
        boolean res1 = jdbcTemplate.update(sql1, param1)>0;

        String sql2 = "update tb_order set gas_bill=gas_bill+? where uuid=?";
        Object[] param2 = {gasBill.getCost(), gasBill.getOrder_uuid()};
        boolean res2 = jdbcTemplate.update(sql2, param2)>0;

        System.out.println(res2);
        return res;
    }
}
