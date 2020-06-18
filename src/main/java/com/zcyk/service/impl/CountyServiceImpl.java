package com.zcyk.service.impl;

import com.zcyk.dao.CountyDao;
import com.zcyk.entity.County;
import com.zcyk.service.CountyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/5/27 9:01
 */
@Service("CountyService")
public class CountyServiceImpl implements CountyService {
    @Resource
    private CountyDao countyDao;

    public List<County> getAllCounty(){
        List<County> counties = countyDao.selectAll();
        return counties;
    }
}
