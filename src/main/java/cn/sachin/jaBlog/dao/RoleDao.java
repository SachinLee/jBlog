package cn.sachin.jaBlog.dao;

import cn.sachin.jaBlog.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {
}
