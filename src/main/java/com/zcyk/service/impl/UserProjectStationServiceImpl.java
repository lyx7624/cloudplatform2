package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.User;
import com.zcyk.entity.UserProjectStation;
import com.zcyk.dao.UserProjectStationDao;
import com.zcyk.service.UserProjectStationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户项目岗位表(UserProjectStation)表服务实现类
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@Service("userProjectStationService")
@Transactional
public class UserProjectStationServiceImpl implements UserProjectStationService {
    @Resource
    private UserProjectStationDao userProjectStationDao;

    @Override
    public PageInfo<UserStationDto> getProjectUser(String projectDepartment_id, Integer status, Integer pageSize, Integer pageNum) {
        if(pageNum!=null&& pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userProjectStationDao.getProjectUserByStatus(projectDepartment_id,status));
    }

    @Override
    public boolean addUserToProjectDepartment(UserProjectStation userProjectStation) {
        return userProjectStationDao.insertSelective(userProjectStation)>0;
    }

    @Override
    public boolean updateUserStationById(UserProjectStation userProjectStation) {
        return userProjectStationDao.updateByPrimaryKeySelective(userProjectStation)>0;
    }

    @Override
    public PageInfo<UserStationDto> getUserProjectDepartmentStation(Integer pageSize, Integer pageNum, String user_id, String project_department_id, Integer status) {
        if(pageNum!=null&& pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userProjectStationDao.getUserStation(project_department_id,user_id,status));
    }

    @Override
    public List<UserProjectStation> getUserProjectDepartmentStationRecord(String user_id, Integer status) {
        Example example = new Example(UserProjectStation.class);
        example.and().andEqualTo("status",status).andEqualTo("user_id",user_id);
        return userProjectStationDao.selectByExample(example);
    }

    @Override
    public UserStationDto getUserProjectDepartmentStationById(String id) {
        return userProjectStationDao.getUserStationById(id);
    }

    @Override
    public PageInfo<UserStationDto> getUserDelegateStation(Integer pageSize, Integer pageNum, String user_id, String company_id) {
        if(pageNum!=null&& pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userProjectStationDao.getUserDelegateStation(company_id,user_id));

    }

    @Override
    public PageInfo<UserStationDto> getPDUsersByCompany(Integer pageSize, Integer pageNum, String company_id, String project_department_id, Integer status) {

        if(pageNum!=null&& pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userProjectStationDao.getProjectUserByCompany(company_id,project_department_id,status));
    }

    @Override
    public List<User> gatCompanyNoDelegate(String company_id) {
        return userProjectStationDao.gatCompanyNoDelegate(company_id);
    }

    @Override
    public UserProjectStation getById(String id) {
        return userProjectStationDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean replaceStation(UserProjectStation userProjectStation) throws Exception {
        boolean update = userProjectStationDao.updateByPrimaryKeySelective(new UserProjectStation().setId(userProjectStation.getId()).setStatus(4))>0;
        if(update){
            boolean add = userProjectStationDao.insertSelective(userProjectStation.setId(UUID.randomUUID().toString()).setJoin_time(new Date())
                    .setStatus(1).setOperation_time(new Date()))>0;
            if(!add){
                throw new Exception("新增历史岗位失败");
            }
        }
        return true;
    }
}