package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.ProjectFile;
import com.zcyk.service.ProjectFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目文件表(ProjectFile)表控制层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@RestController
@RequestMapping("projectFile")
public class ProjectFileController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectFileService projectFileService;

    /**
     * 功能描述：2D上传文件（添加到Project_file表）
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 17:51
     * 参数：[ * @param null]
     * 返回值：
    */
//    @RequestMapping("upFile")
//    public ResultData upFile(List<ProjectFile> projectFiles){
//        try {
//            projectFileService.upFile(projectFiles);
//            return ResultData.SUCCESS();
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResultData.FAILED();
//        }
//    }

    /**
     * 功能描述：删除文件
     * 开发人员：Wujiefeng
     * 创建时间：\ 19:11
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("deleteFile/{id}")
    public ResultData deleteFile(@PathVariable("id") String id){
        try {
            projectFileService.deleteFile(id);
            return ResultData.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED();
        }
    }



}