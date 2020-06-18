package com.zcyk.dao;

import com.zcyk.entity.Project;
import com.zcyk.entity.ProjectRelation;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/6/8 15:49
 */
public interface ProjectRelationDao extends Mapper<ProjectRelation> {


    @Select("select * from project_relation where project_id = #{project_id}")
    List<ProjectRelation> selectByProjectId(String project_id);

    @Update("update project_relation set status = 0,end_time = #{nowDate} where project_process_id = #{process_id}")
    int updateStatusByProcessId(@Param("process_id") String process_id, @Param("nowDate") Date nowDate);

    /*查询下级项目部*/
    @Select("select pp.*,pr.*,pp.id as process_id,pr.end_time as relationEndTime from project_relation pr " +
            "inner join project_process pp on pr.project_process_id = pp.id where parent_id = #{parent_id} and pr.status != 0")
    @Results(value = {@Result(property = "project_name",column = "project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "parent_name",column = "parent_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "initiator",column = "initiator_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "handler",column = "handler_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "project_users",column = "project_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserProjectStationDao.getAllUserCount"))})
    List<ProjectRelation> selectByParentId(@Param("parent_id") String parent_id);

    /*查询上级项目部*/
    @Select("select pp.*,pr.*,pp.id as process_id,pr.end_time as relationEndTime from project_relation pr " +
            "inner join project_process pp on pr.project_process_id = pp.id where project_id = #{project_id} and pr.status != 0")
    @Results(value = {@Result(property = "project_name",column = "project_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "parent_name",column = "parent_id",javaType = String.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "initiator",column = "initiator_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "handler",column = "handler_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "project_users",column = "project_id",javaType = Integer.class,one = @One(select = "com.zcyk.dao.UserProjectStationDao.getAllUserCount"))})
    List<ProjectRelation> selectByProjectId2(@Param("project_id") String project_id);
}
