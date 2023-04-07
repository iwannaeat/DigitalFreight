package com.nccbc.digitalfreight.controller;

import com.nccbc.digitalfreight.model.AElfChain;
import com.nccbc.digitalfreight.response.Result;
import com.nccbc.digitalfreight.service.AElfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Api(tags = "AElfService")
@RestController
@RequestMapping(value = "/digitalfreight/test")
public class AElfServiceController extends BaseController {

    @ApiOperation(value = "获取块高")
    //@ApiImplicitParam(name = "name", value = "演员姓名", paramType = "String")
    @PostMapping("/getBlockHeight")
    public Result getBlockHeight(){
        long blockHeight;
        try {
            blockHeight = AElfChain.getInstance().getClient().getBlockHeight();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.successData(blockHeight);
    }
}
