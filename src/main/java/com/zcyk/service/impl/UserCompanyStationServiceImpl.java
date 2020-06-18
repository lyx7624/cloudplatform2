package com.zcyk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.UserDao;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.User;
import com.zcyk.entity.UserCompanyStation;
import com.zcyk.dao.UserCompanyStationDao;
import com.zcyk.service.UserCompanyStationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 用户公司岗位表(UserCompanyStation)表服务实现类
 *
 * @author makejava
 * @since 2020-05-25 14:40:38
 */
@Service("userCompanyStationService")
@Transactional
public class UserCompanyStationServiceImpl implements UserCompanyStationService {
    @Resource
    private UserCompanyStationDao userCompanyStationDao;

    @Resource
    private UserDao userDao;




    @Override
    public ResultData updateStation(UserCompanyStation userCompanyStation) {
        Example example = new Example(UserCompanyStation.class);
        example.and().andEqualTo("user_id",userCompanyStation.getUser_id()).andEqualTo("station_id",userCompanyStation.getStation_id());
        userCompanyStationDao.updateByExample(userCompanyStation,example);
        return ResultData.WRITE(200,"修改成功");
    }

    @Override
    public boolean updateStationById(UserCompanyStation userCompanyStation) {
        return userCompanyStationDao.updateByPrimaryKeySelective(userCompanyStation)>0;
    }

    @Override
    public boolean updateById(UserCompanyStation userCompanyStation) {
        return userCompanyStationDao.updateByPrimaryKeySelective(userCompanyStation)>0;
    }

    @Override
    public PageInfo<Company> getByUserId(String id, Integer pageNum, Integer pageSize) {
        if(pageNum !=null && pageSize !=null){
            PageHelper.startPage(pageSize,pageNum);
        }
        return new PageInfo<>(userCompanyStationDao.getCompanyByUser(id));
    }

    @Override
    public PageInfo<Company> getUserManageCompany(String user_id, Integer pageNum, Integer pageSize) {
        if(pageNum !=null && pageSize !=null){
            PageHelper.startPage(pageSize,pageNum);
        }
        return new PageInfo<>(userCompanyStationDao.getUserManageCompany(user_id));
    }

    @Override
    public boolean addUserToCompanyStation(UserCompanyStation userCompanyStation) {
        return userCompanyStationDao.insertSelective(userCompanyStation)>0;
    }



    /**
     * 功能描述：获取该公司审核人员的记录
     * 开发人员： lyx
     * 创建时间： 2020/5/28 9:15
     * 参数：
     * 返回值：
     * 异常：
     */
    @Override
    public PageInfo<UserCompanyStation> gatAuditUser(String company_id, Integer pageSize, Integer pageNum) {
        if(pageNum !=null && pageSize !=null){
            PageHelper.startPage(pageSize,pageNum);
        }

        return new PageInfo<>(userCompanyStationDao.gatAuditUser(company_id));
    }

    /**
     * 功能描述：获取该公司所有成员：在职，离职，在审核
     * 开发人员： lyx
     * 创建时间： 2020/5/28 9:15
     * 参数：
     * 返回值：
     * 异常：
     */
    public PageInfo<User> gatAllUser(String company_id, Integer pageSize, Integer pageNum, String search, Integer status){
        if(pageSize!=null&&pageNum!=null){
            PageHelper.startPage(pageNum,pageSize);
        }

        return new PageInfo<>(userCompanyStationDao.gatAllUser(company_id,search,status));
    }

    @Override
    public boolean deleteById(String id) {
        return userCompanyStationDao.deleteByPrimaryKey(id)>0;
    }


    @Override
    public UserCompanyStation getUserCompany(String user_id, String company_id) {
        Example example = new Example(UserCompanyStation.class);
        example.and().andEqualTo("user_id",user_id).andEqualTo("company_id",company_id).andEqualTo("status",1);
        return userCompanyStationDao.selectOneByExample(example);
    }

    @Override
    public boolean replaceStation(UserCompanyStation userCompanyStation) throws Exception {
        //先将以前的岗位去掉
        boolean replace = userCompanyStationDao.updateByPrimaryKeySelective(new UserCompanyStation().setId(userCompanyStation.getId()).
                setStatus(4).setQuit_time(new Date()))>0;
        if(replace){
            boolean reAdd = userCompanyStationDao.insertSelective(userCompanyStation.setId(UUID.randomUUID().toString()).setJoin_time(new Date())
                    .setStatus(1).setOperation_time(new Date()))>0;

            if(!reAdd){
                throw new Exception("新增历史岗位失败");
            }
        }
        return true;

    }

    @Override
    public PageInfo<UserStationDto> getHistoryStation(String user_id, String company_id, Integer pageNum, Integer pageSize) {
        if(pageNum!=null && pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userCompanyStationDao.getHistoryStation(user_id,company_id));
    }

    @Override
    public PageInfo<User> gatUserUnder(String search, String user_id, Integer pageSize, Integer pageNum) {
        if(pageNum!=null && pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(userCompanyStationDao.gatUserUnder(search,user_id));
    }

    @Override
    public UserCompanyStation getUserCompanyRole(String user_id, String company_id) {
        return userCompanyStationDao.getUserCompanyRole(user_id,company_id);
    }

    @Override
    public UserStationDto getCompanyUserById(String id) {
        return userCompanyStationDao.getCompanyStationById(id);
    }

    @Override
    public UserCompanyStation getById(String id) {
        return userCompanyStationDao.selectByPrimaryKey(id);
    }

    @Override
    public UserCompanyStation getCompanyStationByUser(String user_id){
        UserCompanyStation companyStation = userCompanyStationDao.selectByUser(user_id);
        return companyStation;
    }
}