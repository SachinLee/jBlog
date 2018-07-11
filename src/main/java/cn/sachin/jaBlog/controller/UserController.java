package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.common.ServerResult;
import cn.sachin.jaBlog.pojo.User;
import cn.sachin.jaBlog.service.FileService;
import cn.sachin.jaBlog.service.UserService;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/u")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/{loginName}/profile")
    @PreAuthorize("authentication.name.equals(#loginName)")
    public ModelAndView getProfile(@PathVariable(value = "loginName") String loginName) {
        ModelAndView modelAndView = new ModelAndView("users/profile");

        User user = userService.getUserByLogin(loginName);

        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PostMapping(value = "/{loginName}/upload")
    @PreAuthorize("authentication.name.equals(#loginName)")
    public ServerResult uploadAvatar(@PathVariable("loginName") String loginName,MultipartFile file) {

        try {
            String avatar = fileService.uploadFile(file);
            if (StringUtils.isNoneBlank(avatar)) {
                return ServerResult.createResultBySuccess(avatar);
            }
        } catch (Exception e) {
            log.error("头像上传失败，失败原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return ServerResult.createResultByError();
    }

    @PostMapping(value = "/{loginName}/save")
    @PreAuthorize("authentication.name.equals(#loginName)")
    public ServerResult save(@PathVariable(value = "loginName") String loginName, User user) {
        User originalUser = userService.get(user.getId());

        originalUser.setAvatar(user.getAvatar());
        originalUser.setLoginName(user.getLoginName());
        originalUser.setEmail(user.getEmail());

        userService.save(originalUser);

        return ServerResult.createResultBySuccess();
    }


}
