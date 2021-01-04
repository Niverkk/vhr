package org.javaboy.vhr.controller.employee;

import org.javaboy.vhr.model.*;
import org.javaboy.vhr.service.*;
import org.javaboy.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author JKXAING on 2020/8/21
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    NationService nationService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10")Integer size,
                                          Employee employee,
                                          Date[] beginDateScope){
        //前端对象中没有的值为null,id类型为integer，无法转换，引发400错误
        System.out.println(employee);
        System.out.println(beginDateScope);
        return employeeService.getEmployeeByPage(page,size,employee,beginDateScope);
    }


    @PostMapping("/")
    public RespBean addEmployee(@RequestBody Employee employee){
        if(employeeService.addEmployee(employee) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updateEmployee(@RequestBody Employee employee){
        if(employeeService.updateEmployee(employee) == 1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean delEmployee(@PathVariable Integer id){
        if(employeeService.delEmployee(id) == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/jobLevels")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDeprtments();
    }

    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkID(){
        return RespBean.build().
                setStatus(200).
                setObj(String.format("%08d",employeeService.getMaxWorkID()+1));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportEmps(){
        return POIUtils.employee2Excel(((List<Employee>) employeeService.getEmployeeByPage(null, null,null,null).getData()));
    }


    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Employee> list = POIUtils.excel2Employee(file, nationService.getAllNations(), politicsstatusService.getAllPoliticsstatus(), departmentService.getAllDepartmentsWithOutChildren(), positionService.getAllPositions(), jobLevelService.getAllJobLevels());
        if (employeeService.addEmps(list) == list.size()) {
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }

}
