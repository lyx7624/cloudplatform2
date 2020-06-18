//package com.zcyk.controller;
//
//import com.github.pagehelper.PageInfo;
//import com.zcyk.dto.ResultData;
//import com.zcyk.entity.*;
//import com.zcyk.service.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
///**
// * 企业——项目组(CompanyProjectGroup)表控制层
// *
// * @author makejava
// * @since 2020-04-28 17:29:17
// */
//@Slf4j
//@RestController
//@RequestMapping("projectGroup")
//public class ProjectGroupController {
//    /**
//     * 服务对象
//     */
//    @Resource
//    private CompanyProjectGroupService companyProjectGroupService;
//
//
//    @Resource
//    private CompanyCredentialService companyCredentialService;
//
//    @Resource
//    private ProjectGroupService projectGroupService;
//
//    @Resource
//    private ProjectGroupCredentialService projectGroupCredentialService;
//
//    @Resource
//    UserService userService;
//
//    @Resource
//    UserCompanyService userCompanyService;
//
//
//    /**
//     * 功能描述：新增项目组
//     * 开发人员： lyx
//     * 创建时间： 2020/5/6 14:45
//     * 参数： [projectGroup]
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PostMapping("updateProjectGroup")
//    @Transactional(rollbackFor = Exception.class)
//    public ResultData updateProjectGroup(@RequestBody @Valid ProjectGroup projectGroup) throws Exception {
//
//        String msg;
//        if(projectGroup.getId()==null){
//            msg = "添加";
//        }else {
//            msg = "修改";
//        }
//
//        //重复判断
//        List<ProjectGroup> repeat = projectGroupService.isRepeat(projectGroup);
//        if(repeat.size()!=0 && (projectGroup.getId()==null || !repeat.get(0).getId().equals(projectGroup.getId()))){
//            return ResultData.WRITE(400,"该项目组已存在，请修改项目编号或者项目名称");
//        }
//
//        String id;
//
//        try {
//            //1.新增项目组
//            ResultData updateProjectGroup = projectGroupService.updateProjectGroup(projectGroup);
//            if(updateProjectGroup.getCode()!=200){
//                return ResultData.FAILED();
//            }
//            id = (String) updateProjectGroup.getData();
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new Exception(msg+"项目组失败");
//        }
//        try {
//            //2.项目组关联公司
//            companyProjectGroupService.updateCompanyToGroup(projectGroup.getCompanies(),id);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new Exception(msg+"项目组关联企业失败");
//
//        }
//        try {
//            //3.项目文件添加
//            projectGroupCredentialService.updateCredentials(projectGroup.getProjectGroupCredentials(),id);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new Exception(msg+"项目组关联证书失败");
//
//        }
//
//        return ResultData.WRITE(200,msg+"成功");
//
//
//    }
//
//    /**
//     * 功能描述：查询项目组 根据id查看详细情况
//     * 开发人员： lyx
//     * 创建时间： 2020/5/6 14:45
//     * 参数： [id]
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @GetMapping("getProjectGroup/{id}")
//    public ResultData getProjectGroup(@NotNull @PathVariable String id){
//
//        //1、获取项目组
//        ProjectGroup projectGroup = projectGroupService.getById(id);
//
//        //2、获取参建单位
//        List<Company> companyProjectGroup = companyProjectGroupService.getByProjectGroupId(id);
//        companyProjectGroup.forEach(company -> company.setCompanyCredentials(companyCredentialService.selectByUseId(company.getId())));
//
//        //3、获取证书
//        List<ProjectGroupCredential> projectGroupCredentials = projectGroupCredentialService.selectByUseId(id);
//
//        return ResultData.SUCCESS(projectGroup.setCompanies(companyProjectGroup).setProjectGroupCredentials(projectGroupCredentials));
//    }
//
//    /**
//     * 功能描述：查询项目组 根据当前登录人
//     * 开发人员： lyx
//     * 创建时间： 2020/5/6 14:45
//     * 参数： [id]
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PostMapping ("getProjectGroup")
//    public ResultData getProjectGroup(HttpServletRequest request,
//                               Integer pageNum, Integer  pageSize,
//                               @RequestParam(defaultValue = "") String search){
//
//      /*  User nowUser = userService.getNowUser(request);
//        //一个人只能在一个公司！
//        UserCompany userCompany = userCompanyService.getCompanyByUser(nowUser.getId());
//        if(userCompany == null || userCompany.getCompany_id() == null){
//            return ResultData.WRITE(400,"请先加入公司并加入群组");
//        }
//
////        List<UserCompany> userCompanies = userCompanyService.getCompanyByUser(id);
//        PageInfo<ProjectGroup> projectGroups = projectGroupService.getByCompanyId(userCompany.getCompany_id(),pageNum,pageSize,search);
//
//       return ResultData.SUCCESS(projectGroups);*/
//      return null;
//    }
//
//
//
//    /**
//     * 功能描述：获取公司所在群组的所有项目
//     * 开发人员： lyx
//     * 创建时间： 2020/5/7 14:44
//     * 参数：
//     * 返回值：
//     * 异常：
//     */
//    @PostMapping("getCompanyProject")
//    public List<Project> getByCompany(HttpServletRequest request,@NotNull String company_id,
//                                   Integer pageNum,
//                                   Integer pageSize,
//                                   @RequestParam(defaultValue = "") String search){
//        //查询企业所在项目组的所有项目
////        User nowUser = userService.getNowUser(request);
//        //一个人只能在一个公司！
////        UserCompany userCompany = userCompanyService.getCompanyByUser(nowUser.getId());
//        List<Project> projects = companyProjectGroupService.getByCompanyId(/*userCompany.getCompany_id()*/company_id,pageNum,pageSize,search);
//        return projects;
//
//
//    }
//
//
//
//
//
//}