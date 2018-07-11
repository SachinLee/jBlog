package cn.sachin.jaBlog.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "blog_user")
@Data
public class User implements Serializable {

    public interface UserSimpleInfo{};
    public interface UserDetailsInfo extends UserSimpleInfo{};


    @JsonView(UserSimpleInfo.class)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", length = 50)
    @ApiModelProperty(value = "用户Id")
    private String id;

    @JsonView(UserSimpleInfo.class)
    @NotEmpty(message = "账号不能为空")
    @Size(min = 3, max = 20)
    @Column(name = "login_name", length = 20)
    @ApiModelProperty(value = "用户账号")
    private String loginName;

    @JsonView(UserDetailsInfo.class)
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 200)
    @Column(name = "password", length = 200)
    @ApiModelProperty(value = "用户密码")
    private String password;

    @JsonView(UserSimpleInfo.class)
    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50)
    @Email(message = "邮箱格式不正确")
    @Column(name = "email", length = 50)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @JsonView(UserSimpleInfo.class)
    @Column(name = "avatar", length = 200)
    private String avatar; //头像地址

    @JsonView(UserSimpleInfo.class)
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createTime; //创建时间

    @JsonView(UserSimpleInfo.class)
    @Column(name = "last_login")
    private Date lastLogin; //上次登录时间

    @JsonView(UserSimpleInfo.class)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "role_id") })
    @ApiModelProperty(value = "用户角色")
    private Set<Role> roles;

    //密码加密
    public void setEncodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        this.password = encodePassword;
    }
}
