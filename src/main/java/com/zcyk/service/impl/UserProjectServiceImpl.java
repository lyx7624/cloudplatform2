package com.zcyk.service.impl;

import com.zcyk.entity.UserProject;
import com.zcyk.dao.UserProjectDao;
import com.zcyk.service.UserProjectService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 公司用户表(UserProject)表服务实现类
 *
 * @author makejava
 * @since 2020-05-06 17:23:03
 */
@Service("userProjectService")
public class UserProjectServiceImpl implements UserProjectService {

    @Resource
    private UserProjectDao userProjectDao;

    @Override
    public void updateByUserId(UserProject userProject,String userId) {
        Example example = new Example(UserProject.class);

        if(userProject.getStatus()==null){//只修改在用数据
            example.and().andEqualTo("status",1).andEqualTo("user_id",userId);
        }else {
            example.and().andEqualTo("user_id",userProject.getUser_id());
        }
        List<UserProject> userCompanies = userProjectDao.selectByExample(example);
        if(userCompanies.size() == 0){//新增
            userProject.setId(UUID.randomUUID().toString()).setStatus(1).setUser_id(userId);
            userProjectDao.insertSelective(userProject);
        }else {//修改
            userProjectDao.updateByExampleSelective(userProject.setUser_id(userId),example);
        }
    }

    @Override
    public List<UserProject> getByUserId(String id) {
        return userProjectDao.selectByUserId(id);

    }
}