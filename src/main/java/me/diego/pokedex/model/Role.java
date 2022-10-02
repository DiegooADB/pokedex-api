package me.diego.pokedex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.diego.pokedex.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "TB_ROLE")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }

    public Role(long roleId, RoleName roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Role() {
    }
}
