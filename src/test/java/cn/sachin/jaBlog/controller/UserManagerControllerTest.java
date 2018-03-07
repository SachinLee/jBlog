package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.pojo.User;
import cn.sachin.jaBlog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManagerControllerTest {

    @Autowired
    private UserManagerController userManagerController;

    @Test
    public void add() {
        User user = new User();
        user.setLoginName("test");
        user.setEmail("test@test.com");

        userManagerController.addUser(user);
    }

}