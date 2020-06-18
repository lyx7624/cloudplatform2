//package com.zcyk.dao;
//
//import com.zcyk.entity.ProjectGroupCredential;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//import tk.mybatis.mapper.common.Mapper;
//
//import java.util.List;
//
///**
// * 项目证书表(ProjectGroupCredential)表数据库访问层
// *
// * @author makejava
// * @since 2020-04-28 17:29:17
// */
//public interface ProjectDepartmentCredentialDao extends Mapper<ProjectGroupCredential> {
//
//
//    @Select("select * from project_group_credential where project_group_id = #{projectGroupId} and status = 1")
//    List<ProjectGroupCredential> selectByProjectGroupId(@Param("projectGroupId") String projectGroupId);
//}