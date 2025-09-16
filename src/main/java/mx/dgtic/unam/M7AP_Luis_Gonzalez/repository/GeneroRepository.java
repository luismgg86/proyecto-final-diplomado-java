package mx.dgtic.unam.M7AP_Luis_Gonzalez.repository;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    //Derivada 1
    Optional<Genero> findByClave(String clave);
    //Derivada 2
    List<Genero> findByClaveIgnoreCase(String clave);
    //Derivada 3
    List<Genero> findByNombreContainingIgnoreCase(String nombre);

    //Nativa 1
    @Query(value = "SELECT * FROM genero WHERE clave = :clave", nativeQuery = true)
    Optional<Genero> buscarPorClave(@Param("clave") String clave);

    //Nativa 2
    @Query(value = "SELECT * FROM genero WHERE nombre LIKE %:nombre%", nativeQuery = true)
    List<Genero> buscarPorNombre(@Param("nombre") String nombre);

    //Con relaciones
    @Query("""
        select distinct g
        from Genero g
        left join fetch g.canciones
        where g.clave = :clave
    """)
    Optional<Genero> detalleConCancionesPorClave(@Param("clave") String clave);
}

