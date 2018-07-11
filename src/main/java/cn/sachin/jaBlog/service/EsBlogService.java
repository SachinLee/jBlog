package cn.sachin.jaBlog.service;

import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.pojo.EsBlog;
import org.springframework.data.domain.Pageable;

public interface EsBlogService {

    void removeEsBlog(String id);

    EsBlog updateEsBlog(EsBlog esBlog);

    EsBlog getEsBlogByBlogId(String blogId);

    PageList<EsBlog> getEsBlogPage(String keyword, PageConfig pageConfig);


}
