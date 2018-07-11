package cn.sachin.jaBlog.service.impl;

import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.dao.EsBlogDao;
import cn.sachin.jaBlog.pojo.EsBlog;
import cn.sachin.jaBlog.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EsBlogServiceImpl implements EsBlogService {

    @Autowired
    private EsBlogDao esBlogDao;

    @Override
    public void removeEsBlog(String id) {
        esBlogDao.delete(id);
    }

    @Override
    public EsBlog updateEsBlog(EsBlog esBlog) {
        return esBlogDao.save(esBlog);
    }

    @Override
    public EsBlog getEsBlogByBlogId(String blogId) {
        return esBlogDao.findByBlogId(blogId);
    }

    @Override
    public PageList<EsBlog> getEsBlogPage(String keyword, PageConfig pageConfig) {
        Page<EsBlog> page = esBlogDao.findDistinctEsBlogByTitleContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword,
                new PageRequest(pageConfig.getPageNum() - 1, pageConfig.getPageSize()));

        PageList<EsBlog> pageList = new PageList<>(page.getContent());
        pageConfig.setPageCount(page.getTotalPages());
        pageConfig.setRowCount(page.getTotalElements());
        pageList.setPageConfig(pageConfig);

        return pageList;
    }
}
