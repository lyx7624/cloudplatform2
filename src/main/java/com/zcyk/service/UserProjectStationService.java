package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.User;
import com.zcyk.entity.UserProjectStation;
import java.util.List;

/**
 * 用户项目岗位表(UserProjectStation)表服务接口
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
public interface UserProjectStationService {


    /*获取项目部所有成员*/
    PageInfo<UserStationDto> getProjectUser(String projectDepartment_id, Integer status, Integer pageSize, Integer pageNum);

    /*委派：添加*/
    boolean addUserToProjectDepartment(UserProjectStation userProjectStation);

    /*修改*/
    boolean updateUserStationById(UserProjectStation setStatus);

    /*获取岗位*/
    PageInfo<UserStationDto> getUserProjectDepartmentStation(Integer pageSize, Integer pageNum, String user_id, String project_department_id, Integer status);
    /*查询用户所有的委派*/
    List<UserProjectStation> getUserProjectDepartmentStationRecord(String user_id, Integer status);
    /*获取用户岗位 根据记录id*/
    UserStationDto getUserProjectDepartmentStationById(String id);
    /*获取用户所有公司的的委派记录*/
    PageInfo<UserStationDto> getUserDelegateStation(Integer pageSize, Integer pageNum, String user_id, String company_id);

    /*获取项目中本公司委派的成员*/
    PageInfo<UserStationDto> getPDUsersByCompany(Integer pageSize, Integer pageNum, String company_id, String project_department_id, Integer status);


    /*h获取公司未委派的成员*/
    List<User> gatCompanyNoDelegate(String company_id);

    UserProjectStation getById(String id);

    /*修改岗位*/
    boolean replaceStation(UserProjectStation setRole) throws Exception;


}