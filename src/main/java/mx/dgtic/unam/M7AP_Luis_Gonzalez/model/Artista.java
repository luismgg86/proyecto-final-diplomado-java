package mx.dgtic.unam.M7AP_Luis_Gonzalez.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artista_id")
    private Integer artistaId;

    @Column(length = 5, nullable = false, unique = true)
    private String clave;

    @Column(length = 100, nullable = false)
    private String nombre;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cancion> canciones;

    public void agregarCancion(Cancion cancion) {
        if (this.canciones == null) {
            this.canciones = new java.util.ArrayList<>();
        }
        canciones.add(cancion);
        cancion.setArtista(this);
    }

}
