package com.zcyk.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.PageData;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.User;
import com.zcyk.entity.UserProjectStation;
import com.zcyk.service.CompanyProjectService;
import com.zcyk.service.UserProjectStationService;
import com.zcyk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户项目岗位表(UserProjectStation)表控制层
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@RestController
@Slf4j
@RequestMapping("userProjectStation")
public class ProjectDepartment_UserStationController {
    /**
     * 服务对象
     */
    @Resource
    private UserProjectStationService userProjectStationService;

    @Resource
    private CompanyProjectService companyProjectService;

    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;

    /**
     * 项目部后台
     */

    //###############群组管理后台   --------

    /**  管理员操作
     * 功能描述：修改岗位
     *    将以前的岗位设为离职
     *    再新增岗位
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PostMapping("/u")
    @Transactional(rollbackFor = Exception.class)
    public ResultData updateUserStation(UserProjectStation userProjectStation)throws Exception{
        UserProjectStation station = userProjectStationService.getById(userProjectStation.getId());
        if(userProjectStation.getRole()==null){//可以不选择是否是管理员
            userProjectStation.setRole("user");
        }
        if(userProjectStation.getStation_id()==null){//可以不选择岗位
            userProjectStation.setStation_id("");
        }

        //管理员可以相互取消权限？
        if("admin".equals(station.getRole()) && !"admin".equals(userProjectStation.getRole())){
            return ResultData.WRITE(400,"超管角色不能更改");
        }
        //权限认证
        String user_id  = userService.getNowUser(request).getId();


        if(station.getStation_id()!=null && !station.getStation_id().equals(userProjectStation.getStation_id())){//更换了岗位
            if(!userProjectStationService.replaceStation(
                    station.setOut_auditor_id(user_id).setStation_id(userProjectStation.getStation_id()).setSuperior_id(userProjectStation.getSuperior_id())
                    .setRole(userProjectStation.getRole()))){
                return ResultData.WRITE(400,"修改岗位失败");
            }
        }else {
            if(!userProjectStationService.updateUserStationById(userProjectStation)){
                return ResultData.WRITE(400,"修改岗位失败");
            }
        }
        return ResultData.WRITE(200,"修改成功");
    }


    /**
     * 功能描述：获取所有成员 姓名 公司 职位 部门 加入时间 通过人
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String company_id
     * 返回值：
     * 异常：
     */
    @GetMapping("{department_id}/{status}")
    public ResultData getUserByProjectDepartment(@PathVariable String department_id,
                                                 @PathVariable Integer status,Integer pageNum,Integer pageSize,
                                                 @RequestParam(defaultValue = "") String search){
        PageInfo<UserStationDto> projectUser = userProjectStationService.getProjectUser(department_id, status, pageNum, pageSize);
        List<UserStationDto> list = projectUser.getList();

        if(StringUtils.isNotBlank(search)){
            //模糊搜索
            List<UserStationDto> collect = list.stream().filter(us ->
                    (us.getDepartment_name() != null && us.getDepartment_name().contains(search)) ||
                            (us.getCompany_name() != null && us.getCompany_name().contains(search)) ||
                            (us.getStation_name() != null && us.getStation_name().contains(search)) ||
                            (us.getSuperior_name() != null && us.getSuperior_name().contains(search)) ||
                            (us.getUser_name() != null && us.getUser_name().contains(search)) ||
                            (us.getCompany_role() != null && us.getCompany_role().contains(search))
            ).collect(Collectors.toList());
            projectUser.setList(collect);
            projectUser.setTotal(collect.size());
        }

        return ResultData.SUCCESS(projectUser);
    }

    /**
     * 功能描述：删除记录
     *  踢出
     *
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/5 10:29
     * 参数：[ * @param null]
     * 返回值：
     */
    @DeleteMapping
    public ResultData delete(String id){
        boolean b = userProjectStationService.updateUserStationById(new UserProjectStation().setId(id).setStatus(0));
        if(b){
            return ResultData.WRITE(200,"删除成功");
        }
        return ResultData.FAILED();
    }
    /**
     * 功能描述：修改个人在项目部状态
     *  撤回
     *  踢出
     *
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/5 10:29
     * 参数：[ * @param null]
     * 返回值：
     */
    @PutMapping("/out")
    public ResultData kickedOut(String id){
        UserProjectStation station = userProjectStationService.getById(id);
        if("admin".equals(station.getRole())){
            return ResultData.WRITE(400,"超级管理员不能踢出");
        }
        boolean b = userProjectStationService.updateUserStationById(new UserProjectStation().setId(id).setStatus(6));
        if(b){
            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }



    //###############群组管理后台  end --------




    /**
     * 功能描述：获取y用户所有委派记录
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String user_id,String company_id
     * 返回值：
     * 异常：
     */
    @GetMapping("/delegate/{user_id}/{company_id}")
    public PageInfo<UserStationDto> getDelegateStation(Integer pageNum, Integer pageSize,
                                                          @PathVariable String company_id,
                                                          @PathVariable String user_id) throws Exception {


        return userProjectStationService.getUserDelegateStation(pageSize,pageNum,user_id,company_id);

    }
    /**
     * 功能描述：获取公司没有委派的人员
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @GetMapping("/delegate/{company_id}")
    public ResultData noDelegate(@PathVariable String company_id)throws Exception{
        List<User> userCompanies = userProjectStationService.gatCompanyNoDelegate(company_id);
        return ResultData.SUCCESS(userCompanies);
    }

    /**
     * 功能描述：获取所zai项目部的历史岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String user_id,String company_id
     * 返回值：
     * 异常：
     */
    @GetMapping("/4/{user_id}/{project_department_id}")
    public PageInfo<UserStationDto> getHistoryStation(Integer pageNum, Integer pageSize,
                                                          @PathVariable String project_department_id,
                                                          @PathVariable String user_id) throws Exception {


        return userProjectStationService.getUserProjectDepartmentStation(pageSize,pageNum,user_id,project_department_id,4);

    }
    /**
     * 功能描述：获取在该项目部的岗位信息
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：id 记录id
     * 返回值：
     * 异常：
     */
    @GetMapping("/1/{id}")
    public UserStationDto getUserNowStation( @PathVariable String id) throws Exception {

        return userProjectStationService.getUserProjectDepartmentStationById(id);

    }


    /**
     * 功能描述：获取公司在该项目的所有成员
     *  1 在项目中
     *  6 未在项目中--离职
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String user_id,String company_id
     * 返回值：
     * 异常：
     */
    @GetMapping("/company/users")
    public PageInfo<UserStationDto> getPDUsersByCompany(Integer pageNum, Integer pageSize,
                                                        String project_department_id, String company_id, Integer status) throws Exception {

        return userProjectStationService.getPDUsersByCompany(pageSize,pageNum,company_id,project_department_id,status);

    }




    /**
     * 功能描述：修改个人在项目部状态
     *  撤回
     *  踢出
     *
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/5 10:29
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping
    public ResultData updateUserProjectStatus( String id,Integer status){
        boolean b = userProjectStationService.updateUserStationById(new UserProjectStation().setId(id).setStatus(status));
        if(b){

            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }

    /**
     * 功能描述：修改多个用户在项目部状态
     *  撤回
     *
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/5 10:29
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("/many")
    public ResultData updateUserProjectStatus(@RequestBody  PageData pageData){
        List<UserProjectStation> userProjectStations = JSON.parseArray(JSON.toJSONString(pageData.get("userProjectStations")),UserProjectStation.class);
        boolean allBack = true;
        for (int i = 0; i < userProjectStations.size(); i++) {
            if(!userProjectStationService.updateUserStationById(userProjectStations.get(i).setStatus(6))){
                allBack = false;
            }
        }
        return ResultData.WRITE(200,allBack?"全部撤回":"部分用户撤回失败");

    }




    /**
     * 功能描述：企业委派人员
     * 开发人员： lyx  JSON.stringify
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PostMapping
    public ResultData userToProjectDepartment(@RequestBody  PageData pageData)throws Exception{
        List<UserProjectStation> userStations = JSON.parseArray(JSON.toJSONString(pageData.get("userProjectStations")),UserProjectStation.class);
        List<UserProjectStation> userProjectStation = new ArrayList<>();
        StringBuffer unUser = new StringBuffer();
        for (int i = 0; i < userStations.size(); i++) {
            UserProjectStation station = userStations.get(i);
            //1查看该人是否有其他委派
            if(userProjectStationService.getUserProjectDepartmentStationRecord(station.getUser_id(),1).size()==0){
                userProjectStation.add(station);
            }else {
                unUser.append(userService.getById(station.getUser_id()).getName()).append("（有委派）").append(",");
            }
        }

        String id = userService.getNowUser(request).getId();
        userProjectStation.forEach(ups->{
            long total = userProjectStationService.getProjectUser(ups.getProject_department_id(), 1, null, null).getTotal();
            ups.setId(UUID.randomUUID().toString()).setAppoint_id(id).setStatus(1).setJoin_time(new Date())
                    .setRole("user").setCode(Calendar.getInstance().get(Calendar.YEAR)+"0000"+total);
            if(!userProjectStationService.addUserToProjectDepartment(ups)){
                unUser.append(userService.getById(ups.getUser_id()).getName()).append("（添加失败）,");
            }

        });

        return ResultData.WRITE(200,unUser.length()!=0?unUser+"其余人委派成功":"全部委派成功");
    }



    /**  需要管理员
     * 功能描述：取消委派委派
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
//    @DeleteMapping ("")
    public ResultData undoToProjectDepartment(String id)throws Exception{
        if(userProjectStationService.updateUserStationById(new UserProjectStation().setId(id).setStatus(5))){
            return ResultData.WRITE(200,"取消成功");
        }
        return ResultData.FAILED();
    }




}