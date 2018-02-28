package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.ServerResult;
import cn.sachin.jaBlog.pojo.User;
import cn.sachin.jaBlog.service.UserService;
import cn.sachin.jaBlog.util.ConstraintViolationExceptionHandler;
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

    //通过配置文件，配置初始密码
    @Value("${init-password}")
    private String initPassword;

    /**
     * 查询用户列表信息
     * @param user 用户查询条件
     * @param pageConfig 分页信息
     * @return 返回用户列表信息
     */
    @GetMapping(value = "/manager")
    public ModelAndView userManager(User user, PageConfig pageConfig) {
        ModelAndView model = new ModelAndView("admin/users/list");

        Page<User> page = userService.getUserPage(user, pageConfig);

        model.addObject("page", page);

        return model;
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return 返回添加结果，成功/失败
     */
    @PostMapping(value = "/add")
    public ServerResult addUser(User user) {
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





    /**
     * 处理用户信息
     * @param user 用户信息
     * @return 返回处理后用户信息
     */
    private User userHandler(User user) {
        if (StringUtils.isNoneBlank(user.getId())) {
            //更新用户
            //加上原来的密码
            user.setPassword(userService.get(user.getId()).getPassword());

        } else {
            //新增用户
            //为初始密码加密
            user.setEncodePassword(initPassword);
            user.setCreateTime(LocalDateTime.now()); //创建时间为当前日期
        }

        return user;
    }
}
