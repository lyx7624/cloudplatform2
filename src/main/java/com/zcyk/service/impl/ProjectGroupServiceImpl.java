package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.ProjectGroup;
import com.zcyk.dao.ProjectGroupDao;
import com.zcyk.service.ProjectGroupService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 项目组(ProjectGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("projectGroupService")
public class ProjectGroupServiceImpl implements ProjectGroupService {
    @Resource
    private ProjectGroupDao projectGroupDao;

    @Override
    public ResultData updateProjectGroup(ProjectGroup projectGroup) {
        if(projectGroup.getId()==null){//新增
            String id = UUID.randomUUID().toString();
            projectGroup.setId(id).setCreate_time(new Date()).setStatus(1);
            projectGroupDao.insertSelective(projectGroup);
            return ResultData.SUCCESS(id);
        }else {//修改
            projectGroupDao.updateByPrimaryKeySelective(projectGroup);
            return ResultData.SUCCESS(projectGroup.getId());

        }
    }

    @Override
    public List<ProjectGroup> isRepeat(ProjectGroup projectGroup) {
        Example example = new Example(ProjectGroup.class);
        example.and().andEqualTo("code",projectGroup.getCode()).orEqualTo("name",projectGroup.getName())
        .andEqualTo("status",1);
        List<ProjectGroup> projectGroups = projectGroupDao.selectByExample(example);
        return projectGroups;
    }

    @Override
    public ProjectGroup getById(String id) {
        return projectGroupDao.selectByPrimaryKey(id);
    }

    @Override
    public  PageInfo<ProjectGroup> getByCompanyId(String id, Integer pageNum, Integer pageSize, String search) {
        if(null != pageNum && null !=pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        PageInfo<ProjectGroup> projectGroupPageInfo= new PageInfo<>(projectGroupDao.selectByCompanyId(id,search));
        return projectGroupPageInfo;
    }
}