package cn.sachin.jaBlog.service.impl;

import cn.sachin.jaBlog.common.BaseServiceImpl;
import cn.sachin.jaBlog.dao.RoleDao;
import cn.sachin.jaBlog.pojo.Role;
import cn.sachin.jaBlog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
