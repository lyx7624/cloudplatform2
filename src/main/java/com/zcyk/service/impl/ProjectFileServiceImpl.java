package com.zcyk.service.impl;

import com.zcyk.entity.ProjectFile;
import com.zcyk.dao.ProjectFileDao;
import com.zcyk.service.ProjectFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 项目文件表(ProjectFile)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("projectFileService")
public class ProjectFileServiceImpl implements ProjectFileService {
    @Value(value = "${contextPath}")
    public String contextPath;

    @Resource
    private ProjectFileDao projectFileDao;

    public void upFile(List<ProjectFile> projectFiles,String project_id){

        for(ProjectFile projectFile:projectFiles){
            projectFileDao.insertSelective(projectFile.setId(UUID.randomUUID().toString())
                                             .setStatus(1)
                                              .setProject_id(project_id));
        }
    }

    @Override
    public void deleteFile(String id){
        projectFileDao.deleteFileById(id);
    }

    @Override
    public void deleteFileByProjectId(String project_id){
        projectFileDao.deleteFileByProjectId(project_id);
    }

    @Override
    public void getImage(String path, HttpServletResponse response) throws IOException {
        FileInputStream fis = null;
        OutputStream out = null;
        response.setContentType("image/gif");
        out = response.getOutputStream();
        java.io.File file = new java.io.File(contextPath + path);

        try {

            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}