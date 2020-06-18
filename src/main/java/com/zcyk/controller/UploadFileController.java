package com.zcyk.controller;

import com.zcyk.entity.UploadFile;
import com.zcyk.service.UploadFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文件存储表(UploadFile)表控制层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@RestController
@RequestMapping("uploadFile")
public class UploadFileController {
    /**
     * 服务对象
     */
    @Resource
    private UploadFileService uploadFileService;


}