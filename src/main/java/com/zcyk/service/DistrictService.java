package com.zcyk.service;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.District;
import com.zcyk.entity.District_company;

import java.util.List;

public interface DistrictService {

    void set();
    //获取区县绑定列表
    List<District> getDistrictCompany();
    //绑定单位
    void bindDistrictCompany(District district);
    //解绑单位
    ResultData unBindDistrictCompany(District district);
    //判断是否区域管理员
    District isDistrictManager(String company_id);

}
