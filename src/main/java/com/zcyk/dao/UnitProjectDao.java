package com.zcyk.dao;

import com.zcyk.entity.Project;
import com.zcyk.entity.UnitProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 子项目表(UnitProject)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UnitProjectDao extends Mapper<UnitProject> {


    //根据2D项目查询3D工程
    @Select("select * from unit_project where project_id = #{project_id} and status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    List<UnitProject> selectUniProjectByProjectId(@Param("project_id") String project_id, @Param("search") String search, @Param("level") String level,@Param("type") String type, @Param("phase") String phase);
    //查询所有3D工程（条件查询）
    @Select("select * from unit_project where status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    List<UnitProject> selectAllUniProjectByProjectId(@Param("search") String search, @Param("level") String level,@Param("type") String type, @Param("phase") String phase);
    //查询2D项目下3D工程数量
    @Select("select count(*) from unit_project where project_id = #{project_id} and status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    int selectCountByProjectId(@Param("project_id")String project_id,@Param("search") String search, @Param("level") String level,@Param("type") String type, @Param("phase") String phase);
    //查询3D工程总数
    @Select("select count(*) from unit_project where status != 0 and name like '%${search}%' and level like '%${level}%' and type like '%${type}%' and phase like '%${phase}%'")
    int selectAllCountByProjectId(@Param("search") String search, @Param("level") String level,@Param("type") String type, @Param("phase") String phase);
}