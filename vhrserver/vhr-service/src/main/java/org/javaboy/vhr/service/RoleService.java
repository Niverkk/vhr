package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.JobLevelMapper;
import org.javaboy.vhr.mapper.RoleMapper;
import org.javaboy.vhr.model.JobLevel;
import org.javaboy.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author JKXAING on 2020/8/13
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        //spring security 角色前缀要有ROLE_
        if(!role.getName().startsWith("ROLE_")){
           role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insertSelective(role);
    }

    public int deleteRole(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
