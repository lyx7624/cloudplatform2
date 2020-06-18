package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.CompanyProcessDao;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.CompanyProcess;
import com.zcyk.entity.CompanyRelation;
import com.zcyk.dao.CompanyRelationDao;
import com.zcyk.service.CompanyRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 公司关系表(CompanyRelation)表服务实现类
 *
 * @author makejava
 * @since 2020-05-29 17:02:45
 */
@Service("companyRelationService")
public class CompanyRelationServiceImpl implements CompanyRelationService {
    @Resource
    private CompanyRelationDao companyRelationDao;
    @Resource
    private CompanyProcessDao companyProcessDao;



    @Override
    public PageInfo<CompanyRelation> getAllRelation(String company_id, Integer pageNum, Integer pageSie, String search, Integer status) {
        if(pageNum!=null&&pageSie!=null){
            PageHelper.startPage(pageNum,pageSie);
        }
        return new PageInfo<>(companyRelationDao.selectByCompanyStatusType(company_id,search,status));
    }

    @Override
    public PageInfo<CompanyRelation> getByRelation(String relation_id, Integer pageNum, Integer pageSie, String search, int status) {
        if(pageNum!=null&&pageSie!=null){
            PageHelper.startPage(pageNum,pageSie);
        }
        return new PageInfo<>(companyRelationDao.byRelation(relation_id,search,status));
    }

    @Override
    public boolean addRelation(CompanyRelation companyRelation) {
        return companyRelationDao.insertSelective(companyRelation)>0;

    }

    @Override
    public ResultData cancelAddRelation(CompanyRelation setId) {
        return null;
    }

    @Override
    public boolean updateRelationById(CompanyRelation companyRelation) {
        return companyRelationDao.updateByPrimaryKeySelective(companyRelation)>0;
    }



    @Override
    public CompanyRelation getUpRelation(String company_id) {
        return companyRelationDao.selectOne(new CompanyRelation().setCompany_id(company_id).setStatus(1).setType(1));
    }

    /*查询已添加的公司*/
    @Override
    public PageInfo<CompanyRelation> selectNoCheck(String company_id,Integer pageNum,Integer pageSize,String search){
        if(pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<CompanyRelation> companyRelations = companyRelationDao.selectNoCheck(company_id,search);
        if(StringUtils.isNotBlank(search)){
            //模糊搜索
            List<CompanyRelation> collect = companyRelations.stream().filter(cs ->
                    (cs.getCompany_name()!= null && cs.getCompany_name().contains(search)) ||
                            (cs.getRelation_name() != null && cs.getRelation_name().contains(search)) ||
                            (cs.getCompany_code() != null && cs.getCompany_code().contains(search)) ||
                            (cs.getApplicant() != null && cs.getApplicant().contains(search)) ||
                            (cs.getAudit() != null && cs.getAudit().contains(search)) ||
                            (cs.getCompany_address() != null && cs.getCompany_address().contains(search))
            ).collect(Collectors.toList());

            return new PageInfo<>(collect);
        }
        return new PageInfo<>(companyRelations);
    }
    /*查询待审核的记录*/
    @Override
    public PageInfo<CompanyRelation> selectCheck(String company_id,Integer pageNum,Integer pageSize,String search){
        if(pageNum!=null&&pageSize!=null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<CompanyRelation> companyRelations = companyRelationDao.selectCheck(company_id,search);
        if(StringUtils.isNotBlank(search)){
            //模糊搜索
            List<CompanyRelation> collect = companyRelations.stream().filter(cs ->
                    (cs.getCompany_name()!= null && cs.getCompany_name().contains(search)) ||
                            (cs.getRelation_name() != null && cs.getRelation_name().contains(search)) ||
                            (cs.getCompany_code() != null && cs.getCompany_code().contains(search)) ||
                            (cs.getApplicant() != null && cs.getApplicant().contains(search)) ||
                            (cs.getAudit() != null && cs.getAudit().contains(search)) ||
                            (cs.getCompany_address() != null && cs.getCompany_address().contains(search))
            ).collect(Collectors.toList());

            return new PageInfo<>(collect);
        }
        return new PageInfo<>(companyRelations);
    }
    /*查询已审核的记录*/
    public PageInfo<CompanyRelation> selectChecked(String company_id,Integer pageNum,Integer pageSize,String search){
        if(pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<CompanyRelation> companyRelations = companyRelationDao.selectChecked(company_id,search);
        if(StringUtils.isNotBlank(search)){
            //模糊搜索
            List<CompanyRelation> collect = companyRelations.stream().filter(cs ->
                    (cs.getCompany_name()!= null && cs.getCompany_name().contains(search)) ||
                            (cs.getRelation_name() != null && cs.getRelation_name().contains(search)) ||
                            (cs.getCompany_code() != null && cs.getCompany_code().contains(search)) ||
                            (cs.getApplicant() != null && cs.getApplicant().contains(search)) ||
                            (cs.getAudit() != null && cs.getAudit().contains(search)) ||
                            (cs.getCompany_address() != null && cs.getCompany_address().contains(search))
            ).collect(Collectors.toList());

            return new PageInfo<>(collect);
        }
        return new PageInfo<>(companyRelations);
    }
    /*修改状态*/
    public ResultData updateCompanyRelation(CompanyRelation companyRelation){

        int i= companyRelationDao.updateByPrimaryKeySelective(companyRelation);
        return ResultData.SUCCESS();

    }

    public boolean relatedCompany(CompanyRelation companyRelation){
        List<CompanyRelation> companyRelations = companyRelationDao.relatedCompany(companyRelation.getCompany_id(), companyRelation.getRelation_id());
        if (companyRelations.size()!=0){
            return true;
        }
        return false;
    }

//    //*********************************流程表+关系表************************************//
//    /*发起添加流程*/
//    public ResultData addProcess(CompanyProcess companyProcess){//1、controller添加发起人及发起人公司，2、处理人公司id 3、状态
//        if(companyProcess.getStatus()==1){//添加上级
//            //判断是否已有上级
//            Example example = new Example(CompanyRelation.class);
//            example.and().andEqualTo("company_id",new CompanyRelation().setCompany_id(companyProcess.getInitiator_company_id()));
//            List<CompanyRelation> companyRelations = companyRelationDao.selectByExample(example);
//            if (companyRelations!=null){
//                return ResultData.WRITE(400,"已有上级单位");
//            }
//        }
//        companyProcess.setId(UUID.randomUUID().toString());
//        int insert = companyProcessDao.insert(companyProcess);
//        if(insert!=0){
//            return ResultData.SUCCESS();
//        }
//        return ResultData.FAILED();
//    }
//    /*处理流程*/
//    /*待审核（1、添加上级、2、添加下级、3、申请退出）、已审核（4、同意加入、5、同意退出、6、拒绝加入、7、拒绝退出、8、撤回9、踢出）*/
//    public ResultData handleProcess(CompanyProcess companyProcess){
//        CompanyProcess oldcompanyProcess = companyProcessDao.selectByPrimaryKey(companyProcess);
//        if(companyProcess.getStatus()==4){//同意加入
//            //修改流程状态、设当前登录人为审核人
//            companyProcessDao.updateByPrimaryKey(companyProcess);
//            //关联关系表(添加关系)
//            if(oldcompanyProcess.getStatus()==1){//同意添加上级（当前登录人为上级）
//                companyRelationDao.insert(new CompanyRelation().setId(UUID.randomUUID().toString())
//                        .setCompany_id(oldcompanyProcess.getInitiator_company_id())
//                        .setRelation_id(companyProcess.getHandler_company_id()));
//            }else {//同意添加下级(当前登录人为下级)
//                companyRelationDao.insert(new CompanyRelation().setId(UUID.randomUUID().toString())
//                        .setCompany_id(companyProcess.getHandler_company_id())
//                        .setRelation_id(oldcompanyProcess.getInitiator_company_id()));
//            }
//            return ResultData.SUCCESS();
//        }else if(companyProcess.getStatus()==5){//同意退出
//            //修改流程状态、设当前登录人为审核人
//            companyProcessDao.updateByPrimaryKey(companyProcess);
//            //修改关联关系（解除关系）设关系表状态为0
//            companyRelationDao.updateByExampleSelective();
//            return ResultData.SUCCESS();
//        }   //状态6、7、8、9（对关系没有影响）
//            //设当前登录人为处理人
//            //nowUser
//            companyProcessDao.updateByPrimaryKey(companyProcess);
//            return ResultData.SUCCESS();
//    }
//
//
//
//    /*查询流程*/
//    public ResultData getProcess(String company_id,Integer type){//type:1、已添加2、待审核3、已审核
//        HashMap<String, Object> map = new HashMap<>();
//        if (type==1){//已添加
//           //查出关系表中的对应公司的上下级公司
//           //1、先查出上级公司 List upCompany
//           Example example = new Example(CompanyRelation.class);
//           example.and().andEqualTo("company_id",company_id);
//           List<CompanyRelation> upCompanys = companyRelationDao.selectByExample(example);
//           //2、再查出下级公司 List underCompany
//           Example example1 = new Example(CompanyRelation.class);
//           example1.and().andEqualTo("relation",company_id);
//           List<CompanyRelation> underCompanys = companyRelationDao.selectByExample(example1);
//           //查出对应的流程信息及公司信息（）
//
//       }else if(type==2){//待审核
//           //查出流程信息表中发起公司及审核公司为自己公司的数据（状态：1、2、3）
//           //1、先查出自己为发起公司的数据 list initiator
//           //2、再查出自己为审核公司的数据 list handler
//       }else if(type==3){//已审核
//           //1、查出自己为发起公司的数据，状态（4、5、6、7、8、9）
//           //2、查出自己为审核公司的数据，状态（4、5、6、7、8、9）
//       }
//    }

}