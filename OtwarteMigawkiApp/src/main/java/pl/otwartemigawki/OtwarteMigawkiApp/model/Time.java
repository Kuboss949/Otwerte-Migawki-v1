package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "times")
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_times", nullable = false)
    private Integer id;


    @Column(name = "hour", nullable = false)
    private Integer hour;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_available_date", nullable = false)
    private AvailableDate idAvailableDate;

}