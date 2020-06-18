package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.entity.User;
import com.zcyk.entity.UserCredential;
import java.util.List;

/**
 * 用户证书表(UserCredential)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UserCredentialService extends CredentialService<UserCredential>{

    PageInfo<UserCredential> getUserCredentialByTrade(String trade_id,
                                            int status,
                                            String area_code,
                                            String type,String level,
                                            Integer pageNum,
                                            Integer pageSize);
}