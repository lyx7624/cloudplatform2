package com.zcyk.service.impl;

import com.zcyk.dao.ProjectStationDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.*;
import com.zcyk.dao.ProjectDepartmentSubdivisionDao;
import com.zcyk.service.ProjectDepartmentSubdivisionService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公司——部门(ProjectDepartmentSubdivision)表服务实现类
 *
 * @author makejava
 * @since 2020-06-10 09:25:06
 */
@Service("projectDepartmentSubdivisionService")
public class ProjectDepartmentSubdivisionServiceImpl implements ProjectDepartmentSubdivisionService {
    @Resource
    private ProjectDepartmentSubdivisionDao projectDepartmentSubdivisionDao;

    @Resource
    private ProjectStationDao projectStationDao;

    /*判断同一级别下是否有重名*/
    boolean getByNameInCompany(String id, String superior, String name){//重名判断
        Example example = new Example(CompanyDepartment.class);
        example.and().andEqualTo("superior_id",superior).andEqualTo("status",1).andEqualTo("name",name).andNotEqualTo("id",id);
        int size = projectDepartmentSubdivisionDao.selectByExample(example).size();
        if(size==0){
            return true;
        }
        return false;
    }


    @Override
    public ResultData addDepartmentSubdivision(ProjectDepartmentSubdivision departmentSubdivision) {
        if(!getByNameInCompany(departmentSubdivision.getSuperior_id(),departmentSubdivision.getSuperior_id(), departmentSubdivision.getName())){
            return ResultData.WRITE(400,"该部门已存在");
        }
        projectDepartmentSubdivisionDao.insertSelective(departmentSubdivision);
        return ResultData.WRITE(200,"部门新增成功",departmentSubdivision);
    }

    @Override
    public List<ProjectDepartmentSubdivision> getUnderDepartment(String superior_id) {
        return projectDepartmentSubdivisionDao.getUnderDepartment(superior_id);
    }


    @Override
    public ResultData updateDepartment(ProjectDepartmentSubdivision projectDepartment) {

        Integer status = projectDepartment.getStatus();

        if(status!=null && status!=0 && !getByNameInCompany(projectDepartment.getId(),projectDepartment.getSuperior_id(),projectDepartment.getName())){
            return ResultData.WRITE(400,"该部门已存在");
        }
        projectDepartmentSubdivisionDao.updateByPrimaryKeySelective(projectDepartment);
        return ResultData.WRITE(200,"操作成功");

    }

    @Override
    public void deleteDepartmentAndUnder(String id) {
        //删除当前部门
        projectDepartmentSubdivisionDao.updateByPrimaryKeySelective(new ProjectDepartmentSubdivision().setId(id).setStatus(0));
        //获取下级部门
        List<ProjectDepartmentSubdivision> underDepartments = projectDepartmentSubdivisionDao.getUnderDepartment(id);
        //获取部门下一级的岗位
        List<ProjectStation> stations = projectStationDao.getStationBySuperior(id);
        stations.forEach(station-> {
            projectStationDao.updateByPrimaryKeySelective(station.setDepartment_id("").setSuperior_id(station.getProject_department_id()));
            //设置岗位下级岗位部门为空
            deleteUnderStationDepartment(station.getId());

        });
        underDepartments.forEach(underDepartment-> deleteDepartmentAndUnder(underDepartment.getId()));

    }

    void  deleteUnderStationDepartment(String station_id){
        Example example = new Example(ProjectStation.class);
        example.and().andEqualTo("superior_id",station_id).andEqualTo("status",1);
        List<ProjectStation> projectStations = projectStationDao.selectByExample(example);
        projectStations.forEach(cs->{
            projectStationDao.updateByPrimaryKeySelective(cs.setDepartment_id(""));
            deleteUnderStationDepartment(cs.getId());
        });

    }
}