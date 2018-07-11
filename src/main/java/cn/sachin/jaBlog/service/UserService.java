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

    /**
     * 通过用户名查询用户信息
     * @param loginName 用户登录名
     * @return 返回用户信息
     */
    User getUserByLogin(String loginName);

    /**
     * 通过用户的某些信息查询信息，查询用户
     * @param user 用户查询信息
     * @return 返回查询用户的详细信息
     */
    User getUserByParams(User user);

    /**
     * 通过邮箱查询用户信息
     * @param email 邮箱
     * @return 返回用户信息
     */
    User getUserByEmail(String email);

    /**
     * 校验用户名是否已经存在
     * @param loginName 用户名
     * @param id 用户id
     * @return 返回用户信息
     */
    User validateLogin(String loginName, String id);


    User validateEmail(String email, String id);
}
