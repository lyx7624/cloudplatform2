package com.zcyk.dao;

import com.zcyk.entity.ProjectStation;
import com.zcyk.entity.UserProjectStation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司部门岗位2(ProjectStation)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
public interface ProjectStationDao extends Mapper<ProjectStation> {



    /*根据上级部门擦候选下级的岗位*/
    @Select("select * from project_station where department_id = #{department_id} and status = 1")
    List<ProjectStation> getStationByDepartment(@Param("department_id") String department_id);

    @Select("select * from project_station where superior_id = #{superior_id} and status = 1")
    List<ProjectStation> getStationBySuperior(@Param("superior_id") String superior_id);


    @Select("select * from project_station where project_department_id = #{0} and status = 1")
    List<ProjectStation> getAllStation(String department_id);

    /*根据上级部门擦候选下级的岗位*/
    @Select("select name from project_station where id = #{id}")
    String getStationNameById(@Param("id") String id);

    /*获取岗位部门  result*/
    @Select("select pd.name from project_station ps inner join project_department_subdivision pd on ps.department_id = pd.id" +
            " where ps.id = #{id} and pd.status = 1")
    String getStationDepartmentName(@Param("id") String id);



}