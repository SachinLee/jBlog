package cn.sachin.jaBlog.service;

import cn.sachin.jaBlog.common.BaseService;
import cn.sachin.jaBlog.common.PageConfig;
import cn.sachin.jaBlog.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends BaseService<User, String> {
    /**
     * 查询人员分页列表信息
     * @param user 用户查询条件
     * @param pageConfig 分页信息
     * @return 返回人员分页列表
     */
    Page<User> getUserPage(User user, PageConfig pageConfig);

}
