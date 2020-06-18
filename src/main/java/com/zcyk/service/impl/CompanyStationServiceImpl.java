package com.zcyk.service.impl;

import com.zcyk.dao.CompanyDepartmentDao;
import com.zcyk.dao.CompanyStationDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.User;
import com.zcyk.service.CompanyStationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司部门岗位2(CompanyStation)表服务实现类
 *
 * @author makejava
 * @since 2020-05-25 14:40:05
 */
@Service("companyDepartmentStationService")
public class CompanyStationServiceImpl implements CompanyStationService {
    @Resource
    private CompanyStationDao companyStationDao;

    @Resource
    private CompanyDepartmentDao companyDepartmentDao;


    /*判断同一级别下是否有重名*/
    boolean getByNameInDepartment(String superior,String name,String id){
        //重名判断
        Example example = new Example(CompanyStation.class);
        example.and().andEqualTo("superior_id",superior).andEqualTo("status",1).andEqualTo("name",name).andNotEqualTo("id",id);
        int size = companyStationDao.selectByExample(example).size();
        if(size!=0){
            return false;
        }
        return true;
    }


    /*添加岗位*/
    public ResultData addStation(CompanyStation station){
        if(!getByNameInDepartment(station.getSuperior_id(),station.getName(),station.getId())){
           return ResultData.WRITE(400,"岗位已存在");
        }


        companyStationDao.insertSelective(station);

        return ResultData.WRITE(200,"添加成功",station);

    }

    /*修改岗位*/
    public ResultData updateStation(CompanyStation station) {
        if (!getByNameInDepartment(station.getSuperior_id(), station.getName(),station.getId())) {
            ResultData.WRITE(400, "岗位已存在");
        }
        companyStationDao.updateByPrimaryKeySelective(station);
        return ResultData.WRITE(200,"修改成功");

    }


    /*获取部门下所有岗位*/
    public List<CompanyStation> getDepartmentStation(String department_id){
        Example example = new Example(CompanyStation.class);
        example.and().andEqualTo("superior_id",department_id).andEqualTo("status",1);
        List<CompanyStation> companyStations = companyStationDao.selectByExample(example);
        companyStations.forEach(companyDepartmentStation -> {
            List<CompanyStation> allDepartment = getDepartmentStation(companyDepartmentStation.getId());
            companyDepartmentStation.setChild(allDepartment);
        });

        return companyStations;

    }

    @Override
    public List<CompanyStation> getCompanyStation(String company_id) {
        List<CompanyStation> companyStations = companyStationDao.selectByCompanyId(company_id,1);
        return companyStations;
    }

    @Override
    public List<CompanyStation> getUnderStation(String superior_id) {
        Example example = new Example(CompanyStation.class);
        example.and().andEqualTo("superior_id",superior_id).andEqualTo("status",1);
        List<CompanyStation> companyStations = companyStationDao.selectByExample(example);
        return companyStations;
    }

    /*根据岗位id获取岗位负责人*/
    @Override
    public User getStationAdministrator(String id) {
        return companyStationDao.getStationAdministrator(id);
    }

    /*根据人员id查询当前所管理的岗位   可能涉及到一人多岗位*/
    @Override
    public List<CompanyStation> getAdministratorStation(String user_id) {
        Example example = new Example(CompanyStation.class);
        example.and().andEqualTo("user_id",user_id).andEqualTo("status",1);
        return companyStationDao.selectByExample(example);
    }

    /*获取岗位部门*/
    @Override
    public CompanyDepartment getStationDepartment(String station_id) {
        String department_id = companyStationDao.selectByPrimaryKey(station_id).getDepartment_id();
        if(StringUtils.isBlank(department_id)){
            return null;
        }
        return companyDepartmentDao.selectByPrimaryKey(department_id);
    }

    @Override
    public CompanyStation getStationById(String id) {
        return companyStationDao.selectByPrimaryKey(id);
    }

    @Override
    public List<CompanyStation> getNoDepartmentStation(String company_id) {
        Example example = new Example(CompanyStation.class);
        example.and().andEqualTo("superior_id",company_id).andEqualTo("status",1);
        List<CompanyStation> companyStations = companyStationDao.selectByExample(example);
        companyStations.stream().filter(projectStation -> StringUtils.isBlank(projectStation.getDepartment_id())).collect(Collectors.toList());
        return companyStations;
    }


}