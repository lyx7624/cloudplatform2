package com.zcyk.controller;


import com.github.pagehelper.PageInfo;

import com.zcyk.entity.User;
import com.zcyk.service.UserCompanyStationService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 * 开发人员: xlyx
 * 创建日期: 2020/4/29 10:00
 */
@RestController
public class T {
    @Resource
    private UserCompanyStationService userCompanyStationService;


    @RequestMapping("/")
    public Long loginPage() {
        return  userCompanyStationService.gatAllUser("1", null, null,"", 1).getTotal();

    }


    @RequestMapping("/test")
    public PageInfo<User> age(String company_id) {
        return userCompanyStationService.gatAllUser(company_id, null, null, "", 1);
    }

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }



    public static void main(String[] args) throws IOException {


    }
}