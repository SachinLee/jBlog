package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.common.ServerResult;
import cn.sachin.jaBlog.pojo.Role;
import cn.sachin.jaBlog.service.RoleService;
import com.mysql.fabric.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/roles")
@Slf4j
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class RoleMangerController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/manager")
    public ModelAndView manager(Role role, PageConfig pageConfig) {
        ModelAndView modelAndView = new ModelAndView("admin/roles/list");

        PageList<Role> page = roleService.getRolePage(role, pageConfig);

        modelAndView.addObject("page", page);

        return modelAndView;
    }

    @PostMapping(value = "/create")
    public ServerResult add(Role role) {
        try {
            roleService.save(role);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ServerResult.createResultByErrorMsg("保存角色失败");
        }
        return ServerResult.createResultBySuccess();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ServerResult delete(@PathVariable String id) {
        try {
            roleService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ServerResult.createResultByErrorMsg("角色删除失败");
        }
        return ServerResult.createResultBySuccess();
    }

    @GetMapping(value = "/validateId")
    public ServerResult validateId(String id) {
        Role role = roleService.get(id);
        if (role != null) {
            return ServerResult.createResultByErrorMsg("当前编码已经存在");
        } else {
            return ServerResult.createResultBySuccess();
        }
    }
}
