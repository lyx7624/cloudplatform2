package com.zcyk.service.impl;

import com.zcyk.dao.CompanyStationDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.dao.CompanyDepartmentDao;
import com.zcyk.entity.CompanyStation;
import com.zcyk.service.CompanyDepartmentService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公司——部门(CompanyDepartment)表服务实现类
 *
 * @author makejava
 * @since 2020-05-25 14:29:04
 */
@Service("companyDepartmentService")
public class CompanyDepartmentServiceImpl implements CompanyDepartmentService {
    @Resource
    private CompanyDepartmentDao companyDepartmentDao;
    @Resource
    private CompanyStationDao  companyStationDao;


    /*判断同一级别下是否有重名*/
    boolean getByNameInCompany(String id, String superior, String name){
        //重名判断
        Example example = new Example(CompanyDepartment.class);
        example.and().andEqualTo("superior_id",superior).andEqualTo("status",1).andEqualTo("name",name).andNotEqualTo("id",id);
        int size = companyDepartmentDao.selectByExample(example).size();
        if(size!=0){
            return false;
        }
        return true;
    }



    @Override
    public List<CompanyDepartment> getUnderDepartment(String superior_id) {
        return companyDepartmentDao.getUnderDepartment(superior_id);
    }

    /*根据id修改企业部门*/
    @Override
    public ResultData updateCompanyDepartment(CompanyDepartment companyDepartment) {
        if( companyDepartment.getStatus()!=null &&  companyDepartment.getStatus()!=0 && !getByNameInCompany(companyDepartment.getId(),companyDepartment.getSuperior_id(),companyDepartment.getName())){
            return ResultData.WRITE(400,"该部门已存在");
        }
        companyDepartmentDao.updateByPrimaryKeySelective(companyDepartment);
        return ResultData.WRITE(200,"操作成功");

    }

    /*添加企业部门*/
    @Override
    public ResultData addCompanyDepartment(CompanyDepartment companyDepartment) {
        if(!getByNameInCompany(companyDepartment.getSuperior_id(),companyDepartment.getSuperior_id(), companyDepartment.getName())){
            return ResultData.WRITE(400,"该部门已存在");
        }
        companyDepartmentDao.insertSelective(companyDepartment);
        return ResultData.WRITE(200,"部门新增成功",companyDepartment);

    }

    @Override
    public void deleteUnderStation(String id) {
        //获取下级部门
        companyDepartmentDao.updateByPrimaryKeySelective(new CompanyDepartment().setId(id).setStatus(0));
        List<CompanyDepartment> underDepartments = companyDepartmentDao.getUnderDepartment(id);

        //获取部门下的岗位
        List<CompanyStation> stations = companyStationDao.getStationBySuperior(id);
        stations.forEach(station-> {
            //设置一级岗位上级为公司并且设置公司为空
            companyStationDao.updateByPrimaryKeySelective(station.setDepartment_id("").setSuperior_id(station.getCompany_id()));
            //设置一级岗位下级岗位部门为空
            deleteUnderStationDepartment(station.getId());

        });
        underDepartments.forEach(underDepartment-> deleteUnderStation(underDepartment.getId()));

    }

    void  deleteUnderStationDepartment(String station_id){
        Example example = new Example(CompanyStation.class);
        example.and().andEqualTo("superior_id",station_id).andEqualTo("status",1);
        List<CompanyStation> companyStations = companyStationDao.selectByExample(example);
        companyStations.forEach(cs->{
            companyStationDao.updateByPrimaryKeySelective(cs.setDepartment_id(""));
            deleteUnderStationDepartment(cs.getId());
        });

    }
}