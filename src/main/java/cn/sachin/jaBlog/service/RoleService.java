package cn.sachin.jaBlog.service;

import cn.sachin.jaBlog.common.BaseService;
import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.pojo.Role;

public interface RoleService extends BaseService<Role, String> {

    PageList<Role> getRolePage(Role role, PageConfig pageConfig);

}
