package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.Credential;
import com.zcyk.entity.ProjectGroupCredential;
import com.zcyk.entity.UserCredential;
import com.zcyk.dao.UserCredentialDao;
import com.zcyk.service.UserCredentialService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 用户证书表(UserCredential)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("userCredentialService")
public class UserCredentialServiceImpl implements UserCredentialService {
    @Resource
    private UserCredentialDao userCredentialDao;


    @Override
    public boolean addCredential(UserCredential userCredential, String user_id) {
       return userCredentialDao.insertSelective(userCredential.setUser_id(user_id))>0;
    }

    @Override
    public void updateCredentials(List<UserCredential> userCredentials, String id) {
        //删除所有旧的
        Example example = new Example(UserCredential.class);
        example.and().andEqualTo("user_id",id);
        userCredentialDao.deleteByExample(example);
        //再添加
        userCredentials.forEach(userCredential -> {
            userCredential.setUser_id(id).setId(UUID.randomUUID().toString()).setStatus(1);
            userCredentialDao.insertSelective(userCredential);
        });

    }

    /*根据id修改证书*/
    @Override
    public boolean updateCredentials(UserCredential userCredential) {
        return userCredentialDao.updateByPrimaryKeySelective(userCredential)>0;
    }

    /*根据用户ID获取用户证书 为0的就是没有用的 或者是撤销审核的*/
    @Override
    public PageInfo<UserCredential> selectByUseId(String userId, Integer pageSize, Integer pageNum, Integer status) {
        if(pageSize!=null&&pageNum!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userCredentialDao.getByUserId(userId));
    }

    /*根据用户ID获取用户证书*/
    @Override
    public UserCredential selectById(String id) {
        return userCredentialDao.getById(id);
    }

    @Override
    public Credential selectByCodeAndId(String id, String code) {
        Example example = new Example(ProjectGroupCredential.class);
        example.and().andEqualTo("code",code).andEqualTo("user_id",id);
        return userCredentialDao.selectOneByExample(example);
    }

    @Override
    public void updateByCodeAndId(Credential credential) {
        userCredentialDao.updateByPrimaryKeySelective((UserCredential)credential);
    }

    public PageInfo<UserCredential> getUserCredentialByTrade(String trade_id, int status,String area_code, String type, String level, Integer pageNum,
                                                   Integer pageSize){
        if(status==2) {
            if (pageSize != null && pageNum != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            return new PageInfo<>(userCredentialDao.getUserCredentialByTrade(trade_id, status, area_code, type, level));
        }else {
            if (pageSize != null && pageNum != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            return new PageInfo<>(userCredentialDao.getCheckedUserCredentialByTrade(trade_id, status, area_code, type, level));
        }
    }
}