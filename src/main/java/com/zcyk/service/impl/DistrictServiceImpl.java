package com.zcyk.service.impl;

import com.zcyk.dao.DistrictCompanyDao;
import com.zcyk.dao.DistrictDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.District;
import com.zcyk.service.DistrictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author WuJieFeng
 * @date 2020/6/1 15:34
 */
@Transactional(rollbackFor = Exception.class)
@Service("DistrictService")
public class DistrictServiceImpl implements DistrictService {
    @Resource
    private DistrictDao districtDao;
    @Resource
    private DistrictCompanyDao districtCompanyDao;

   /**
     * 功能描述：获取区域绑定列表
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 15:42
     * 参数：[ * @param null]
     * 返回值：
    */
    public List<District> getDistrictCompany(){
        List<District> districts = districtDao.getDistrict();
        return districts;
    }

    /**
     * 功能描述：判断是否区域管理员
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 17:33
     * 参数：[ * @param null]
     * 返回值：
    */
    public District isDistrictManager(String company_id){
        return districtDao.isDistrictManager(company_id);
    }

    /**
     * 功能描述：区域绑定单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 16:12
     * 参数：[ * @param null]
     * 返回值：
    */
    public void bindDistrictCompany(District district){
        int i = districtDao.updateByPrimaryKeySelective(district.setBind_status(1).setBind_time(new Date()));

    }

    /**
     * 功能描述：解绑区域单位
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/1 16:08
     * 参数：[ * @param null]
     * 返回值：
    */
    public ResultData unBindDistrictCompany(District district){
        District dis = districtDao.selectByPrimaryKey(district);
        if(dis.getPassword().equals(district.getPassword())){
           int i = districtDao.updateByPrimaryKey(dis.setBind_status(0)
                                                            .setBind_time(null)
                                                            .setCompany_id(null));
            if (i!=0){
                return ResultData.SUCCESS();
            }
            return ResultData.FAILED();
        }else {
            return ResultData.WRITE(400,"密码错误");
        }
    }

    public void set(){
        String[] name = {"市管","万州区","黔江区","涪陵区","渝中区","大渡口区","江北区","沙坪坝区","九龙坡区","南岸区","北碚区","渝北区","巴南区","长寿区","江津区","合川区","永川区","南川区","綦江区","大足区","璧山区","铜梁区","潼南区","荣昌区","开州区","梁平区","武隆区","城口区","丰都县","垫江县","忠县","云阳县","奉节县","巫山县","巫溪县","石柱县","秀山县","酉阳县","彭水县","两江新区","经开区","高新区","万盛经开区","双桥经开区"};
        String[] code = {"500000","500001","500002","500003","500004","500005","500006","500007","500008","500009","500010","500011","500012","500013","500014","500015","500016","500017","500018","500019","500020","500021","500022","500023","500024","500025","500026","500027","500028","500029","500030","500031","500032","500033","500034","500035","500036","500037","500038","500039","500040","500041","500042","500043"};
        for (int i = 0; i < name.length; i++) {
            districtDao.insertSelective(new District().setId(UUID.randomUUID().toString())
                                                      .setCode(code[i])
                                                      .setHigher("500000")
                                                      .setName(name[i])
                                                      .setPassword("zcyk888"));
        }
    }
}
