package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.ProjectGroup;
import java.util.List;

/**
 * 项目组(ProjectGroup)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectGroupService {


    ResultData updateProjectGroup(ProjectGroup projectGroup);

    List<ProjectGroup> isRepeat(ProjectGroup projectGroup);

    ProjectGroup getById(String id);

    PageInfo<ProjectGroup> getByCompanyId(String id, Integer pageNum, Integer pageSize, String search);
}