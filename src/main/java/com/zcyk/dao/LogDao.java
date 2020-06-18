package com.zcyk.dao;

import com.zcyk.dto.SysLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface LogDao extends Mapper<SysLog> {


}