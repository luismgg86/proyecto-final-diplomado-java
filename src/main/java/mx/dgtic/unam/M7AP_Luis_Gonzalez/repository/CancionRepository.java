package mx.dgtic.unam.M7AP_Luis_Gonzalez.repository;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CancionRepository extends JpaRepository<Cancion, Integer> {
    //Derivada 1
    List<Cancion> findByArtista_NombreContainingIgnoreCase(String nombreArtista);
    //Derivada 2
    List<Cancion> findByGenero_Clave(String claveGenero);
    //Derivada 3
    List<Cancion> findByListas_Id(Integer listaId);

    //Paginado
    Page<Cancion> findByTituloContainingIgnoreCase(String q, Pageable pageable);

    //Nativa1 con relaciones
    @Query(value = """
      select c.* from cancion c
      join artista a on a.artista_id = c.artista_id
      where a.nombre like %:nombre%
    """, nativeQuery = true)
    List<Cancion> buscarPorNombreArtista(@Param("nombre") String nombre);

    //Con relaciones
    @Query("""
        select c
        from Cancion c
        join c.genero g
        where g.clave = :clave
    """)
    List<Cancion> buscarPorClaveGenero(@Param("clave") String clave);

    //Nativa con relaciones
    @Query(value = """
        select c.*
        from cancion c
        join artista a on a.artista_id = c.artista_id
        join genero g on g.genero_id = c.genero_id
        where a.clave = :claveArtista
        and g.clave = :claveGenero
    """, nativeQuery = true)
    List<Cancion> nativaPorArtistaYGenero(@Param("claveArtista") String claveArtista,
                                          @Param("claveGenero") String claveGenero);

}

