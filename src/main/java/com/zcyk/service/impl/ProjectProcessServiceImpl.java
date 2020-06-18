package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.ProjectProcessDao;
import com.zcyk.dao.ProjectRelationDao;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyRelation;
import com.zcyk.entity.ProjectProcess;
import com.zcyk.entity.ProjectRelation;
import com.zcyk.service.ProjectProcessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author WuJieFeng
 * @date 2020/6/8 15:54
 */
@Service("projectProcessService")
@Transactional
public class ProjectProcessServiceImpl implements ProjectProcessService {
    @Resource
    private ProjectRelationDao projectRelationDao;
    @Resource
    private ProjectProcessDao projectProcessDao;

    //*********************************流程表+关系表************************************//
    /*发起添加流程*/
    public ResultData addProcess(ProjectProcess projectProcess){//1、controller添加发起人及发起人项目部，2、处理人项目部id 3、状态
        if(projectProcess.getInitiator_project_id()==projectProcess.getHandler_project_id()){
            return ResultData.WRITE(400,"不能添加自己为自己上下级");
        }
        //1、先判断审核单位跟当前单位有无关系
        //2


        if(projectProcess.getStatus()==1){//添加上级
            //判断是否已有上级
            List<ProjectRelation> projectRelations = projectRelationDao.selectByProjectId(projectProcess.getInitiator_project_id());
            if (projectRelations.size()!=0){
                return ResultData.WRITE(400,"已有上级单位");
            }
        }
        projectProcess.setId(UUID.randomUUID().toString())
                .setStart_time(new Date());
        int insert = projectProcessDao.insert(projectProcess);
        if(insert!=0){
            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }
    /**
     * 功能描述：处理流程
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/8 16:11
     * 参数：[ * @param null]
     * 返回值：
     */
    /*待审核（1、添加上级、2、添加下级、3、申请退出）、已审核（4、同意加入、5、同意退出、6、拒绝加入、7、拒绝退出、8、撤回9、踢出）*/
    public ResultData handleProcess(ProjectProcess projectProcess){
        ProjectProcess oldProjectProcess = projectProcessDao.selectByPrimaryKey(projectProcess);
        if(projectProcess.getStatus()==4){//同意加入
            //修改流程状态、设当前登录人为审核人
            projectProcessDao.updateByPrimaryKeySelective(projectProcess.setEnd_time(new Date()));
            //关联关系表(添加关系)
            if(oldProjectProcess.getStatus()==1){//同意添加上级（当前登录人为上级）
                projectRelationDao.insert(new ProjectRelation().setId(UUID.randomUUID().toString())
                        .setCreate_time(new Date())
                        .setStatus(1)
                        .setProject_process_id(projectProcess.getId())
                        .setProject_id(oldProjectProcess.getInitiator_project_id())
                        .setParent_id(oldProjectProcess.getHandler_project_id()));
            }else {//同意添加下级(当前登录人为下级)
                projectRelationDao.insert(new ProjectRelation().setId(UUID.randomUUID().toString())
                        .setCreate_time(new Date())
                        .setStatus(1)
                        .setProject_process_id(projectProcess.getId())
                        .setProject_id(oldProjectProcess.getHandler_project_id())
                        .setParent_id(oldProjectProcess.getInitiator_project_id()));
            }
            return ResultData.SUCCESS();
        }else if(projectProcess.getStatus()==5) {//同意退出
            //修改流程状态、设当前登录人为审核人
            projectProcessDao.updateByPrimaryKeySelective(projectProcess.setEnd_time(new Date()));
            //修改关联关系（解除关系）设关系表状态为0
            projectRelationDao.updateStatusByProcessId(projectProcess.getId(), new Date());
            return ResultData.SUCCESS();
        }else if(projectProcess.getStatus()==9){//踢出
            //修改流程状态、设当前登录人为审核人
            projectProcessDao.updateByPrimaryKeySelective(projectProcess.setEnd_time(new Date()));
            //修改关联关系表（解除关系）设关联关系表状态为0
            projectRelationDao.updateStatusByProcessId(projectProcess.getId(),new Date());
            return ResultData.SUCCESS();
        }   //状态6、7、8（对关系没有影响）
        //设当前登录人为处理人
        //nowUser
        projectProcessDao.updateByPrimaryKeySelective(projectProcess.setEnd_time(new Date()));
        return ResultData.SUCCESS();
    }



    /*查询流程*/
    public ResultData getProcess(String project_id, Integer type,Integer pageNum,Integer pageSize){//type:1、已添加2、待审核3、已审核
        HashMap<String, Object> map = new HashMap<>();

        if(pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        if (type==1){//已添加
            //查出关系表中的对应项目部的上下级项目部
            //1、先查出上级项目部 List upCompany
            List<ProjectRelation> upProjects = projectRelationDao.selectByProjectId2(project_id);
            for(ProjectRelation upProject:upProjects){
                upProject.setType(1);
            }
            //2、再查出下级项目部 List underCompany
            List<ProjectRelation> underProjects = projectRelationDao.selectByParentId(project_id);
            for (ProjectRelation underProject:underProjects){
                underProject.setType(2);
            }
            underProjects.addAll(upProjects);
            return ResultData.SUCCESS(new PageInfo<>(underProjects));
//            map.put("upProjects",upProjects);
//            map.put("underProjects",underProjects);
        }else if(type==2){//待审核
            //查出流程信息表中发起项目部及审核项目部为自己项目部的数据（状态：1、2、3）
            //1、先查出自己为发起项目部的数据 list initiator
            List<ProjectProcess> initiatorProcesses = projectProcessDao.selectProcessByInitiator(project_id);
            for(ProjectProcess iprocess:initiatorProcesses){
                iprocess.setType(1);
            }
            //2、再查出自己为审核项目部的数据 list handler
            List<ProjectProcess> handlerProcesses = projectProcessDao.selectProcessByHandler(project_id);
            for (ProjectProcess hprocess:handlerProcesses){
                hprocess.setType(2);
            }
            handlerProcesses.addAll(initiatorProcesses);
            return ResultData.SUCCESS(new PageInfo<>(handlerProcesses));
//            map.put("initiatorProcesses",initiatorProcesses);
//            map.put("handlerProcesses",handlerProcesses);
            }//已审核
            //1、查出自己为发起项目部的数据，状态（4、5、6、7、8、9）
            List<ProjectProcess> initiatorProcesses = projectProcessDao.selectCheckedProcessByInitiator(project_id);
            for(ProjectProcess iprocess:initiatorProcesses){
                iprocess.setType(1);
            }
            //2、查出自己为审核项目部的数据，状态（4、5、6、7、8、9）
            List<ProjectProcess> handlerProcesses = projectProcessDao.selectCheckedProcessByHandler(project_id);
            for (ProjectProcess hprocess:handlerProcesses){
                hprocess.setType(2);
            }
            handlerProcesses.addAll(initiatorProcesses);
//            map.put("initiatorProcesses",initiatorProcesses);
//            map.put("handlerProcesses",handlerProcesses);
            return ResultData.SUCCESS(new PageInfo<>(handlerProcesses));


    }
}
