package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.UserCompanyStation;
import com.zcyk.service.CompanyService;
import com.zcyk.service.CompanyStationService;
import com.zcyk.service.UserCompanyStationService;
import com.zcyk.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 公司部门岗位2(CompanyStation)表控制层
 *
 * @author makejava
 * @since 2020-05-25 14:40:05
 */
@RestController
@RequestMapping("companyStation")
public class CompanyStationController {
    /**
     * 服务对象
     */
    @Resource
    private CompanyStationService companyStationService;

    @Resource
    HttpServletRequest request;

    @Resource
    CompanyService companyService;

    @Resource
    UserService userService;

    @Resource
    private UserCompanyStationService userCompanyStationService;

    boolean getPermissions(String user_id,String company_id){
        UserCompanyStation userCompany = userCompanyStationService.getUserCompany(user_id, company_id);
        if ("user".equals(userCompany.getRole())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 功能描述：添加岗位,不选择部门的时候就选择岗位的部门id就是公司id
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PostMapping
    public ResultData addStation(@Valid CompanyStation companyStation) throws Exception {
        //权限认证
        String user_id = userService.getNowUser(request).getId();
        if(!getPermissions(user_id, companyStation.getCompany_id())){
            return ResultData.WRITE(400,"没有权限");
        }
        return companyStationService.addStation(companyStation.setCreate_time(new Date()).setCreate_user(user_id)
                                                    .setId(UUID.randomUUID().toString()).setStatus(1));


    }

    /**
     * 功能描述：删除岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @DeleteMapping
    public ResultData deleteStation(String id) throws Exception {
        CompanyStation companyStation = companyStationService.getStationById(id);
        //权限认证
        String user_id = userService.getNowUser(request).getId();
        if(!getPermissions(user_id,companyStation.getCompany_id())){
            return ResultData.WRITE(400,"没有权限");
        }

        companyStationService.updateStation(new CompanyStation().setId(id).setStatus(0));
        return ResultData.WRITE(200,"删除成功");



    }

    /**
     * 功能描述：修改企业岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PutMapping ("")
    public ResultData updateStation(@Valid CompanyStation companyStation) throws Exception {
        //权限认证
        String user_id = userService.getNowUser(request).getId();
        if(!getPermissions(user_id, companyStation.getCompany_id())){
            return ResultData.WRITE(400,"没有权限");
        }
        return companyStationService.updateStation(companyStation);



    }

//       /**
     //     * 功能描述：查询部门下所有岗位
     //     * 开发人员： lyx
     //     * 创建时间： 2020/5/25 14:48
     //     * 参数：
     //     * 返回值：
     //     * 异常：
     //     */
//    @GetMapping ("/stations/{department_id}")
//    public ResultData getDepartmentStation( @PathVariable String department_id) throws Exception {
//
//        List<CompanyStation> companyDepartmentStations = companyStationService.getDepartmentStation(department_id);
//
//        return ResultData.SUCCESS(companyDepartmentStations);
//
//    }

    /**
     * 功能描述：查询公司下下所有岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/s/{company_id}")
    public ResultData getCompanyStation( @PathVariable String company_id) throws Exception {

        List<CompanyStation> companyStations = companyStationService.getCompanyStation(company_id);

        return ResultData.SUCCESS(companyStations);

    }

    /**
     * 功能描述：查询岗位下一级岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/under/{superior_id}")
    public ResultData getUnderStation( @PathVariable String superior_id) throws Exception {

        List<CompanyStation> companyStations = companyStationService.getUnderStation(superior_id);

        return ResultData.SUCCESS(companyStations);

    }

    /**
     * 功能描述：根据岗位获取岗位所在部门
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/department/{station}")
    public ResultData getStationDepartment( @PathVariable String station) throws Exception {

        CompanyDepartment department = companyStationService.getStationDepartment(station);

        if(department==null){//部门被删除，返回公司名称,!!!!!!!!!!!!!!!!!!!!!!可能会报错，因为删除部门将岗位部门id设置成了""
             String name = companyService.getCompanyById(companyStationService.getStationById(station).getCompany_id()).getName();
             return  ResultData.SUCCESS(name);
        }

        return ResultData.SUCCESS(department.getName());

    }




    /**
     * 功能描述：根据岗位id获取岗位负责人，获取上级岗位负责人也是这个接口
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
//    @GetMapping ("/station/admin/{id}")
//    public ResultData getStationAdministrator( @PathVariable String id) throws Exception {
//        return ResultData.SUCCESS(companyStationService.getStationAdministrator(id));
//    }

    /**
     * 功能描述：修改岗位负责人
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
//    @PutMapping ("/station/admin")
//    public ResultData updateStationAdministrator(String id,String administrator,String company_id) throws Exception {
//        //权限认证
//        String user_id = userService.getNowUser(request).getId();
//        if(!getPermissions(user_id,company_id)){
//            return ResultData.WRITE(400,"没有权限");
//        }
//        return companyStationService.updateStation(new CompanyStation().setId(id).setAdministrator_id(administrator));
//    }








}