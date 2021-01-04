package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JKXAING on 2020/8/17
 */

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDeprtments(){
        return departmentService.getAllDeprtments();
    }

    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department dep){
        departmentService.addDepartment(dep);
        if(dep.getResult() == 1){
            return RespBean.ok("添加成功！",dep);

        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepartment(@PathVariable Integer id){
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepartment(dep);
        if(dep.getResult() == -2){
            return RespBean.error("该部门存在子部门，删除失败！");
        }else if(dep.getResult() == -1){
            return RespBean.error("该部门存在员工，删除失败！");
        }else if(dep.getResult() == 1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
