package cn.sachin.jaBlog.dao;

import cn.sachin.jaBlog.pojo.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogDao extends JpaRepository<Blog, String> {
}
