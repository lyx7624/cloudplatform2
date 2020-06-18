package com.zcyk.dao;


import com.zcyk.entity.UserProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司用户表(UserProject)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-06 17:23:03
 */
public interface UserProjectDao extends Mapper<UserProject> {

    @Update("update user_project set project_id = #{project_id} where user_id = #{user_id} and status = 1")
    void updateByUserId(@Param("user_id") String user_id, @Param("project_id") String project_id);

    /*根据用户id查询所在所在项目*/
    @Select("select up.*,p.name project_name from user_project up inner join project p on up.project_id = p.id where up.status = 1")
    List<UserProject> selectByUserId(String id);
}