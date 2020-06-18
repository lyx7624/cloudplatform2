package com.zcyk.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zcyk.dto.Goujian;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.Project;
import com.zcyk.service.ProjectFileService;
import com.zcyk.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.TinyBitSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * 项目(Project)表控制层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@RestController
@RequestMapping("project")
public class ProjectController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectFileService projectFileService;


    /**
     * 功能描述：增加/修改2D项目
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 16:11
     * 参数：[ * @param null]
     * 返回值：
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("{methodType}")
    public ResultData addProject(@PathVariable("methodType") String mothedType,
                                 @RequestBody  @Valid Project project) throws Exception{
        if (mothedType.equals("add")) {//新增
            try {
                Project nowProject = projectService.addProject(project);
                if (!project.getProjectFiles().isEmpty()) {
                    projectFileService.upFile(project.getProjectFiles(),nowProject.getId());
                }
                return ResultData.WRITE(200,"添加成功",nowProject);
            } catch (Exception e) {
                throw new Exception("添加失败");
            }
        } else if (mothedType.equals("update")) {//修改
            try {
                Project nowProject = projectService.updateProject(project);
                projectFileService.deleteFileByProjectId(project.getId());
                projectFileService.upFile(project.getProjectFiles(),nowProject.getId());
                return ResultData.WRITE(200,"修改成功",nowProject);
            } catch (Exception e) {
                throw new Exception("修改失败");
            }
        } else if (mothedType.equals("delete")) {//删除
            try {
                projectService.deleteProject(project);
                return ResultData.WRITE(200,"删除成功");
            } catch (Exception e) {
                throw new Exception("删除失败");
            }
        }
       return ResultData.WRITE(404,"接口不存在");
    }

    /**
     * 功能描述：查看2D项目
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 16:40
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("getProject")
    public ResultData getProject(@RequestParam(defaultValue = "") String group_id,
                                    @RequestParam(defaultValue = "1") int pageNum,
                                    @RequestParam(defaultValue = "8") int pageSize,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "")String level,
                                    @RequestParam(defaultValue = "")String type,
                                    @RequestParam(defaultValue = "")String phase){
        if (group_id.isEmpty()){//查询所有项目组下2D项目
            try {
                Map<String, Object> map = projectService.getAllProject(pageNum, pageSize, search, level, type, phase);
                return ResultData.SUCCESS(map);
            }catch (Exception e){
                e.printStackTrace();
                return ResultData.FAILED();
            }
        }
        try {//查询指定项目组下2D项目
            Map<String, Object> map = projectService.getProject(group_id, pageNum, pageSize,  search,level,type,phase);
            return ResultData.SUCCESS(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED();
        }

    }
   /* *//**
     * 功能描述：查看所有2D项目
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 16:40
     * 参数：[ * @param null]
     * 返回值：
     *//*
    @GetMapping("getAllProject")
    public ResultData getProject(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "8") int pageSize,
                                 @RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "")String level,
                                 @RequestParam(defaultValue = "")String type,
                                 @RequestParam(defaultValue = "")String phase){
        try {
            Map<String, Object> map = projectService.getAllProject(pageNum, pageSize, search, level, type, phase);
            return ResultData.SUCCESS(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED();
        }

    }*/
    /**
     * 功能描述：查看单个模型
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/6 19:05
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("getOneProject/{id}")
    public ResultData getOneProject(@PathVariable("id") String id){
        try {
            Map<String, Object> map= projectService.getOneProject(id);
            return ResultData.SUCCESS(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.FAILED();
        }

    }
@Autowired
private JdbcTemplate jdbcTemplate;
    @RequestMapping("/goujian")
    public Map<String,Object> shengchan(){

        Integer in = jdbcTemplate.queryForObject("select count(*) from information_schema.columns where table_name = 'user' and column_name = 'name';", Integer.class);
        if(in!=0){
            jdbcTemplate.execute("alter table CloudPlatform.user ADD name varchar(20);");
        }

        String name=  "剪力墙-300mm 剪力墙-200mm";
        String type = "幕墙-基本墙";
        //String code = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 ";
        String address = "187.7,-366.5,-350 856.9,2938.6,-600 132.9,92.4,-600 -636.5,-3341.5,-600 102.9,-275.5,-600";//位置187.7,-366.5,-350
        String status = "已完成 未开始 进行中 ";
        String volume = "10.16 10.35 12.35 2.32 1.02 3.12 6.2 5.12 6.12 54.12 68.12 25.10 6.50 3.15";
        String material ="混泥土-C30 混泥土-C35 混泥土-C40 混泥土-C45 混泥土-C50";
        String manage = "李强 吴洁丰 雷银翔 胡琳 王加国 余昆蔓";
        String startTime = "2019/5/2 2019/5/3 2019/6/9 2019/5/15 2019/5/15 2019/5/2 2019/5/3 2019/5/14 2019/5/16";
        String endTime = "2019/6/2 2019/6/3 2019/7/9 2019/6/15 2019/6/15 2019/6/2 2019/6/3 2019/6/14 2019/6/16";

        String[] names = name.split(" ");
        String[] types = type.split(" ");
      //  String[] codes = code.split(" ");
        String[] addresses = address.split(" ");
        String[] statuses = status.split(" ");
        String[] volumes = volume.split(" ");
        String[] manages = manage.split(" ");
        String[] materials = material.split(" ");
        String[] startTimes = startTime.split(" ");
        String[] endTimes = endTime.split(" ");
        //随机生成
        List<Goujian>goujians = new ArrayList<>();

        for (int i = 0; i <200 ; i++) {
            int[] max={names.length,types.length,addresses.length,addresses.length,statuses.length,volumes.length,manages.length,materials.length,startTimes.length,endTimes.length};
            int min=0;
            Random random = new Random();
            int n = random.nextInt(max[0])%(max[0]-min+1) + min;
            // int c = random.nextInt(max[2])%(max[2]-min+1) + min;
            int a = random.nextInt(max[3])%(max[3]-min+1) + min;
            int s = random.nextInt(max[4])%(max[4]-min+1) + min;
            int v = random.nextInt(max[5])%(max[5]-min+1) + min;
            int m1 = random.nextInt(max[6])%(max[6]-min+1) + min;
            int m2 = random.nextInt(max[7])%(max[7]-min+1) + min;
            int stime = random.nextInt(max[8])%(max[8]-min+1) + min;
            int etime = random.nextInt(max[9])%(max[9]-min+1) + min;

            Goujian goujian = new Goujian();
            goujian.setName(names[n]);
            goujian.setType("幕墙-基本墙");
            goujian.setCode("000"+String.valueOf(i));
            goujian.setAddress(addresses[a]);
            goujian.setStatus(statuses[s]);
            goujian.setVolume(volumes[v]);
            goujian.setManage(manages[m1]);
            goujian.setMaterial(materials[m2]);
            goujian.setStartTime(startTimes[stime]);
            goujian.setEndTime(endTimes[etime]);

            goujians.add(goujian);
        }
        Map<String,Object>map = new HashMap<>();
        map.put("g",goujians);
           return map;
    }

}