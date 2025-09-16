package mx.dgtic.unam.M7AP_Luis_Gonzalez.repository;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.UsuarioCancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioCancionRepository extends JpaRepository<UsuarioCancion, Integer> {

    //Derivada 1
    List<UsuarioCancion> findByUsuario_Id(Integer id);

    //Derivada 2
    List<UsuarioCancion> findByCancion_TituloContainingIgnoreCase(String titulo);

    //Con relaciones
    @Query("""
        select uc from UsuarioCancion uc
        join uc.usuario u
        where u.nickname = :nickname
    """)
    List<UsuarioCancion> buscarPorNicknameUsuario(@Param("nickname") String nickname);

    // Nativa
    @Query(value = """
        select uc.*
        from usuario_cancion uc
        join cancion c on c.cancion_id = uc.cancion_id
        join artista a on a.artista_id = c.artista_id
        where a.nombre like %:nombre%
    """, nativeQuery = true)
    List<UsuarioCancion> buscarPorNombreArtista(@Param("nombre") String nombre);

    //Con relaciones
    @Query("""
    select uc.cancion
    from UsuarioCancion uc
    join uc.usuario u
    where u.nickname = :nickname
    """)
    List<Cancion> cancionesDescargadasPorUsuario(@Param("nickname") String nickname);

}
