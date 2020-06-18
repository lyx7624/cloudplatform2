package com.zcyk.dao;

import com.zcyk.entity.ProjectFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 项目文件表(ProjectFile)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectFileDao extends Mapper<ProjectFile> {


    //根据项目或工程id和type查询文件
    @Select("select * from project_file where project_id = #{project_id} and type = #{type} and status = 1")
    List<ProjectFile> selectFileByProjectId(String project_id,String type);
    //根据id删除文件
    @Update("update project_file set status = 0 where id = #{id}")
    int deleteFileById(String id);
    //根据工程或项目id删除文件
    @Update("update project_file set status = 0 where project_id = #{project_id}")
    int deleteFileByProjectId(String project_id);

    @Select("select * from project_file where project_id = #{project_id} and status = 1")
    List<ProjectFile> selectAllFileByProjectId(String project_id);
}