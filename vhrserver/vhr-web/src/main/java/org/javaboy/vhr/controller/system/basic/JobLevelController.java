package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.JobLevel;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JKXAING on 2020/8/16
 */

@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel JobLevel){
        if(jobLevelService.addJobLevel(JobLevel) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.ok("添加失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel JobLevel){
        if(jobLevelService.updateJobLevel(JobLevel) == 1){
            return RespBean.ok("更新成功");
        }
        return RespBean.ok("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if(jobLevelService.deleteJobLevelById(id) == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.ok("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(@RequestBody Integer[] ids){
        if(jobLevelService.deleteJobLevelsByIds(ids) == ids.length){
            return RespBean.ok("批量删除成功");
        }
        return RespBean.ok("批量删除失败");
    }
}
