package eci.cvds.armario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conjuntos")
public class Conjuntos {
    @Id
    @Column(name = "conjunto_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID conjuntoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prenda1_id", nullable = true)
    private Prenda prenda1;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prenda2_id", nullable = true)
    private Prenda prenda2;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prenda3_id", nullable = true)
    private Prenda prenda3;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prenda4_id", nullable = true)
    private Prenda prenda4;

}