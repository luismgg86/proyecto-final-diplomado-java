package mx.dgtic.unam.M7AP_Luis_Gonzalez.repository;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {

    // Derivada 1
    Optional<Artista> findByClave(String clave);
    // Derivada 2
    List<Artista> findByNombreContainingIgnoreCase(String nombre);
    // Derivada 3
    boolean existsByClave(String clave);

    // Nativa 1
    @Query(value = "select * from artista where clave = :clave", nativeQuery = true)
    Optional<Artista> buscarPorClaveNativa(@Param("clave") String clave);

    // Nativa 2
    @Query(value = "select * from artista where nombre like %:nombre%", nativeQuery = true)
    List<Artista> buscarPorNombreNativa(@Param("nombre") String nombre);

    // Nativa 3 con relaciones
    @Query(value = """
        select distinct a.*
        from artista a
        join cancion c on c.artista_id = a.artista_id
        where lower(c.titulo) like lower(concat('%', :q, '%'))
    """, nativeQuery = true)
    List<Artista> nativaPorTituloDeCancion(@Param("q") String q);

    //Con relaciones
    @Query("""
        select distinct a
        from Artista a
        left join fetch a.canciones c
        where a.clave = :clave
    """)
    Optional<Artista> detalleConCancionesPorClave(@Param("clave") String clave);
}
