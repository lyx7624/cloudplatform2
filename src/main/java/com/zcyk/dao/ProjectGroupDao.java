package com.zcyk.dao;

import com.zcyk.entity.ProjectGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 项目组(ProjectGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectGroupDao extends Mapper<ProjectGroup> {


    @Select("select * from project_group pg inner join company_project_group cpg on pg.id = cpg.project_group_id where cpg.company_id = #{company_id}" +
            " and (pg.name like '%${search}%' or pg.type like '%${search}%')")
    List<ProjectGroup> selectByCompanyId(@Param("company_id") String id,@Param("search") String search);
}