package cn.sachin.jaBlog.service;

import cn.sachin.jaBlog.common.BaseService;
import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.PageList;
import cn.sachin.jaBlog.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService ,BaseService<User, String> {
    /**
     * 查询人员分页列表信息
     * @param user 用户查询条件
     * @param pageConfig 分页信息
     * @return 返回人员分页列表
     */
    PageList<User> getUserPage(User user, PageConfig pageConfig);


    User getUserByLogin(String loginName);

    User getUserByParams(User user);

    User getUserByEmail(String email);
}
