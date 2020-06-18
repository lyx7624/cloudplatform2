package com.zcyk.controller;

import com.zcyk.dao.CountyDao;
import com.zcyk.entity.County;
import com.zcyk.service.CountyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/5/27 8:58
 */
@RequestMapping("county")
public class CountyController {

    @Resource
    private CountyService countyService;

    @GetMapping("/")
    public List<County> getAllCounty() throws Exception {
        try {
            List<County> allCounty = countyService.getAllCounty();
            return allCounty;
        }catch (Exception e){
            throw new Exception("失败");
        }
    }

}
