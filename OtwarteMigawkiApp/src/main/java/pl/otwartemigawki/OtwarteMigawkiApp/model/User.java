package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "salt", length = 100)
    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role")
    private Role idRole;

    @Column(name = "is_tmp", nullable = false)
    private Boolean isTmp = false;

    @OneToMany(mappedBy = "idUser")
    private Set<UserDetail> userDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUser")
    private Set<UserSession> userSessions = new LinkedHashSet<>();

}