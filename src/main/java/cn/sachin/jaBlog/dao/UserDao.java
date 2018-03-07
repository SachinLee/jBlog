package cn.sachin.jaBlog.dao;

import cn.sachin.jaBlog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, String> {

    User findByLoginName(String loginName);

    User findByEmail(String email);
}
