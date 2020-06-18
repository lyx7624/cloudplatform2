package com.zcyk.service;

import com.zcyk.entity.UnitProject;
import java.util.List;
import java.util.Map;

/**
 * 子项目表(UnitProject)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UnitProjectService {
    /*新增3D工程*/
    UnitProject addUniProject(UnitProject unitProject);
    /*修改3D工程*/
    UnitProject updateUniProject(UnitProject unitProject);
    /*删除3D工程*/
    void deleteUniProject(UnitProject unitProject);
    /*查询3D工程*/
    Map<String,Object> getUniProject(String project_id,int pageNum, int pageSize, Integer sort, String search,String level,String type,String phase);
    /*查询所有3D*/
    Map<String,Object> getAllUniProject(int pageNum, int pageSize, Integer sort, String search,String level,String type,String phase);

    /*查询单个3D工程*/
    Map<String,Object> getOneUniProject(String id);
}