package com.nccbc.digitalfreight.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: DigitalFreight
 * @description: JavaBean Order
 * @author: Haochen Ren
 * @create: 2023-03-02 20:51
 **/
@Data
public class Order {
    private int id;
    private String uuid;
    private String status;
    @JSONField(name = "is_reimbursed")
    private boolean is_reimbursed;
    @JSONField(name = "is_approved")
    private boolean is_approved;
    private String original;
    private String destination;
    private String company_uuid;
    private String driver_uuid;
    private String create_time;
    private String finish_time;
    private String start_time;
    private Double distance;
    private Double estimate_cost;
    private String truck_type;
    private int gas_bill;
    private boolean bill_uploaded;
    private String deadline;

    private int block_height;
    private String block_hash;
    private String tx_hash;

    public int getBlock_height() {
        return block_height;
    }

    public void setBlock_height(int block_height) {
        this.block_height = block_height;
    }

    public String getBlock_hash() {
        return block_hash;
    }

    public void setBlock_hash(String block_hash) {
        this.block_hash = block_hash;
    }

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIs_reimbursed() {
        return is_reimbursed;
    }

    public void setIs_reimbursed(boolean is_reimbursed) {
        this.is_reimbursed = is_reimbursed;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(String company_uuid) {
        this.company_uuid = company_uuid;
    }

    public String getDriver_uuid() {
        return driver_uuid;
    }

    public void setDriver_uuid(String driver_uuid) {
        this.driver_uuid = driver_uuid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getEstimate_cost() {
        return estimate_cost;
    }

    public void setEstimate_cost(Double estimate_cost) {
        this.estimate_cost = estimate_cost;
    }

    public String getTruck_type() {
        return truck_type;
    }

    public void setTruck_type(String truck_type) {
        this.truck_type = truck_type;
    }

    public int getGas_bill() {
        return gas_bill;
    }

    public void setGas_bill(int gas_bill) {
        this.gas_bill = gas_bill;
    }

    public boolean isBill_uploaded() {
        return bill_uploaded;
    }

    public void setBill_uploaded(boolean bill_uploaded) {
        this.bill_uploaded = bill_uploaded;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
