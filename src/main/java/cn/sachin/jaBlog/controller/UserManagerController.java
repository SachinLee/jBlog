package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.common.ServerResult;
import cn.sachin.jaBlog.pojo.Role;
import cn.sachin.jaBlog.pojo.User;
import cn.sachin.jaBlog.service.RoleService;
import cn.sachin.jaBlog.service.UserService;
import cn.sachin.jaBlog.util.ConstraintViolationExceptionHandler;
import com.fasterxml.jackson.annotation.JsonView;
import com.mysql.fabric.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户管理 controller
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")  //管理员角色才可以操作
public class UserManagerController {

    private static final Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //通过配置文件，配置初始密码
    @Value("${init-password}")
    private String initPassword;

    /**
     * 查询用户列表信息
     * @param user 用户查询条件
     * @param pageConfig 分页信息
     * @return 返回用户列表信息
     */
    @JsonView(User.UserSimpleInfo.class)
    @GetMapping(value = "/manager")
    public ModelAndView userManager(User user, PageConfig pageConfig) {
        ModelAndView model = new ModelAndView("admin/users/list");

        PageList<User> page = userService.getUserPage(user, pageConfig);

        List<Role> roles = roleService.getAll();

        model.addObject("page", page);
        model.addObject("user", user);
        model.addObject("roles", roles);

        return model;
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return 返回添加结果，成功/失败
     */
    @PostMapping(value = "/add")
    public ServerResult addUser(User user) {

        User loginUser = userService.getUserByLogin(user.getLoginName());
        if (loginUser != null) {
            return ServerResult.createResultByErrorMsg("用户名已经存在");
        }

        User emailUser = userService.getUserByEmail(user.getEmail());
        if (emailUser != null) {
            return ServerResult.createResultByErrorMsg("邮箱已经存在");
        }

        User userChanged = this.userHandler(user);

        try {
            userService.save(userChanged);
            return ServerResult.createResultBySuccessMsg("添加成功");
        } catch (ConstraintViolationException e1) {
            //返回校验的异常信息
            logger.error(ConstraintViolationExceptionHandler.getMessage(e1));
            return ServerResult.createResultByErrorMsg(ConstraintViolationExceptionHandler.getMessage(e1));
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ServerResult.createResultByErrorMsg("添加失败");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ServerResult deleteUser(@PathVariable("id") String id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ServerResult.createResultByErrorMsg("删除失败");
        }
        return ServerResult.createResultBySuccessMsg("删除成功");
    }

    @GetMapping(value = "/validateLogin/{loginName}")
    public ServerResult validateLoginName(@PathVariable String loginName) {

        try {
            User user = userService.getUserByLogin(loginName);
            if (user != null) {
                return ServerResult.createResultByErrorMsg("用户名已经存在");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ServerResult.createResultByError();
        }

        return ServerResult.createResultBySuccess();
    }

    @GetMapping(value = "/validateEmail/{email}")
    public ServerResult validateEmail(@PathVariable String email) {
        User queryUser = new User();
        queryUser.setEmail(email);

        ServerResult result = ServerResult.createResultBySuccess();
        try {
            User user = userService.getUserByParams(queryUser);
            if (user != null) {
                result = ServerResult.createResultByErrorMsg("邮箱已经存在");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            result = ServerResult.createResultByError();
        }

        return result;
    }



    /**
     * 处理用户信息
     * @param user 用户信息
     * @return 返回处理后用户信息
     */
    private User userHandler(User user) {
        if (StringUtils.isNoneBlank(user.getId())) {
            //更新用户
            //加上原来的密码
            User oldUser = userService.get(user.getId());
            //user.setPassword(oldUser.getPassword());
            oldUser.setLoginName(user.getLoginName());
            oldUser.setEmail(user.getEmail());
            oldUser.setRoles(user.getRoles());
        } else {
            //新增用户
            //为初始密码加密
            user.setPassword(initPassword);
            user.setEncodePassword(user.getPassword());
        }

        return user;
    }
}
