package com.nccbc.digitalfreight.dao;

import com.nccbc.digitalfreight.model.GasBill;

import java.util.List;

public interface GasBillDao {
    List<GasBill> findByOrderId(String id);
    boolean addGasBill(GasBill gasBill);
}
