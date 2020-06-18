package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.Company;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.User;
import com.zcyk.service.CompanyService;
import com.zcyk.service.ProjectDepartmentService;
import com.zcyk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author WuJieFeng
 * @date 2020/6/3 14:42
 */
@Slf4j
@RestController
@RequestMapping("districtManager")
public class DistrictManagerController {

    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyService;
    @Resource
    private ProjectDepartmentService projectDepartmentService;
    /**
     * 功能描述：（区管）人员认证查询
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 14:42
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("{area_code}/{status}")
    public ResultData getUserByDistrictM(@PathVariable String area_code,
                                         @PathVariable int status,
                                         Integer pageNum,Integer pageSize,
                                         String search) throws Exception {
        try {
            PageInfo<User> districtUser = userService.getDistrictUser(area_code, status,search,pageNum, pageSize);
            return ResultData.SUCCESS(districtUser);
        }catch (Exception e){
            throw new Exception("查询失败");
        }
    }

    /**
     * 功能描述：更改用户状态（冻结、正常）
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 14:55
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("{id}/{status}")
    public ResultData updateUserStatus(@PathVariable String id,@PathVariable int status) throws Exception {
        try {
            boolean update = userService.update(new User().setId(id).setStatus(status));
            return ResultData.SUCCESS(update);
        }catch (Exception e){
            throw new Exception("操作失败");
        }

    }

    /**
     * 功能描述：（区管）查询区域内企业列表
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 14:58
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("company/{area_code}/{status}")
    public ResultData getDistrictCompany(@PathVariable("area_code")String area_code,
                                         @PathVariable("status")int status,
                                         Integer pageNum,Integer pageSize,
                                         String search) throws Exception {
        try {
            PageInfo<Company> companyByDistrictManager = companyService.getCompanyByDistrictManager(status, area_code,search,pageNum, pageSize);
            return ResultData.SUCCESS(companyByDistrictManager);
        }catch (Exception e){
            throw new Exception("操作失败");
        }

    }

    /**
     * 功能描述：企业认证
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 15:20
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("company/{id}/{status}")
    public ResultData updateCompanyStatus(@PathVariable String id,@PathVariable int status) throws Exception {
        try {
            ResultData resultData = companyService.updateCompany(new Company().setId(id).setStatus(status));
            return resultData;
        }catch (Exception e){
            throw new Exception("操作失败");
        }

    }

    /**
     * 功能描述：（区管）查询项目部区列表
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/10 10:25
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("projectDepartment/{area_code}/{status}")
    public ResultData getDistrictProjectDepartment(@PathVariable("area_code")String area_code,
                                                   @PathVariable("status")int status,
                                                   Integer pageNum,Integer pageSize,
                                                   String search) throws Exception {
        try {
            PageInfo<ProjectDepartment> projectDepartmentByDistrict = projectDepartmentService.getProjectDepartmentByDistrict(status, area_code,search, pageNum, pageSize);
            return ResultData.SUCCESS(projectDepartmentByDistrict);
        }catch (Exception e){
            throw new Exception("操作失败");
        }

    }

    /**
     * 功能描述：项目部认证
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 15:20
     * 参数：[ * @param null]
     * 返回值：
     */
    @PutMapping("projectDepartment/{id}/{status}")
    public ResultData updateDepartmentStatus(@PathVariable String id,@PathVariable int status) throws Exception {
        try {
            boolean b = projectDepartmentService.updateProjectDepartment(new ProjectDepartment().setId(id).setStatus(status));
            return ResultData.SUCCESS();
        }catch (Exception e){
            throw new Exception("操作失败");
        }

    }

}
