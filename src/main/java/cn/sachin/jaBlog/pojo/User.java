package cn.sachin.jaBlog.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "blog_user")
@Data
public class User implements Serializable {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", length = 50)
    private String id;

    @NotEmpty(message = "账号不能为空")
    @Size(min = 3, max = 20)
    @Column(name = "login_name", length = 20)
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 200)
    @Column(name = "password", length = 200)
    private String password;

    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50)
    @Email(message = "邮箱格式不正确")
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "avatar", length = 200)
    private String avatar; //头像地址

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    //密码加密
    public void setEncodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        this.password = encodePassword;
    }
}
