package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.Company;
import com.zcyk.entity.User;
import com.zcyk.entity.UserCompanyStation;

/**
 * 用户公司岗位表(UserCompanyStation)表服务接口
 *
 * @author makejava
 * @since 2020-05-25 14:40:38
 */
public interface UserCompanyStationService {




    /*修改:岗位 user_id station_id   离职了设置status为0*/
    ResultData updateStation(UserCompanyStation userCompanyStation);

    boolean updateStationById(UserCompanyStation setStatus);


    /*根据用户查询*/
    PageInfo<Company> getByUserId(String user_id, Integer pageNum, Integer pageSize);
    /*根据用户查询 管理的公司*/
    PageInfo<Company> getUserManageCompany(String user_id, Integer pageNum, Integer pageSize);

    /*添加*/
    boolean addUserToCompanyStation(UserCompanyStation userCompanyStation);

    /*获取用户公司信息*/
    UserCompanyStation getUserCompany(String user_id, String company_id);

    /*修改： 主键*/
    boolean updateById(UserCompanyStation userCompanyStation);

    /**
     * 功能描述：获取该公司审核人员的记录，已审核+未审核
     * 开发人员： lyx
     * 创建时间： 2020/5/28 9:15
     * 参数：
     * 返回值：
     * 异常：
     */
    PageInfo<UserCompanyStation> gatAuditUser(String company_id, Integer pageSize, Integer pageNum);


    /**
     * 功能描述：获取该公司所有成员：在职，离职，在审核
     * 开发人员： lyx
     * 创建时间： 2020/5/28 9:15
     * 参数：
     * 返回值：
     * 异常：
     */
    PageInfo<User> gatAllUser(String company_id, Integer pageSize, Integer pageNum, String search, Integer status);



    /**
     * 功能描述：根据id删除数据
     * 开发人员： lyx
     * 创建时间： 2020/5/28 9:15
     * 参数：
     * 返回值：
     * 异常：
     * @return
     */
    boolean deleteById(String id);


    /**
     * 功能描述：跟换岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/29 15:40
     * 参数：
     * 返回值：
     * 异常：
     */
    boolean replaceStation(UserCompanyStation userCompanyStation) throws Exception;

    /**
     * 功能描述：获取历史岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/29 15:40
     * 参数：
     * 返回值：
     * 异常：
     */
    PageInfo<UserStationDto> getHistoryStation(String user_id, String company_id, Integer pageNum, Integer pageSize);



    /**
     * 功能描述：获取下级
     * 开发人员： lyx
     * 创建时间： 2020/6/3 16:28
     * 参数：
     * 返回值：
     * 异常：
     */
    PageInfo<User> gatUserUnder(String search, String user_id, Integer pageSize, Integer pageNum);

    /**
     * 获取用户公司信息
    * */
    UserCompanyStation getUserCompanyRole(String id, String company_id);

    /*根据岗位记录id获取岗位信息*/

    UserStationDto getCompanyUserById(String id);
    /*根据id获取*/

    UserCompanyStation getById(String id);

    /*获取用户现在的公司*/
    UserCompanyStation getCompanyStationByUser(String user_id);
}