package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.District;
import com.zcyk.service.DistrictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/6/1 16:02
 */
@Slf4j
@RestController
@RequestMapping("district")
public class DistrictController {

    @Resource
    private DistrictService districtService;

    @PostMapping("/set")
    public void set(){
        districtService.set();
    }

    /**
     * 功能描述：获取区域绑定列表
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 16:23
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("/")
    public List<District> getDistrict() throws Exception {
        try {
           return districtService.getDistrictCompany();
        }catch (Exception e){
            throw new Exception("获取失败");
        }
    }

    /**
     * 功能描述：绑定区域单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 16:27
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("/")
    public void bindDistrictCompany(District district) throws Exception {
        try {
            districtService.bindDistrictCompany(district);
        }catch (Exception e){
            throw new Exception("绑定失败");
        }
    }

    /**
     * 功能描述：解绑区域单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 16:31
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("/unBind")
    public ResultData unBindDistrictCompany(District district)throws Exception{
        try {
           return districtService.unBindDistrictCompany(district);
        }catch (Exception e){
            throw new Exception("解绑失败");
        }
    }
}
