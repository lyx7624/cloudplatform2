package com.zcyk.service.impl;

import com.zcyk.entity.UploadFile;
import com.zcyk.dao.UploadFileDao;
import com.zcyk.service.UploadFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件存储表(UploadFile)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("uploadFileService")
public class UploadFileServiceImpl implements UploadFileService {
    @Resource
    private UploadFileDao uploadFileDao;

}