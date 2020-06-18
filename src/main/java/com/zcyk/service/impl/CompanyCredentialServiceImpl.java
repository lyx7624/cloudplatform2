package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.Credential;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.dao.CompanyCredentialDao;
import com.zcyk.service.CompanyCredentialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 企业证书(CompanyCredential)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("companyCredentialService")
@Transactional
public class CompanyCredentialServiceImpl implements CompanyCredentialService {
    @Resource
    private CompanyCredentialDao companyCredentialDao;


    @Override
    public Credential selectByCodeAndId(String id, String code) {
        Example example = new Example(CompanyCredential.class);
        example.and().andEqualTo("code",code).andEqualTo("company_id",id);
        return companyCredentialDao.selectOneByExample(example);
    }

    @Override
    public void updateByCodeAndId(Credential credential) {
        companyCredentialDao.updateByPrimaryKeySelective((CompanyCredential) credential);

    }

    @Override
    public boolean addCredential(CompanyCredential companyCredential, String company_id) {
        return companyCredentialDao.insertSelective(companyCredential)>0;

    }


    @Override
    public void updateCredentials(List<CompanyCredential> companyCredentials, String company_id) {
        //删除以前的所有证书
        Example example = new Example(CompanyCredential.class);
        example.and().andEqualTo("company_id",company_id);
        companyCredentialDao.deleteByExample(example);

        //再次新增
        companyCredentials.forEach(companyCredential ->
                companyCredentialDao.insertSelective(
                        (CompanyCredential)companyCredential.setCompany_id(company_id).setId(UUID.randomUUID().toString()).setStatus(1)));

    }

    @Override
    public boolean updateCredentials(CompanyCredential companyCredential) {
        return companyCredentialDao.updateByPrimaryKeySelective(companyCredential)>0;
    }

    @Override
    public PageInfo<CompanyCredential> selectByUseId(String company_id, Integer pageSize, Integer pageNum, Integer status) {
        if(pageSize!=null&&pageNum!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        if(status==null){
            return new PageInfo<>(companyCredentialDao.getAllByCompanyId(company_id));
        }
        return new PageInfo<>(companyCredentialDao.getByCompanyId(company_id,status));
    }

    @Override
    public CompanyCredential selectById(String id) {
        return companyCredentialDao.getById(id);
    }
}