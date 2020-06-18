package com.zcyk.service.impl;

import com.zcyk.dao.CountyDao;
import com.zcyk.dao.TradeDao;
import com.zcyk.dao.TradeCompanyDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.County;
import com.zcyk.entity.Trade;
import com.zcyk.entity.Trade_company;
import com.zcyk.service.TradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author WuJieFeng
 * @date 2020/5/25 14:52
 */
@Transactional(rollbackFor = Exception.class)
@Service("TradeService")
public class TradeServiceImpl implements TradeService {
    @Resource
    private TradeDao tradeDao;

    @Resource
    private CountyDao countyDao;
    @Resource
    private TradeCompanyDao tradeCompanyDao;

    String menu = "[{\"name\":\"行管\",\"left\":\"\",\"children\":[{\"name\":\"视图\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"2D\"},{\"name\":\"\",\"left\":\"3D\"}]},{\"name\":\"用户\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"个人\"},{\"name\":\"\",\"left\":\"单位\"},{\"name\":\"\",\"left\":\"群组\"}]},{\"name\":\"行业\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"构件\"},{\"name\":\"\",\"left\":\"材料\"},{\"name\":\"\",\"left\":\"工人\"},{\"name\":\"\",\"left\":\"设备\"}]},{\"name\":\"管理\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"进度\"},{\"name\":\"\",\"left\":\"资金\"},{\"name\":\"\",\"left\":\"监管\"},{\"name\":\"\",\"left\":\"监控\"}]}]},{\"name\":\"区管\",\"left\":\"\",\"children\":[{\"name\":\"视图\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"2D\"},{\"name\":\"\",\"left\":\"3D\"}]},{\"name\":\"用户\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"个人\"},{\"name\":\"\",\"left\":\"单位\"},{\"name\":\"\",\"left\":\"群组\"}]},{\"name\":\"行业\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"构件\"},{\"name\":\"\",\"left\":\"材料\"},{\"name\":\"\",\"left\":\"工人\"},{\"name\":\"\",\"left\":\"设备\"}]},{\"name\":\"管理\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"进度\"},{\"name\":\"\",\"left\":\"资金\"},{\"name\":\"\",\"left\":\"监管\"},{\"name\":\"\",\"left\":\"监控\"}]}]},{\"name\":\"企业\",\"left\":\"\",\"children\":[{\"name\":\"视图\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"2D\"},{\"name\":\"\",\"left\":\"3D\"}]},{\"name\":\"用户\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"个人\"},{\"name\":\"\",\"left\":\"单位\"},{\"name\":\"\",\"left\":\"群组\"}]},{\"name\":\"行业\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"构件\"},{\"name\":\"\",\"left\":\"材料\"},{\"name\":\"\",\"left\":\"工人\"},{\"name\":\"\",\"left\":\"设备\"}]},{\"name\":\"管理\",\"left\":\"\",\"children\":[{\"name\":\"\",\"left\":\"进度\"},{\"name\":\"\",\"left\":\"资金\"},{\"name\":\"\",\"left\":\"监管\"},{\"name\":\"\",\"left\":\"监控\"}]}]}]";


    /**
     * 功能描述：新增行业
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/25 14:53
     * 参数：[ * @param null]
     * 返回值：
    */
    public ResultData addTrade(Trade trade){
        List<Trade> trades = tradeDao.selectAll();
        int size = trades.size()+1;
        String code = "0"+size;
        int i = tradeDao.insertSelective(trade.setId(UUID.randomUUID().toString())
                .setCreate_time(new Date())
                .setCode(code)
                .setPassword("123")
                .setMenu(menu));
        List<County> counties = countyDao.selectAll();
        for (int j = 0; j <counties.size() ; j++) {
            Trade_company trade_company = new Trade_company();
            tradeCompanyDao.insertSelective(trade_company.setId(UUID.randomUUID().toString())
                                                                         .setTrade_id(trade.getId())
                                                                         .setBind_status(0)
                                                                         .setCounty_id(counties.get(j).getId())
                                                                         .setTrade_code(counties.get(j).getCredit_code()+trade.getCode()));
        }
        if(i!=0){
            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }

    /**
     * 功能描述：查询行业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 10:52
     * 参数：[ * @param null]
     * 返回值：
    */
    public List<Trade> getTrade(){
        List<Trade> trades = tradeDao.getTradeAsc();
        return trades;
    }

    /**
     * 功能描述：查询单个行业信息
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/2 14:52
     * 参数：[ * @param null]
     * 返回值：
    */
    public Trade getOneTrade(String trade_id){
        Trade trade = tradeDao.selectByPrimaryKey(trade_id);
        return trade;
    }

    /**
     * 功能描述：设置行业菜单
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/25 15:03
     * 参数：[ * @param null]
     * 返回值：
    */
    public ResultData setTradeMenu(Trade trade){
        int i = tradeDao.updateByPrimaryKeySelective(trade);
        if(i!=0){
            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }

    /**
     * 功能描述：绑定单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/25 15:26
     * 参数：[ * @param null]
     * 返回值：
    */
    public ResultData bindOrganization(Trade_company to){
        int i = tradeCompanyDao.updateByPrimaryKeySelective(to.setBind_time(new Date()).setBind_status(1));
        if(i!=0){
            //设置行管
            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }
    /**
     * 功能描述：解绑单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 15:22
     * 参数：[ * @param null]
     * 返回值：
    */
    public ResultData unbindCompany(Trade_company trade_company){

        Trade trade = tradeDao.selectByPrimaryKey(trade_company.getTrade_id());
        Trade_company nowTrade = tradeCompanyDao.selectByPrimaryKey(trade_company.getId());


        if(trade_company.getPassword().equals(trade.getPassword())) {
            int i = tradeCompanyDao.updateByPrimaryKey(nowTrade.setBind_status(0)
                    .setCompany_id(null)
                    .setBind_time(null));
            if (i!=0){
                //解除行业管理员以及所有绑定关系
                return ResultData.SUCCESS();
            }else {
                return ResultData.FAILED();
            }
        }else {
            return ResultData.WRITE(400,"密码错误");
        }
    }
    /**
     * 功能描述：获取行业绑定情况
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/27 10:04
     * 参数：[ * @param null]
     * 返回值：
    */
    public List<Trade> getTradeBind(String trade_id){
        List<Trade> trades = tradeCompanyDao.selectbind(trade_id);
        return trades;
    }


    /**
     * 功能描述：判断行业管理员
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 17:24
     * 参数：[ * @param null]
     * 返回值：
    */
    public Trade_company isTradeManager(String company_id){
        return tradeCompanyDao.isTradeManager(company_id);
    }
}
