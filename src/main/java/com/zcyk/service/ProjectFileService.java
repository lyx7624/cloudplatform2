package com.zcyk.service;

import com.zcyk.entity.ProjectFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目文件表(ProjectFile)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectFileService {
    //绑定文件
    void upFile(List<ProjectFile> projectFiles,String project_id);
    //根据id删除文件
    void deleteFile(String id);
    //根据2D项目id查询文件/
    void deleteFileByProjectId(String project_id);

    /*获取上传的图片*/
    void getImage(String path, HttpServletResponse response) throws Exception;
}