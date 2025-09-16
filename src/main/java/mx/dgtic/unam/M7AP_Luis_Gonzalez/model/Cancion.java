package mx.dgtic.unam.M7AP_Luis_Gonzalez.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cancion")
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancion_id")
    private Integer id;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column(length = 200)
    private String audio;

    private Integer duracion;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "canciones")
    private List<Lista> listas = new ArrayList<>();

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cancion")
    private List<UsuarioCancion> descargas = new ArrayList<>();

}
