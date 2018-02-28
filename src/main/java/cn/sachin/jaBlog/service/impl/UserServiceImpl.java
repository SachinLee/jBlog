package cn.sachin.jaBlog.service.impl;

import cn.sachin.jaBlog.common.BaseServiceImpl;
import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.common.SecurityUser;
import cn.sachin.jaBlog.dao.UserDao;
import cn.sachin.jaBlog.pojo.Role;
import cn.sachin.jaBlog.pojo.User;
import cn.sachin.jaBlog.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public JpaRepository<User, String> getDao() {
        return userDao;
    }

    /**
     * 用户登录认证
     * @param username 用户登录名称
     * @return 返回用户信息 UserDetails 的实现类
     * @throws UsernameNotFoundException 抛出异常，用户未找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByLoginName(username);

        List<GrantedAuthority> authorities = Lists.newArrayList();
        for (Role role: user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        SecurityUser securityUser = new SecurityUser(user.getId(), username, user.getPassword(), authorities);

        securityUser.setAvatar(user.getAvatar());
        securityUser.setEmail(user.getEmail());

        return securityUser;
    }

    @Override
    public Page<User> getUserPage(User user, PageConfig pageConfig) {
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE) //忽略null值
                .withMatcher("loginName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());

        Page<User> page = userDao.findAll(Example.of(user, matcher),
                new PageRequest(pageConfig.getPageNum() - 1, pageConfig.getPageSize()));

        return page;
    }
}
