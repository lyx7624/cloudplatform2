package com.zcyk.service;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.Trade;
import com.zcyk.entity.Trade_company;

import java.util.List;

public interface TradeService  {

    //新增行业
    ResultData addTrade(Trade trade);
    //查询行业
    List<Trade> getTrade();
    //查询单个行业信息
    Trade getOneTrade(String trade_id);
    //设置行业菜单
    ResultData setTradeMenu(Trade trade);
    //绑定行业
    ResultData bindOrganization(Trade_company trade_organization);
    //解绑行业
    ResultData unbindCompany(Trade_company trade_company);
    //获取授权列表
    List<Trade> getTradeBind(String trade_id);
    //判断行业管理员
    Trade_company isTradeManager(String company_id);
}
