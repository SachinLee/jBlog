package cn.sachin.jaBlog.common;

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
public class BaseServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void get() throws Exception {
        User user = userService.get("111111");
        System.out.print(user);
    }

}