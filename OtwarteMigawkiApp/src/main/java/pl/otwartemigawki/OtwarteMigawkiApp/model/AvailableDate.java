package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "available_date")
public class AvailableDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_available_date", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_session_type")
    private SessionType idSessionType;


    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "idAvailableDate")
    private Set<Time> times = new LinkedHashSet<>();

}