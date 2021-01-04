package org.javaboy.vhr.controller.salary;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.model.Salary;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author JKXAING on 2020/8/29
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalSobCfgController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeByPageWithSalary(page, size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmpSalaryById(Integer eid, Integer sid) {
        Integer integer = employeeService.updateEmpSalaryById(eid, sid);
        //使用mysql  replace into
        //eid  需要有唯一索引，否则只会新增
        //若有更新是先删后增，受影响2；若无则是直接新增，受影响1
        if (integer == 1 || integer == 2) {
            return RespBean.ok("更新成功");
        }
        return RespBean.ok("更新失败");
    }
}
