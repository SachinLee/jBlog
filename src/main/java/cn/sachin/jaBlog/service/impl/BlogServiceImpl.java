package cn.sachin.jaBlog.service.impl;

import cn.sachin.jaBlog.common.BaseServiceImpl;
import cn.sachin.jaBlog.dao.BlogDao;
import cn.sachin.jaBlog.pojo.Blog;
import cn.sachin.jaBlog.pojo.EsBlog;
import cn.sachin.jaBlog.service.BlogService;
import cn.sachin.jaBlog.service.EsBlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogServiceImpl extends BaseServiceImpl<Blog, String> implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private EsBlogService esBlogService;

    @Override
    public JpaRepository<Blog, String> getDao() {
        return blogDao;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        Blog returnBlog = blogDao.save(blog);

        EsBlog esBlog = null;
        if (StringUtils.isBlank(blog.getId())) {
            //新增
            esBlog = new EsBlog(blog);
        } else {
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(blog);
        }
        esBlogService.updateEsBlog(esBlog);

        return returnBlog;
    }
}
