package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.UnitProject;
import com.zcyk.service.ProjectFileService;
import com.zcyk.service.UnitProjectService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * 子项目表(UnitProject)表控制层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@RestController
@RequestMapping("unitProject")
public class UnitProjectController {
    /**
     * 服务对象
     */
    @Resource
    private UnitProjectService unitProjectService;
    @Resource
    private ProjectFileService projectFileService;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("{method}")
    public ResultData restUniProject(@PathVariable("method") String method,
                                     @RequestBody @Valid UnitProject unitProject) throws  Exception{
        if (method.equals("add")) {//添加3D工程
            try {
                UnitProject unitProject1 = unitProjectService.addUniProject(unitProject);
                //绑定文件
                projectFileService.upFile(unitProject.getProjectFiles(),unitProject1.getId());
                return ResultData.WRITE(200,"添加成功",unitProject1);
            } catch (Exception e) {
                throw new Exception("添加失败");
            }
        } else if (method.equals("update")) {//修改
            try {
                UnitProject nowUnitProject = unitProjectService.updateUniProject(unitProject);
                return ResultData.WRITE(200,"修改成功",nowUnitProject);
            } catch (Exception e) {
               throw new Exception("修改失败");
            }
        } else if (method.equals("delete")) {//删除
            try {
                unitProjectService.deleteUniProject(unitProject);
                return ResultData.WRITE(200,"删除成功");
            } catch (Exception e) {
                throw new Exception("删除失败");
            }
        }
        return ResultData.WRITE(400, "接口不存在");
    }

    /**
     * 功能描述：查询3D工程
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 19:52
     * 参数：[ * @param null]
     * 返回值：
     */
    @PostMapping("getProject")
    public ResultData getProject(@RequestParam(defaultValue = "") String project_id,
                                 @RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "12") int pageSize,
                                 @RequestParam(defaultValue = "") String search,//检索信息
                                 @RequestParam(defaultValue = "1") Integer sort,
                                 @RequestParam(defaultValue = "") String level,//级别
                                 @RequestParam(defaultValue = "") String type,//类别
                                 @RequestParam(defaultValue = "") String phase ){
        if(project_id.isEmpty()){
            try {
                Map<String, Object> map = unitProjectService.getAllUniProject(pageNum, pageSize, sort, search, level, type, phase);
                return ResultData.SUCCESS(map);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.FAILED();
            }
        }
        try {
            Map<String, Object> map = unitProjectService.getUniProject(project_id, pageNum, pageSize, sort, search, level, type, phase);
            return ResultData.SUCCESS(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.FAILED();
        }
    }

    /**
     * 功能描述：查询单个3D工程
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 20:04
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("getOneUniProject/{id}")
    public ResultData getOneUniProject(@PathVariable("id")String id){
        try {
            Map<String, Object> oneUniProject = unitProjectService.getOneUniProject(id);
            return ResultData.SUCCESS(oneUniProject);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultData.FAILED();
        }
    }
}