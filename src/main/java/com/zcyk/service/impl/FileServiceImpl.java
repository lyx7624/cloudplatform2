package com.zcyk.service.impl;

import com.zcyk.dao.UploadFileDao;
import com.zcyk.entity.UploadFile;
import com.zcyk.service.FileService;
import com.zcyk.util.FileMd5Util;
import com.zcyk.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 功能描述:
 * 开发人员: xlyx
 * 创建日期: 2019/8/10 9:11
 */
@Service("FileService")
@Transactional
public class FileServiceImpl implements FileService {

    @Value(value = "${contextPath}")
    public String contextPath;

    @Resource
    UploadFileDao uploadFileDao;
    @Override
    public void getImage(String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        response.setContentType("image/gif");
        byte[] b = new byte[fis.available()];
        fis.read(b);
        out.write(b);
        out.flush();
        FileUtil.downStream(out,fis);

    }

    /**
     * 功能描述：上传文件到服务器
     * 开发人员： lyx
     * 创建时间： 2019/9/25 14:39
     * 参数：
     * 返回值：
     * 异常：
     */
    public String upFileToServer(MultipartFile file,String contextPath,String fileName) throws Exception {

        //判断文件是否为空
        if (file.isEmpty()) {
            throw new IOException("文件为空");
        }

        //判断该文件是否已经上传
        String fileMD5 = FileMd5Util.getMultipartFileMd5(file);
        UploadFile existFile = uploadFileDao.findFileByMd5(fileMD5);
        if(existFile!=null && existFile.getMd5()!=null){//说明文件已经上传
            System.out.println("文件已上传");
            return existFile.getPath();
        }else {
            //创建文件夹
            File fileFolder = new File(contextPath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            //获取文件字节流
            //获取path对象
            UploadFile uploadFile = new UploadFile().setName(fileName==null?file.getOriginalFilename():fileName);


            fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") == -1 ? file.getOriginalFilename().length() : file.getOriginalFilename().indexOf("."));

            File transFile = new File(contextPath  + fileName);
            file.transferTo(transFile);
            //写入记录

            uploadFile.setMd5(fileMD5)
                    .setCreate_time(new Date())
                    .setId(UUID.randomUUID().toString())
                    .setName(file.getOriginalFilename())
                    .setPath(fileName)
                    .setSize(file.getSize())
                    .setStatus(1).setUpdate_time(new Date())
                    .setSuffix(FileUtil.getExtensionName(file.getOriginalFilename()));
            uploadFileDao.insertSelective(uploadFile);
            return fileName;
        }
    }

    /**
     * 功能描述：下载单个文件
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/9 11:33
     * 参数：[ * @param null]
     * 返回值：
    */
    public String downloadFile(String filePath, HttpServletResponse response, HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException, UnknownHostException {
        String fileName = uploadFileDao.findFileNameByFilePath(filePath);
        return  FileUtil.downloadFile(fileName,contextPath+filePath,response,request);
    }

    /**
     * 功能描述：多文件打包下载
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/9 13:38
     * 参数：[ * @param null]
     * 返回值：
    */
//    {
//        int downloadType;
//        String filePath;
//        {
//            int type;
//            String path;
//            String id;
//        }
//    }
//     public void bulkDownloadFiles(List<Download> list){
//         String realPath = contextPath + "temp" + UUID.randomUUID().toString().replace("-", "") + "/";//创建根目录
//         java.io.File pathFile = new java.io.File(realPath);
//         try {
//             if (!pathFile.exists()) {//如果文件夹不存在
//                 pathFile.mkdirs();//创建多级文件夹
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         String downloadName = "下载.zip";
//         /**
//          * 预留自定义下载文件名
//          */
//         for(Download download : list){
//             if(download.getDownloadType()==1){
//
//             }
//         }
//     }

}