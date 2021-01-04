package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author JKXAING on 2020/8/9
 */

@RestController
@RequestMapping("/hr")
public class HrInfoController {
    @Autowired
    HrService hrService;

    @GetMapping("/info")
    public Hr getCurrentHr(Authentication authentication){
        return ((Hr) authentication.getPrincipal());
    }

    @PutMapping("/info")
    public RespBean updateHr(@RequestBody Hr hr,Authentication authentication){
        if(hrService.updateHr(hr)==1){
            //更新authentication，其实就是更新session里的信息
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(),authentication.getAuthorities()));
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
