package cn.sachin.jaBlog.service;

import cn.sachin.jaBlog.common.BaseService;
import cn.sachin.jaBlog.pojo.Blog;

public interface BlogService extends BaseService<Blog, String> {

    Blog saveBlog(Blog blog);

}
