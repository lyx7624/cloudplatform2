package com.zcyk.controller;

import com.zcyk.dto.FileForm;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.UploadFile;
import com.zcyk.myenum.ResultCode;
import com.zcyk.service.FileService;
import com.zcyk.service.ProjectFileService;
import com.zcyk.util.File_upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * 功能描述:
 * 开发人员: xlyx
 * 创建日期: 2020/4/29 15:38
 */
@RestController
@Slf4j
@RequestMapping("file")
public class FileController {

    @Value(value = "${contextPath}")
    public String contextPath;

    @Resource
    FileService fileService;
    @Resource
    File_upload file_upload;
    @Resource
    ProjectFileService projectFileService;

    /**
     * 功能描述：普通上传文件
     * 开发人员：lxy
     * 创建时间：2020/5/6 13:56
     * 参数：[ * @param null]
     * 返回值：
    */
    @RequestMapping("/upload")
    public ResultData upload( MultipartFile file, String fileName){
        String pathName;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(suffix);
        try {
            if(suffix.equals("mp4")){
                pathName = fileService.upFileToServer(file, contextPath+"/video/", fileName);
            }else {
                pathName = fileService.upFileToServer(file, contextPath, fileName);
            }
        } catch (Exception e) {
            log.error("文件上传错误",e);
            return ResultData.WRITE(ResultCode.ERROR);
        }
        return ResultData.SUCCESS(pathName);
    }


    /**
     * 功能描述：分片上传
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 13:56
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("/isUpload")
    public ResultData isUpload(@Valid FileForm form) {
        try {
            Map<String, Object> map = file_upload.findByFileMd5(form.getMd5());
            return ResultData.SUCCESS(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED();
        }
    }

    @PostMapping("/realUpload")
    public ResultData upload(@Valid FileForm form,
                             @RequestParam(value = "data", required = false) MultipartFile multipartFile) throws Exception {
        try {
            Map<String, Object>  map = file_upload.realUpload(form, multipartFile,contextPath);
            return ResultData.SUCCESS(map);
        }catch (Exception e){
            log.error("分片上传报错",e);
            return ResultData.WRITE(400,"失败");
        }

    }

    /**
     * 根据路径获取图片
     *
     * @param path
     * @param response
     */
    @GetMapping(value = "/image/get/{path:.+}")
    public void getImage(@PathVariable String path, HttpServletResponse response) throws Exception {
        projectFileService.getImage(path, response);
    }


    /**
     * 功能描述：下载单个文件
     * 开发人员：Wujiefeng
     * 创建时间： 11:39
     * 参数：[ * @param null]
     * 返回值：
    */
    @RequestMapping("downloadFile")
    public void downloadFile(String filePath,
                             HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException, UnknownHostException {
      fileService.downloadFile(filePath,response,request);
    }
}