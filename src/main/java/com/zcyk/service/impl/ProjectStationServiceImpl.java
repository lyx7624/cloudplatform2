package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.ProjectStation;
import com.zcyk.dao.ProjectStationDao;
import com.zcyk.entity.UserProjectStation;
import com.zcyk.service.ProjectStationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司部门岗位2(ProjectStation)表服务实现类
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@Service("projectStationService")
@Transactional
public class ProjectStationServiceImpl implements ProjectStationService {
    @Resource
    private ProjectStationDao projectStationDao;

    @Override
    public boolean addStation(ProjectStation projectStation) {
        return projectStationDao.insertSelective(projectStation)>0;
    }

    @Override
    public boolean updateStation(ProjectStation projectStation) {
        return projectStationDao.updateByPrimaryKeySelective(projectStation)>0;
    }

    @Override
    public List<ProjectStation> getDepartmentStation(String department_id) {
        return projectStationDao.getStationByDepartment(department_id);
    }

    @Override
    public ProjectStation getById(String station_id) {
        return projectStationDao.selectByPrimaryKey(station_id);
    }

    @Override
    public String getStationDepartment(String station_id) {

        return projectStationDao.getStationDepartmentName(station_id);
    }

    @Override
    public List<ProjectStation> getDepartmentAllStation(String department_id) {
        return projectStationDao.getAllStation(department_id);
    }

    @Override
    public List<ProjectStation> getNoDepartmentStation(String project_department_id) {
        Example example = new Example(ProjectStation.class);
        example.and().andEqualTo("project_department_id",project_department_id)
                .andEqualTo("status",1).andEqualTo("superior_id",project_department_id);
        List<ProjectStation> projectStations = projectStationDao.selectByExample(example);
        return projectStations;
    }

    @Override
    public List<ProjectStation> getUnderStation(String superior_id) {
        Example example = new Example(ProjectStation.class);
        example.and().andEqualTo("superior_id",superior_id).andEqualTo("status",1);
        List<ProjectStation> companyStations = projectStationDao.selectByExample(example);
        return companyStations;
    }

    @Override
    public boolean deleteStation(String id) throws Exception {
        if(projectStationDao.updateByPrimaryKeySelective(new ProjectStation().setId(id).setStatus(0))>0){
            //设置下级岗位的上级为项目部
            List<ProjectStation> stations = projectStationDao.getStationBySuperior(id);
            for (int i = 0; i < stations.size(); i++) {
                ProjectStation station = stations.get(i);
                boolean a = projectStationDao.updateByPrimaryKeySelective(station.setSuperior_id(station.getProject_department_id()))>0;
                if(!a){
                    throw new Exception("修改下级岗位错误");
                }
            }

        }
        return true;
    }
}