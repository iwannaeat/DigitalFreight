package com.nccbc.digitalfreight.controller;

import com.nccbc.digitalfreight.aelf.schemas.SendTransactionOutput;
import com.nccbc.digitalfreight.aelf.schemas.TransactionResultDto;
import com.nccbc.digitalfreight.model.AElfChain;
import com.nccbc.digitalfreight.model.Order;
import com.nccbc.digitalfreight.response.Result;
import com.nccbc.digitalfreight.service.AElfService;
import com.nccbc.digitalfreight.service.OrderService;
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
 * @description: OrderController
 * @author: Haochen Ren
 * @create: 2023-03-04 15:13
 **/
@Api(tags = "OrderService")
@RestController
@RequestMapping(value = "/digitalfreight/order")
//@CrossOrigin
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    private final AElfService aelfService = new AElfService();

    @ApiOperation(value = "获取全部订单")
    @GetMapping("/getall")
    public Result getAllOrder() {
        try {
            List<Order> orderList = orderService.getAll();
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("orderlist", orderList);
            System.out.println(retMap);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "获取某司机的订单")
    @GetMapping("/driverorder")
    public Result findByDriverId(@ApiParam(name="driver_uuid",value="司机uuid")@RequestParam(value = "driver_uuid") String uuid) {
        try {
            List<Order> orderList = orderService.findByDriverId(uuid);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("orderlist", orderList);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "获取某公司的订单")
    @GetMapping("/companyorder")
    public Result findByCompanyId(@ApiParam(name="company_uuid",value="公司uuid")@RequestParam(value = "company_uuid") String uuid) {
        try {
            List<Order> orderList = orderService.findByCompanyId(uuid);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("orderlist", orderList);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "上传订单")
    @GetMapping("/addorder")
    public Result addOrder(@ApiParam(name="uuid",value="订单uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="original",value="始发地")@RequestParam(value = "original") String original,
                           @ApiParam(name="destination",value="目的地")@RequestParam(value = "destination") String destination, @ApiParam(name="distance",value="公里数")@RequestParam(value = "distance") Double distance,
                           @ApiParam(name="company_uuid",value="公司uuid")@RequestParam(value = "company_uuid") String company_uuid,
                           @ApiParam(name="deadline",value="截止时间")@RequestParam(value = "deadline") String deadline, @ApiParam(name="driver_uuid",value="司机uuid")@RequestParam(value = "driver_uuid") String driver_uuid) {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(sdf.format(System.currentTimeMillis()));
            Order order = new Order();
            order.setUuid(uuid);
            order.setStatus("0");
            order.setIs_reimbursed(false);
            order.setIs_approved(false);
            order.setOriginal(original);
            order.setDestination(destination);
            order.setCompany_uuid(company_uuid);
            order.setDriver_uuid(driver_uuid);
            order.setCreate_time(sdf.format(System.currentTimeMillis()));
            order.setDistance(distance);
            order.setEstimate_cost(distance * 1.5 * 100);
            order.setTruck_type("0");
            order.setBill_uploaded(false);
            order.setGas_bill(0);
            order.setDeadline(deadline);

            String string = uuid + original + destination + company_uuid + driver_uuid + order.getCreate_time()
                    + distance + order.getEstimate_cost() + deadline;

            SendTransactionOutput output = aelfService.SaveDataOnChain(ChainHelper.sha_func(string,"SHA-256"), AElfChain.getInstance().getPrivateKey());
            TransactionResultDto transactionResult;
            while (true) {
            transactionResult = AElfChain.getInstance().getClient().getTransactionResult(output.getTransactionId());
            if ("MINED".equals(transactionResult.getStatus())) {
                System.out.println("transactionResult:"+transactionResult.getBlockHash());

                order.setTx_hash(transactionResult.getTransactionId());
                order.setBlock_height((int) transactionResult.getBlockNumber());
                order.setBlock_hash(AElfChain.getInstance().getClient().getBlockByHeight(order.getBlock_height()).getBlockHash());
                // 当状态为MINED表示执行成功，直接返回
                break;
            } else if ("PENDING".equals(transactionResult.getStatus())) {
                // 当状态为PENDING表示还未获取到结果，等待
                Thread.sleep(300);
            }
        }
            System.out.println(order.getBlock_hash());
            System.out.println(order.getBlock_height());
            System.out.println(order.getTx_hash());
//            order.setBlock_hash(ChainHelper.createUUID(64));
//            order.setTx_hash(ChainHelper.createUUID(64));
//            order.setBlock_height(ChainHelper.getDifferHour(date) * 5);

            boolean res = orderService.addOrder(order);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "更改订单状态")
    @GetMapping("/setorderstatus")
    public Result setOrderStatus(@ApiParam(name="uuid",value="订单uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="status",value="订单状态，0未开始 1进行中 2已完成")@RequestParam(value = "status") String status) {
        try {
            boolean res = orderService.updateByStatus(uuid, status);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "更改订单报销状态")
    @GetMapping("/setorderreimbursed")
    public Result setOrderReimbursed(@ApiParam(name="uuid",value="订单uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="status",value="订单是否已报销")@RequestParam(value = "status") boolean is_reimbursed) {
        try {
            boolean res = orderService.setReimbursed(uuid, is_reimbursed);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "更改报销审核状态")
    @GetMapping("/setorderapproved")
    public Result setOrderApproved(@ApiParam(name="uuid",value="订单uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="status",value="订单报销是否已审核")@RequestParam(value = "status") boolean is_approved) {
        try {
            boolean res = orderService.setApproved(uuid, is_approved);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "更改订单油票上传状态")
    @GetMapping("/setorderuploaded")
    public Result setOrderUploaded(@ApiParam(name="uuid",value="订单uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="status",value="订单油票是否已上传")@RequestParam(value = "status") boolean is_uploaded) {
        try {
            boolean res = orderService.setUploaded(uuid, is_uploaded);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "设置订单开始时间")
    @GetMapping("/setorderbegin")
    public Result setOrderBegin(@ApiParam(name="uuid",value="订单uuid")@RequestParam(value = "uuid") String uuid, @ApiParam(name="timestamp",value="时间戳")@RequestParam(value = "timestamp") String timestamp) {
        try {
            boolean res = orderService.setOrderBegin(uuid, timestamp);
            Map<String, Object> retMap = new HashMap<>();

            retMap.put("result", res);

            return Result.successData(retMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
