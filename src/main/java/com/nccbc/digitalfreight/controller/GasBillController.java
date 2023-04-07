package com.nccbc.digitalfreight.controller;

import com.nccbc.digitalfreight.aelf.schemas.SendTransactionOutput;
import com.nccbc.digitalfreight.aelf.schemas.TransactionResultDto;
import com.nccbc.digitalfreight.model.AElfChain;
import com.nccbc.digitalfreight.model.GasBill;
import com.nccbc.digitalfreight.response.Result;
import com.nccbc.digitalfreight.service.AElfService;
import com.nccbc.digitalfreight.service.GasBillService;
import com.nccbc.digitalfreight.utils.ChainHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: DigitalFreight
 * @description: GasBillController
 * @author: Haochen Ren
 * @create: 2023-03-06 13:57
 **/
@Api(tags = "GasBillService")
@RestController
@RequestMapping(value = "/digitalfreight/gasbill")
//@CrossOrigin
public class GasBillController extends BaseController{
    @Autowired
    private GasBillService gasBillService;

    private final AElfService aelfService = new AElfService();

    @ApiOperation(value = "获取某订单的油票")
    @GetMapping("/ordergasbill")
    public Result findByOrderId1(@ApiParam(name="order_uuid",value="订单uuid",required=true)
            @RequestParam(value = "order_uuid") String uuid) {
        try {
            List<GasBill> gasBillList = gasBillService.findByOrderId(uuid);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("gasBills", gasBillList);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "上传油票")
    @GetMapping("/addgasbill")
    public Result addOrder(@ApiParam(name="uuid",value="油票uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="cost",value="int值，油票上的钱数，单位为分")@RequestParam(value = "cost") int cost,
                           @ApiParam(name="type",value="油票类型，0为油票，1为电票")@RequestParam(value = "type") String type, @ApiParam(name="timestamp",value="油票时间戳")@RequestParam(value = "timestamp") String timestamp,
                           @ApiParam(name="order_uuid",value="油票对应订单的uuid")@RequestParam(value = "order_uuid") String order_uuid) {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(sdf.format(System.currentTimeMillis()));
            GasBill gasBill = new GasBill();
            gasBill.setCost(cost);
            gasBill.setUuid(uuid);
            gasBill.setType(type);
            gasBill.setTimestamp(timestamp);
            gasBill.setOrder_uuid(order_uuid);

            String string = uuid + type + cost + timestamp + order_uuid;
            SendTransactionOutput output = aelfService.SaveDataOnChain(ChainHelper.sha_func(string,"SHA-256"), AElfChain.getInstance().getPrivateKey());
            TransactionResultDto transactionResult;
            while (true) {
                transactionResult = AElfChain.getInstance().getClient().getTransactionResult(output.getTransactionId());
                if ("MINED".equals(transactionResult.getStatus())) {
                    System.out.println("transactionResult:"+transactionResult.getBlockHash());

                    gasBill.setTx_hash(transactionResult.getTransactionId());
                    gasBill.setBlock_height((int) transactionResult.getBlockNumber());
                    gasBill.setBlock_hash(AElfChain.getInstance().getClient().getBlockByHeight(gasBill.getBlock_height()).getBlockHash());
                    // 当状态为MINED表示执行成功，直接返回
                    break;
                } else if ("PENDING".equals(transactionResult.getStatus())) {
                    // 当状态为PENDING表示还未获取到结果，等待
                    Thread.sleep(300);
                }
            }



//            gasBill.setBlock_hash(ChainHelper.createUUID(64));
//            gasBill.setTx_hash(ChainHelper.createUUID(64));
//            gasBill.setBlock_height(ChainHelper.getDifferHour(date) * 5);


            boolean res = gasBillService.addGasBill(gasBill);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
