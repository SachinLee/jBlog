package cn.sachin.jaBlog.dao;

import cn.sachin.jaBlog.pojo.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogDao extends ElasticsearchRepository<EsBlog, String> {

    /**
     * 模糊查询(去重)
     * @param title
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrContentContainingOrTagsContaining(String title,String content, String tags, Pageable pageable);

    EsBlog findByBlogId(String blogId);
}
