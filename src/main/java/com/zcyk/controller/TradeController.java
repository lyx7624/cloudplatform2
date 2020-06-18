package com.zcyk.controller;

import com.zcyk.dao.TradeCompanyDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.Trade;
import com.zcyk.entity.Trade_company;
import com.zcyk.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行业
 * @author WuJieFeng
 * @date 2020/5/25 14:23
 */
@Slf4j
@RestController
@RequestMapping("trade")
public class TradeController {

    @Resource
    private TradeService tradeService;
//    @RequestMapping("/set")
//   public ResultData bindOrganization(){
//      return tradeService.bindOrganization();
//   }




    /**
     * 功能描述：获取授权列表
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 10:32
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("/{trade_id}")
    public ResultData getTradeBind(@PathVariable String trade_id){
        try {
            List<Trade> tradeBind = tradeService.getTradeBind(trade_id);
            return ResultData.SUCCESS(tradeBind);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED(e.getMessage());
        }

    }
    /**
     * 功能描述：添加行业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 15:18
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("/")
    public ResultData addTrade(@Valid Trade trade){
        try {
            return tradeService.addTrade(trade);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED(e.getMessage());
        }
    }
    /**
     * 功能描述：查询行业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 15:18
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("/trade")
    public ResultData getTrade(){
        try {
            List<Trade> trade = tradeService.getTrade();
            return ResultData.SUCCESS(trade);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED(e.getMessage());
        }
    }

    /**
     * 功能描述：查询单个行业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/2 14:57
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("/one/{trade_id}")
    public ResultData getOneTrade(@PathVariable String trade_id) throws Exception {
        try {
            Trade oneTrade = tradeService.getOneTrade(trade_id);
            return ResultData.SUCCESS(oneTrade);
        }catch (Exception e){
            throw new Exception("查询失败");
        }
    }
    /**
     * 功能描述：绑定单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 15:18
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("/bind")
    public ResultData bindTrade(Trade_company trade_company){
        try {
            return tradeService.bindOrganization(trade_company);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED(e.getMessage());
        }
    }
    /**
     * 功能描述：解绑行业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 15:31
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("/unbind")
    public ResultData unbindCompany(Trade_company trade_company){
        try {
            return tradeService.unbindCompany(trade_company);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED(e.getMessage());
        }
    }
    @PutMapping("/menu")
    public ResultData setTradeMenu(Trade trade){
        try {
            return tradeService.setTradeMenu(trade);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED(e.getMessage());
        }
    }



}
