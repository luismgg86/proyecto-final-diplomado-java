package mx.dgtic.unam.M7AP_Luis_Gonzalez.repository;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListaRepository extends JpaRepository<Lista, Integer> {

    //Derivada 1
    List<Lista> findByUsuario_Nickname(String nickname);

    //Derivada 2
    List<Lista> findByUsuario_Id(Integer id);

    //Derivada 3
    List<Lista> findByNombreContainingIgnoreCase(String nombre);

    //Con relaciones 1
    @Query("select l from Lista l join l.usuario u where u.correo = :correo")
    List<Lista> buscarPorCorreoUsuario(@Param("correo") String correo);

    //Con relaciones 2
    @Query("select l from Lista l join l.canciones c where c.titulo like %:titulo%")
    List<Lista> buscarPorTituloDeCancion(@Param("titulo") String titulo);

    //Nativa
    @Query(value = """
        select l.*
        from lista l
        join usuario u on u.usuario_id = l.usuario_id
        where u.nickname = :nickname
    """, nativeQuery = true)
    List<Lista> nativaPorUsuarioNickname(@Param("nickname") String nickname);
}
