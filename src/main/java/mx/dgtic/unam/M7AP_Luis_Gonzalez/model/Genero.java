package mx.dgtic.unam.M7AP_Luis_Gonzalez.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genero_id")
    private Integer id;

    @Column(length = 5)
    private String clave;

    @Column(length = 100)
    private String nombre;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "genero")
    private List<Cancion> canciones;

    public void agregarCancion(Cancion cancion) {
        if (this.canciones == null) {
            this.canciones = new java.util.ArrayList<>();
        }
        canciones.add(cancion);
        cancion.setGenero(this);
    }
}
