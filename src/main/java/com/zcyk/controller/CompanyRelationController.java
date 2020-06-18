package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyRelation;
import com.zcyk.entity.User;
import com.zcyk.service.CompanyRelationService;
import com.zcyk.service.CompanyService;
import com.zcyk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

/**
 * 公司关系表(CompanyRelation)表控制层
 *
 * @author makejava
 * @since 2020-05-29 17:02:45
 */
@RestController
@Slf4j
@RequestMapping("companyRelation")
public class CompanyRelationController {

    /**
     * 服务对象
     */
    @Resource
    private CompanyRelationService companyRelationService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Resource
    private HttpServletRequest request;






    /**
     * 功能描述：查询已添加的企业
     * 开发人员：Wujiefeng
     * 创建时间： 9:26
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("/noCheck/{company_id}")
    public ResultData noCheckRelation(@PathVariable String company_id,Integer pageNum,Integer pageSize,String search){
        PageInfo<CompanyRelation> companyRelations = companyRelationService.selectNoCheck(company_id,pageNum,pageSize,search);
        return ResultData.SUCCESS(companyRelations);
    }

    /**
     * 功能描述：查询未审核的企业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/7 10:48
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("/Check/{company_id}")
    public ResultData checkRelation(@PathVariable String company_id,Integer pageNum,Integer pageSize,String search){
        PageInfo<CompanyRelation> companyRelations = companyRelationService.selectCheck(company_id,pageNum,pageSize,search);
        return ResultData.SUCCESS(companyRelations);
    }
    /**
     * 功能描述：查询已审核的企业
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/7 11:00
     * 参数：[ * @param null]
     * 返回值： 
    */
    @GetMapping("/Checked/{company_id}")
    public ResultData checkedRelation(@PathVariable String company_id,Integer pageNum,Integer pageSize,String search) {
        PageInfo<CompanyRelation> companyRelationPageInfo = companyRelationService.selectChecked(company_id, pageNum, pageSize, search);
        return ResultData.SUCCESS(companyRelationPageInfo);
    }
    @PutMapping("deal")
    public ResultData dealRelation(CompanyRelation companyRelation){
        User nowUser = userService.getNowUser(request);
        if (companyRelation.getFlow_status()==1
            ||companyRelation.getFlow_status()==2
            ||companyRelation.getFlow_status()==4
            ||companyRelation.getFlow_status()==6
            ||companyRelation.getFlow_status()==7)
        {
            companyRelation.setAudit_id(nowUser.getId());
        }
        ResultData resultData = companyRelationService.updateCompanyRelation(companyRelation);
        return resultData;
    }


    /**
     * 功能描述：申请添加上下级
     * 开发人员： lyx
     * 创建时间： 2020/5/29 17:14
     * 参数： [companyRelation] type = 1 上级 type = 2 下级 update表示是否修改上级 添加下级没有
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @PostMapping
    public ResultData addRelation(@Valid CompanyRelation companyRelation){
        User nowUser = userService.getNowUser(request);
        //自己不能添加自己
        if(companyRelation.getCompany_id().equals(companyRelation.getRelation_id())){
            return ResultData.WRITE(400,"不能添加本公司");
        }
        //重复添加
        if(companyRelationService.relatedCompany(companyRelation)){
            return ResultData.WRITE(400,"请勿重复添加");
        }

        //已经有上级了不能再添加上级 或者就直接修改
        CompanyRelation relation;
        try {
            relation = companyRelationService.getUpRelation(companyRelation.getCompany_id());
        }catch (Exception e){
            log.error("数据错误，查询到企业两个上级",e);
            return ResultData.WRITE(400,"企业关系错乱，请联系超级管理员");
        }
        boolean success;

        if(relation!=null && companyRelation.getUpdate()!=1){
            return ResultData.WRITE(402,"已有上级不能新增是否修改");
        }
        //判断这两个企业关系是否已存在

        //判断是否有密码
        Company company = companyService.getCompanyById(companyRelation.getRelation_id());
        if(company.getCompany_join_password()!=null ){
            if(companyRelation.getCompany_join_password()==null){//有密码没有输入密码
                return ResultData.WRITE(401,"请输入加入密码");
            }
            if(!companyRelation.getCompany_join_password().equals(companyRelation.getCompany_join_password())){//输入密码错误
                return ResultData.WRITE(401,"密码错误");
            }
        }
        companyRelation.setJoin_time(new Date())
                .setApplicant_id(nowUser.getId())
                .setStatus(0).setFlow_status(0);
        if(companyRelation.getUpdate()==null || companyRelation.getUpdate()!=1){//添加
            if(companyRelationService.addRelation(companyRelation.setId(UUID.randomUUID().toString()).setApplicant_type(1))){
                return ResultData.WRITE(200,"添加成功");
            }

        }else {//修改
            if(companyRelationService.updateRelationById(companyRelation.setId(relation.getId()))){
                return ResultData.WRITE(200,"修改成功");
            }

        }
        return ResultData.FAILED();


    }














    //管理员权限 雷银祥 未用##############################

//
//    /**
//     * 功能描述：获取企业正在的所以级别关系
//     * 开发人员： lyx      company_id or relation_id
//     * 创建时间： 2020/5/29 17:11
//     * 参数： [pageNum, pageSie, company_id]
//     * 返回值： com.github.pagehelper.PageInfo<com.zcyk.entity.CompanyRelation>
//     * 异常：
//     */
//    @GetMapping(value = "/s/{company_id}")
//    public PageInfo<CompanyRelation> getAllRelation(Integer pageNum, Integer pageSie, @PathVariable String company_id, String search){
//        return companyRelationService.getAllRelation(company_id,pageNum,pageSie,search,1);
//    }
//
//
//
//
//    /**  0 删除  1在场中 2申请中 3申请失败 4不在场 5撤销申请
//     * 功能描述：获取企业已经添加的级别关系  company_id
//     * 开发人员： lyx  3被拒绝的
//     * 创建时间： 2020/5/29 17:11
//     * 参数： [pageNum, pageSie, company_id]
//     * 返回值： com.github.pagehelper.PageInfo<com.zcyk.entity.CompanyRelation>
//     * 异常：
//     */
//    @GetMapping(value = "/s/{company_id}/{status}")
//    public PageInfo<CompanyRelation> getAllRelationByTS(Integer pageNum, Integer pageSie, @PathVariable String company_id, String search, @PathVariable Integer status){
//        return companyRelationService.getAllRelation(company_id,pageNum,pageSie,search,status);
//    }
//
//
//    /**  需要被自己审核的企业
//     * 功能描述：获取企业已经添加的级别关系
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:11
//     * 参数： [pageNum, pageSie, company_id]
//     * 返回值： com.github.pagehelper.PageInfo<com.zcyk.entity.CompanyRelation>
//     * 异常：
//     */
//    @GetMapping(value = "/need_audit/{company_id}")
//    public PageInfo<CompanyRelation> getNeedAudit(Integer pageNum, Integer pageSie, @PathVariable String company_id, String search){
//        return companyRelationService.getByRelation(company_id,pageNum,pageSie,search, 2);
//    }
//
//    /**  获取被自己拒绝的记录
//     * 功能描述：获取企业已经添加的级别关系
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:11
//     * 参数： [pageNum, pageSie, company_id]
//     * 返回值： com.github.pagehelper.PageInfo<com.zcyk.entity.CompanyRelation>
//     * 异常：
//     */
//    @GetMapping(value = "/reject/{relation_id}")
//    public PageInfo<CompanyRelation> getMyReject(Integer pageNum, Integer pageSie, String search, @PathVariable String relation_id){
//        return companyRelationService.getByRelation(relation_id,pageNum,pageSie,search,3);
//    }
//
//
//
//
//
//
//    /**
//     * 功能描述：撤销上下级申请
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:14
//     * 参数： [companyRelation] type = 1 上级 type = 2 下级
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping
//    public ResultData cancelAddRelation(CompanyRelation companyRelation){
//        User nowUser = userService.getNowUser(request);
//
//        boolean code =  companyRelationService.updateRelationById(companyRelation.setApplicant_id(nowUser.getId()).setStatus(5));
//        if(code){
//            return ResultData.WRITE(200,"撤销申请成功");
//        }
//        return ResultData.FAILED();
//    }
//
//
//    /**
//     * 功能描述：申请解除关系
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:14
//     * 参数： [companyRelation] type = 1 上级 type = 2 下级 update表示是否修改上级 添加下级没有
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping("/r_relation")
//    public ResultData removeRelation(String id){
//
//        if(companyRelationService.updateRelationById(new CompanyRelation().setStatus(2).setApplicant_type(2).setId(id))){
//            return ResultData.WRITE(200,"已申请，等待审核");
//        }
//        return ResultData.FAILED();
//
//    }
//
//    /**
//     * 功能描述：取消申请解除关系
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:14
//     * 参数： [companyRelation] type = 1 上级 type = 2 下级 update表示是否修改上级 添加下级没有
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping("/cr_relation")
//    public ResultData cancelRemoveRelation(String id){
//        if(companyRelationService.updateRelationById(new CompanyRelation().setStatus(1).setId(id))){
//            return ResultData.WRITE(200,"已取消");
//        }
//        return ResultData.FAILED();
//
//    }
//
////    /**
////     * 功能描述：修改上下级关系
////     * 开发人员： lyx
////     * 创建时间： 2020/5/29 17:14
////     * 参数： [companyRelation] type = 1 上级 type = 2 下级
////     * 返回值： com.zcyk.dto.ResultData
////     * 异常：
////     */
////    @PutMapping(value = "")
////    public ResultData updateRelationById(CompanyRelation companyRelation){
////        User nowUser = userService.getNowUser(request);
////        return companyRelationService.updateRelationById(companyRelation
////                .setJoin_time(new Date())
////                .setApplicant_id(nowUser.getId())
////                .setStatus(2).setId(UUID.randomUUID().toString()));
////    }
//
//
//
//
//
//    //管理员操作
//
//
//    /**
//     * 功能描述：操作申请  添加申请
//     *                    0删除  3不通过 1通过
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:14
//     * 参数： [companyRelation] type = 1 上级 type = 2 下级
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping(value = "/a/{status}/{id}")
//    @Transactional(rollbackFor = Exception.class)
//    public ResultData passAddRelation(@PathVariable Integer status, @PathVariable String id) throws Exception {
//        User nowUser = userService.getNowUser(request);
//        CompanyRelation relation = new CompanyRelation().setId(id).setStatus(status).setAudit_id(nowUser.getId());
//        boolean code =  companyRelationService.updateRelationById(relation);//修改本来的数据 通过添加
//        if(code && status==1) {
//            CompanyRelation companyRelation = companyRelationService.getUpRelation(relation);//获取到本条信息的完整信息
//            //通过新增申请给自己添加一个对应的数据
//            String company_id = companyRelation.getRelation_id();
//            String relation_id = companyRelation.getCompany_id();
//            Integer type = companyRelation.getType() == 1 ? 2 : 1;
//            boolean addRelation = companyRelationService.addRelation(companyRelation.setId(UUID.randomUUID().toString()).setCompany_id(company_id)
//                    .setRelation_id(relation_id).setType(type));
//            if (addRelation) {
//                return ResultData.SUCCESS();
//            }
//            throw new Exception("通过失败");//进行数据回滚
//        }
//
//        return ResultData.FAILED();
//    }
//
//
//    /**
//     * 功能描述：操作申请  添加申请，退出申请
//     *                    0删除  3不通过 1通过
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:14
//     * 参数： [companyRelation] type = 1 上级 type = 2 下级
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping(value = "/q/{status}/{id}")
//    @Transactional(rollbackFor = Exception.class)
//    public ResultData passQuitRelation(@PathVariable Integer status, @PathVariable String id) throws Exception {
//        User nowUser = userService.getNowUser(request);
//        CompanyRelation relation = new CompanyRelation().setId(id).setStatus(status).setAudit_id(nowUser.getId());
//        boolean code =  companyRelationService.updateRelationById(relation);
//        if(code && status==1){//通过解除关系
//            CompanyRelation companyRelation = companyRelationService.getUpRelation(relation);//获取到本条信息的完整信息
//
//           //通过上翻转 company_id 和 relation_id 找到相关关系撤销关系
//            CompanyRelation relationRecord = companyRelationService.getUpRelation(new CompanyRelation()
//                    .setCompany_id(companyRelation.getRelation_id()).setRelation_id(companyRelation.getCompany_id()).setStatus(1));
//
//            boolean passQuitRelation =  companyRelationService.updateRelationById(relationRecord.setAudit_id(nowUser.getId()).setStatus(4));
//            if(passQuitRelation){
//                return ResultData.SUCCESS();
//            }
//            throw new Exception("通过失败");//进行数据回滚
//        }
//
//        return ResultData.FAILED();
//    }
//
//    /**
//     * 功能描述：删除
//     *                    0删除  3不通过 1通过
//     * 开发人员： lyx
//     * 创建时间： 2020/5/29 17:14
//     * 参数： [companyRelation] type = 1 上级 type = 2 下级
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @DeleteMapping(value = "/{id}")
//    public ResultData deleteRelation( @PathVariable String id) throws Exception {
//        CompanyRelation relation = companyRelationService.getUpRelation(new CompanyRelation().setId(id));
//        if(relation.getStatus()==1||relation.getStatus()==2){
//            return ResultData.WRITE(400,"请先解除关系再删除");
//        }
//        if(companyRelationService.updateRelationById(relation.setStatus(0))){
//            return ResultData.WRITE(200,"删除成功");
//        }
//        return ResultData.FAILED();
//    }
//




}