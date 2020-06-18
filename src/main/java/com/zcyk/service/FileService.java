package com.zcyk.service;



import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Map;

/**
* 功能描述: w文件服务接口
* 版本信息: Copyright (c)2019
* 公司信息: 智辰云科
* 开发人员: lyx
* 版本日志: 1.0
* 创建日期: 2019/8/10 9:10
*/
public interface FileService {





    /*上传文件到服务器*/
    String upFileToServer(MultipartFile file, String contextPath, String fileName) throws Exception;


    /*获取文件流*/
    void getImage(String path, HttpServletResponse response) throws IOException;


    /*下载单个文件*/
    String downloadFile(String filePath, HttpServletResponse response, HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException, UnknownHostException;




}
