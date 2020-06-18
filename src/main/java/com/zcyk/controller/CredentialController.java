//package com.zcyk.controller;
//
//import com.zcyk.dto.Credential;
//import com.zcyk.exception.APIException;
//import com.zcyk.service.CompanyCredentialService;
//import com.zcyk.service.CredentialService;
//import com.zcyk.service.ProjectGroupCredentialService;
//import com.zcyk.service.UserCredentialService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * 证书(CompanyCredential)表控制层
// *
// * @author makejava
// * @since 2020-04-28 17:29:17
// */
//@RestController
//@RequestMapping("credential")
//public class CredentialController {
//    /**
//     * 企业证书服务对象
//     */
//    @Resource
//    private CompanyCredentialService companyCredentialService;
//    /**
//     * 个人证书服务对象
//     */
//    @Resource
//    private UserCredentialService userCredentialService;
//
//    /**
//     * 项目证书服务对象
//     */
//    @Resource
//    private ProjectGroupCredentialService projectGroupCredentialService;
//
//
//    public CredentialService getService(String credentialType) throws APIException {
//        if("projectGroup".equals(credentialType)){
//            return projectGroupCredentialService;
//        }else if ("user".equals(credentialType)){
//            return userCredentialService;
//        }else if ("company".equals(credentialType)){
//            return companyCredentialService;
//        }else {
//            throw new APIException("接口不存在");
//        }
//    }
//
//    /**
//     * 功能描述：根据关联id查询证书
//     * 开发人员： lyx
//     * 创建时间： 2020/4/29 10:43
//     * 参数： [credentialType company user projectGroup, id]
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @GetMapping("{credentialType}/{id}")
//    public List getCredential(@PathVariable String credentialType, @PathVariable String id) throws Exception {
//        return getService(credentialType).selectByUseId(id, pageSize, pageNum);
//
//    }
//
//    /**
//     * 功能描述：添加证书
//     * 开发人员： lyx
//     * 创建时间： 2020/4/29 10:43
//     * 参数： [credentialType company user projectGroup, id关联的id]
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping("{credentialType}/{id}")
//    public String addCredential(@PathVariable String credentialType, @PathVariable String id,@RequestBody @Valid Credential credential) throws Exception {
//        CredentialService service = getService(credentialType);
//        if(service.selectByCodeAndId(credential.getCode(), id)!=null){
//            return "证书已存在";
//        }
////        credential.setStatus(1).setId(UUID.randomUUID().toString());
//        getService(credentialType).addCredential(credential,id);
//        return "添加成功";
//
//    }
//
//
//
//
//    /**
//     * 功能描述：修改证书
//     * 开发人员： lyx
//     * 创建时间： 2020/4/29 10:43
//     * 参数： [credentialType company user projectGroup, 删除 status = 0]
//     * 返回值： com.zcyk.dto.ResultData
//     * 异常：
//     */
//    @PutMapping("{credentialType}")
//    public String updateCredential(@PathVariable String credentialType,@RequestBody @Valid Credential credential) throws Exception {
//        getService(credentialType).updateByCodeAndId(credential);
//        return "操作成功";
//    }
//
//
//
//}