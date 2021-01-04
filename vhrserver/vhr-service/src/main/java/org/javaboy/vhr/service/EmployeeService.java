package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.VariableElement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author JKXAING on 2020/8/21
 */
@Service
public class EmployeeService {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    EmployeeMapper employeeMapper;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat numberFormat = new DecimalFormat("##.00");

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        Long total = employeeMapper.getTotal(employee, beginDateScope);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setTotal(total);
        respPageBean.setData(data);
        return respPageBean;
    }

    public Integer addEmployee(Employee employee) {
        double months = (Double.parseDouble(yearFormat.format(employee.getEndContract())) - Double.parseDouble(yearFormat.format(employee.getBeginContract()))) * 12
                + Double.parseDouble(monthFormat.format(employee.getEndContract())) - Double.parseDouble(monthFormat.format(employee.getBeginContract()));
        employee.setContractTerm(Double.parseDouble(numberFormat.format(months / 12)));

        //主键回填
        int result = employeeMapper.insertSelective(employee);
        if (result == 1) {
            Employee emp = employeeMapper.getEmployeeById(employee.getId());
            //direct模式
            rabbitTemplate.convertAndSend("niverkk.mail.welcome", emp);

        }
        return result;
    }

    public Integer getMaxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer delEmployee(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmployee(Employee employee) {
        double months = (Double.parseDouble(yearFormat.format(employee.getEndContract())) - Double.parseDouble(yearFormat.format(employee.getBeginContract()))) * 12
                + Double.parseDouble(monthFormat.format(employee.getEndContract())) - Double.parseDouble(monthFormat.format(employee.getBeginContract()));
        employee.setContractTerm(Double.parseDouble(numberFormat.format(months / 12)));
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public int test(String name) {
        return employeeMapper.test(name);

    }

    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null, null));
        return respPageBean;
    }

    public Integer updateEmpSalaryById(Integer eid,Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid, sid);
    }
}
