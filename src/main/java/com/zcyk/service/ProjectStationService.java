package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.entity.*;

import java.util.List;

/**
 * 公司部门岗位2(ProjectStation)表服务接口
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
public interface ProjectStationService {


    /*添加岗位*/
    boolean addStation(ProjectStation projectStation);

    /*修改岗位*/
    boolean updateStation(ProjectStation setStatus);

    /*获取部门下所有岗位*/
    List<ProjectStation> getDepartmentStation(String department_id);

    ProjectStation getById(String station_id);

    /*获取岗位部门*/
    String getStationDepartment(String station_id);

    /*获取所有岗位*/
    List<ProjectStation> getDepartmentAllStation(String project_department_id);

    /*获取没有部门的岗位*/
    List<ProjectStation> getNoDepartmentStation(String project_department_id);

    /*获取下级岗位*/
    List<ProjectStation> getUnderStation(String superior_id);

    /*删除岗位*/
    boolean deleteStation(String id) throws Exception;
}