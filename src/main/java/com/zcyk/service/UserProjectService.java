package com.zcyk.service;

import com.zcyk.entity.UserProject;
import java.util.List;

/**
 * 公司用户表(UserProject)表服务接口
 *
 * @author makejava
 * @since 2020-05-06 17:23:03
 */
public interface UserProjectService {




    void updateByUserId(UserProject userProject,String userId);

    List<UserProject> getByUserId(String id);
}