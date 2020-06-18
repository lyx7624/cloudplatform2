package com.zcyk.dao;

import com.zcyk.entity.UploadFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 文件存储表(UploadFile)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UploadFileDao extends Mapper<UploadFile> {

    /*根据MD5获取文件信息 */
    @Select("select * from upload_file where md5 = #{md5}")
    UploadFile findFileByMd5(@Param("md5") String fileMD5);

    /*根据Path找文件名*/
    @Select("select upload_file.name from upload_file where path = #{path}")
    String findFileNameByFilePath(String path);
}