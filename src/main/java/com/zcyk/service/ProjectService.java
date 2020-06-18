package com.zcyk.service;

import com.zcyk.dto.ProjectParent;
import com.zcyk.entity.Project;
import java.util.List;
import java.util.Map;

/**
 * 项目(Project)表服务接口
 *2D,3D
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectService {
    /*新增2D项目*/
    Project addProject(Project project);
    /*修改2D项目*/
    Project updateProject(Project project);
    /*删除2D项目*/
    void deleteProject(Project project);
    /*根据项目组查询2D项目*/
    Map<String,Object> getProject(String group_id,int pageNum, int pageSize, String search,String level,String type,String phase);
    /*查询所有2D项目*/
    Map<String,Object> getAllProject(int pageNum, int pageSize, String search,String level,String type,String phase);
    /*查询单个2D项目*/
    Map<String,Object> getOneProject(String id);
}