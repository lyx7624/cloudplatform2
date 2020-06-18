package com.zcyk.service;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.User;

import java.util.List;

/**
 * 公司部门岗位2(CompanyStation)表服务接口
 *
 * @author makejava
 * @since 2020-05-25 14:40:05
 */
public interface CompanyStationService {

    /*添加岗位*/
    ResultData addStation(CompanyStation setStatus);

    /*修改岗位*/
    ResultData updateStation(CompanyStation setStatus);


    /*获取部门下所有岗位*/
    List<CompanyStation> getDepartmentStation(String department_id);

    /*获取公司下所有岗位*/
    List<CompanyStation> getCompanyStation(String company_id);

    /*获取岗位下级岗位*/
    List<CompanyStation> getUnderStation(String superior_id);
    /*根据岗位id获取岗位负责人*/

    User getStationAdministrator(String id);

    /*根据人员id查询所管理的岗位 可能涉及到一人多岗位*/

    List<CompanyStation> getAdministratorStation(String user_id);
    /*获取岗位部门*/

    CompanyDepartment getStationDepartment(String station);

    /*根据id获取*/
    CompanyStation getStationById(String id);

    /*根据企业id获取没有部门的岗位*/
    List<CompanyStation> getNoDepartmentStation(String company_id);
    /*根据user_id获取*/

}