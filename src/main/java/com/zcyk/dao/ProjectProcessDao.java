package com.zcyk.dao;

import com.zcyk.entity.ProjectProcess;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProjectProcessDao extends Mapper<ProjectProcess> {

    /*未审核*/
    @Select("select * from project_process where initiator_project_id = #{project_id} and status in (1,2,3)")
    @Results(value = {@Result(property = "initiator_project_name",column = "initiator_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "handler_parent_name",column = "handler_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "initiator",column = "initiator_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "handler",column = "handler_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "project_users",column = "handler_project_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserProjectStationDao.getAllUserCount"))})
    List<ProjectProcess> selectProcessByInitiator(@Param("project_id") String project_id);

    @Select("select * from project_process where handler_project_id = #{project_id} and status in (1,2,3)")
    @Results(value = {@Result(property = "initiator_project_name",column = "initiator_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "handler_parent_name",column = "handler_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "initiator",column = "initiator_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "handler",column = "handler_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "project_users",column = "handler_project_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserProjectStationDao.getAllUserCount"))})
    List<ProjectProcess> selectProcessByHandler(@Param("project_id") String project_id);

    /*已审核*/
    @Select("select * from project_process where initiator_project_id = #{project_id} and status not in (1,2,3)")
    @Results(value = {@Result(property = "initiator_project_name",column = "initiator_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "handler_parent_name",column = "handler_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "initiator",column = "initiator_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "handler",column = "handler_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "project_users",column = "handler_project_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserProjectStationDao.getAllUserCount"))})
    List<ProjectProcess> selectCheckedProcessByInitiator(@Param("project_id") String project_id);

    @Select("select * from project_process where handler_project_id = #{project_id} and status not in (1,2,3)")
    @Results(value = {@Result(property = "initiator_project_name",column = "initiator_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "handler_parent_name",column = "handler_project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "initiator",column = "initiator_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "handler",column = "handler_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "project_users",column = "handler_project_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserProjectStationDao.getAllUserCount"))})
    List<ProjectProcess> selectCheckedProcessByHandler(@Param("project_id") String project_id);

}
