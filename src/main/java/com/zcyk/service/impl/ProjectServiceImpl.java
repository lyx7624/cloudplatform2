package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.ProjectFileDao;
import com.zcyk.dao.UnitProjectDao;
import com.zcyk.dto.ProjectParent;
import com.zcyk.entity.Project;
import com.zcyk.dao.ProjectDao;
import com.zcyk.entity.ProjectFile;
import com.zcyk.entity.UnitProject;
import com.zcyk.service.ProjectParentService;
import com.zcyk.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目(Project)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Transactional(rollbackFor = Exception.class)
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectDao projectDao;
    @Resource
    private ProjectFileDao projectFileDao;
    @Resource
    private UnitProjectDao unitProjectDao;

    /**
     * 新增项目(2D)
     * 开发人员：Wujiefeng
    */
    @Override
    public Project addProject(Project project) {
        projectDao.insertSelective(project.setId(UUID.randomUUID().toString()).setStatus(1));//.setId(UUID.randomUUID().toString())
        return project;
    }
    /**
     * 修改项目(2D)
     * 开发人员：Wujiefeng
     */
    @Override
    public Project updateProject(Project project){
        projectDao.updateByPrimaryKeySelective(project);
        return project;
    }
    /**
     * 删除项目(2D)
     * 开发人员：Wujiefeng
     */
    @Override
    public void deleteProject(Project project){
        List<String>ids = new ArrayList<>();
        ids.add(project.getId());
        //找出删除项目下工程
        List<UnitProject> unitProjects = unitProjectDao.selectUniProjectByProjectId(project.getId(), "", "", "", "");
        for (UnitProject unitProject:unitProjects){
            unitProjectDao.updateByPrimaryKeySelective(unitProject.setStatus(0));
            ids.add(unitProject.getId());
        }
        //删除项目工程关联的文件
        for (String id:ids){
          projectFileDao.deleteFileByProjectId(id);
        }
        //删除项目
        projectDao.deleteProjectById(project.getId());
    }
    /**
     * 查询项目(2D)
     * 开发人员：Wujiefeng
     */
    @Override
    public Map<String,Object> getProject(String group_id,int pageNum, int pageSize,  String search,String level,String type,String phase){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Project> projects = projectDao.selectProjectByGroupId(group_id,search,level,type,phase);
        PageInfo<Project> projectModelPageInfo = new PageInfo<>(projects);
        int i = projectDao.selectCountByGroupId(group_id, search, level, type, phase);
        map.put("count",i);
        map.put("projectPageInfo",projectModelPageInfo);
        return map;
    }
    /**
     *查询所有2D项目
     */
    @Override
    public Map<String,Object> getAllProject(int pageNum, int pageSize, String search,String level,String type,String phase){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Project> projects = projectDao.selectAllProjectByGroupId(search,level,type,phase);
        PageInfo<Project> projectModelPageInfo = new PageInfo<>(projects);
        int i = projectDao.selectAllCountByGroupId( search, level, type, phase);
        map.put("count",i);
        map.put("projectPageInfo",projectModelPageInfo);
        return map;
    }




    /**
     * 功能描述：查看单个2D项目
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 18:41
     * 参数：[ * @param null]
     * 返回值：
    */
    @Override
    public Map<String,Object> getOneProject(String id){
        Map<String,Object> map = new HashMap<>();
        //根据type查询绑定文件
        List<ProjectFile> projectImg = projectFileDao.selectFileByProjectId(id, "1");
        List<ProjectFile> projectVideo = projectFileDao.selectFileByProjectId(id, "2");
        List<ProjectFile> projectFiles = projectFileDao.selectFileByProjectId(id, "3");
       // List<ProjectFile> projectFiles1 = projectFileDao.selectAllFileByProjectId(id);
        Project project = projectDao.selectByPrimaryKey(id);
        map.put("project",project);
        map.put("projectImg",projectImg);
        map.put("projectVideo",projectVideo);
        map.put("projectFiles",projectFiles);
        return map;
    }


}