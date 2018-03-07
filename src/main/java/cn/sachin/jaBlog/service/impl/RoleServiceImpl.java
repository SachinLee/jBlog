package cn.sachin.jaBlog.service.impl;

import cn.sachin.jaBlog.common.BaseServiceImpl;
import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.dao.RoleDao;
import cn.sachin.jaBlog.pojo.Role;
import cn.sachin.jaBlog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public JpaRepository<Role, String> getDao() {
        return roleDao;
    }

    @Override
    public PageList<Role> getRolePage(Role role, PageConfig pageConfig) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE) //忽略null值
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("roleName", ExampleMatcher.GenericPropertyMatchers.contains());

        Page<Role> roles = roleDao.findAll(Example.of(role, matcher),
                new PageRequest(pageConfig.getPageNum() - 1, pageConfig.getPageSize()));

        PageList<Role> pageList = new PageList<>(roles.getContent());

        pageConfig.setRowCount(roles.getTotalElements());
        pageConfig.setPageCount(roles.getTotalPages());

        pageList.setPageConfig(pageConfig);

        return pageList;
    }
}
