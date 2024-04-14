package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_session")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_session_type")
    private SessionType idSessionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "id_tmp_user")
    private Integer idTmpUser;

    @Column(name = "date")
    private Instant date;

    @OneToMany(mappedBy = "idSession")
    private Set<Gallery> galleries = new LinkedHashSet<>();

}