package cn.sachin.jaBlog.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "blog_role")
@Data
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @NotEmpty(message = "角色名称不能为空")
    @Size(min = 2, max = 20)
    @Column(name = "role_name", length = 20)
    private String roleName;


    @Override
    public String getAuthority() {
        return id;
    }
}
