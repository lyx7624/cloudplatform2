package com.zcyk.dao;

import com.zcyk.entity.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 项目(Project)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectDao extends Mapper<Project> {

    //根据id删除2D项目
    @Update("update project set status = 0 where id = #{id}")
    int deleteProjectById(String id);

    /*根据企业查询所在项目组所有项目*/
    @Select("select * from project p inner join company_project_group cpg on p.project_group_id = cpg.project_group_id" +
            " where cpg.company_id = #{company_id} and cpg.status = 1" +
            " and(p.name like '%${search}%' or p.level like '%${search}%' or p.type like '%${search}%' or p.phase like '%${search}%')")
    List<Project> getGroupProjectByCompany(@Param("company_id") String company_id,@Param("search") String search);
    @Select("select * from project where project_group_id = #{group_id} and status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    List<Project> selectProjectByGroupId(@Param("group_id") String group_id,@Param("search") String search,@Param("level") String level,@Param("type") String type,@Param("phase") String phase);

    @Select("select  * from project where status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    List<Project> selectAllProjectByGroupId(@Param("search") String search,@Param("level") String level,@Param("type") String type,@Param("phase") String phase);

    @Select("select count(*) from project where project_group_id = #{group_id} and status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    int selectCountByGroupId(String group_id,String search,String level,String type,String phase);

    @Select("select count(*) from project where status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    int selectAllCountByGroupId(String search,String level,String type,String phase);

    @Select("select name from project where id = #{project_id}")
    String getProjectName(@Param("project_id")String project_id);

}