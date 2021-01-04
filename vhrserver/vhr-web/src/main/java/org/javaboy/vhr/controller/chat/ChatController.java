package org.javaboy.vhr.controller.chat;

import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.service.HrService;
import org.javaboy.vhr.utils.HrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JKXAING on 2020/8/29
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    HrService hrService;

    @GetMapping("/hrs")
    public List<Hr> getAllHrsWithoutCurrentHr(){
        return hrService.getAllHrsWithoutCurrentHr(HrUtil.getCurrentHr().getId());
    }
}
