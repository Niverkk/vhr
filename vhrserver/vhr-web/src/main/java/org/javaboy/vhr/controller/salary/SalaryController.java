package org.javaboy.vhr.controller.salary;

import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.Salary;
import org.javaboy.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JKXAING on 2020/8/29
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if(salaryService.addSalary(salary) ==1 ){
            return RespBean.ok("添加成功");
        }
        return RespBean.ok("添加失败");
    }

    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if(salaryService.updateSalary(salary) ==1 ){
            return RespBean.ok("更新成功");
        }
        return RespBean.ok("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id){
        if(salaryService.deleteSalary(id) ==1 ){
            return RespBean.ok("删除成功");
        }
        return RespBean.ok("删除失败");
    }

}
