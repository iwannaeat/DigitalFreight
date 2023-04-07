package com.nccbc.digitalfreight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: DigitalFreight
 * @description: JavaBean GasBill
 * @author: Haochen Ren
 * @create: 2023-03-06 13:42
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasBill {
    private int id;
    private String img_path;
    private String uuid;
    private int cost;
    private String type;
    private String timestamp;
    private String order_uuid;
    private int block_height;
    private String block_hash;
    private String tx_hash;
}
