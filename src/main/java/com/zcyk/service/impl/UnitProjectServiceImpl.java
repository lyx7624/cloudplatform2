package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.ProjectDao;
import com.zcyk.dao.ProjectFileDao;
import com.zcyk.dto.ProjectParent;
import com.zcyk.dto.UnitprojectGis;
import com.zcyk.dto.UnitprojectGisBim;
import com.zcyk.entity.Project;
import com.zcyk.entity.ProjectFile;
import com.zcyk.entity.UnitProject;
import com.zcyk.dao.UnitProjectDao;
import com.zcyk.service.UnitProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 子项目表(UnitProject)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Transactional(rollbackFor = Exception.class)
@Service("unitProjectService")
public class UnitProjectServiceImpl implements UnitProjectService {

    @Resource
    private UnitProjectDao unitProjectDao;
    @Resource
    private ProjectFileDao projectFileDao;
    @Resource
    private ProjectDao projectDao;


    @Override
    public UnitProject addUniProject(UnitProject unitProject) {
        unitProjectDao.insertSelective(unitProject.setId(UUID.randomUUID().toString())
                                         .setStatus(1));
        return unitProject;
    }

    @Override
    public UnitProject updateUniProject(UnitProject unitProject){
        unitProjectDao.updateByPrimaryKeySelective(unitProject);
        return unitProject;
    }

    @Override
    public void deleteUniProject(UnitProject unitProject){
        //删除3D工程下绑定的文件
        projectFileDao.deleteFileByProjectId(unitProject.getId());
        unitProjectDao.updateByPrimaryKeySelective(unitProject.setStatus(0));
    }

    @Override
    public Map<String,Object> getUniProject(String project_id, int pageNum, int pageSize, Integer sort, String search, String level, String type, String phase){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<UnitProject> uniProjects = unitProjectDao.selectUniProjectByProjectId(project_id,search,level,type,phase);
        PageInfo<UnitProject> projectModelPageInfo = new PageInfo<>(uniProjects);
        int i = unitProjectDao.selectCountByProjectId(project_id, search, level, type, phase);
        map.put("count",i);
        map.put("UniprojectPageInfo",projectModelPageInfo);
        return map;
    }

    @Override
    public Map<String,Object> getAllUniProject( int pageNum, int pageSize, Integer sort, String search, String level, String type, String phase){
        List<UnitprojectGis> UnitprojectGises = new ArrayList<>();//最终数据格式
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<UnitProject> uniProjects = unitProjectDao.selectAllUniProjectByProjectId(search,level,type,phase);
        PageInfo<UnitProject> projectModelPageInfo = new PageInfo<>(uniProjects);
        int i = unitProjectDao.selectAllCountByProjectId( search, level, type, phase);
        //所有项目
        List<Project> projects = projectDao.selectAllProjectByGroupId(search, level, type, phase);
        for (Project project:projects){//查项目下所有工程
            List<UnitprojectGisBim>UnitprojectGisBimList = new ArrayList<>();//刷新数据
            UnitprojectGis UnitprojectGis = new UnitprojectGis();
            List<UnitProject> unitProjects1 = unitProjectDao.selectUniProjectByProjectId(project.getId(), search, level, type, phase);
                        for (UnitProject unitProjectOne:unitProjects1) {//每个工程的L和V
                            UnitprojectGisBim UnitprojectGisBim = new UnitprojectGisBim();//（chldren数据）刷新数据
                            UnitprojectGisBim.setLabel(unitProjectOne.getName())
                                             .setValue(unitProjectOne.getBim_path());
                            UnitprojectGisBimList.add(UnitprojectGisBim);//添加到Chldren里
                            UnitprojectGis.setChildren(UnitprojectGisBimList);
                        }

                        UnitprojectGis.setLabel(project.getName())
                                     .setValue(project.getId());
                        UnitprojectGises.add(UnitprojectGis);
        }
        map.put("Lv",UnitprojectGises);
        map.put("count",i);
        map.put("UniprojectPageInfo",projectModelPageInfo);
        return map;
    }

    @Override
    public Map<String,Object> getOneUniProject(String id){
        Map<String,Object> map = new HashMap<>();
        UnitProject unitProject = unitProjectDao.selectByPrimaryKey(id);
        //根据type获取文件
        List<ProjectFile> projectImg = projectFileDao.selectFileByProjectId(id, "1");
        List<ProjectFile> projectVideo = projectFileDao.selectFileByProjectId(id, "2");
        List<ProjectFile> projectFiles = projectFileDao.selectFileByProjectId(id, "3");
        List<ProjectFile> projectBim = projectFileDao.selectFileByProjectId(id,"4");
        map.put("uniProject",unitProject);
        map.put("projectImg",projectImg);
        map.put("projectVideo",projectVideo);
        map.put("projectFiles",projectFiles);
        map.put("projectBim",projectBim);
        return map;
    }
}