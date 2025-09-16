package mx.dgtic.unam.M7AP_Luis_Gonzalez;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.*;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioCancionJpaTest {

    @Autowired UsuarioRepository usuarioRepository;
    @Autowired CancionRepository cancionRepository;
    @Autowired UsuarioCancionRepository usuarioCancionRepository;
    @Autowired GeneroRepository generoRepository;
    @Autowired ArtistaRepository artistaRepository;

    private Usuario crearUsuario(String nickname) {
        Usuario u = new Usuario();
        u.setNombre("Luis");
        u.setApPaterno("Pérez");
        u.setApMaterno("López");
        u.setCorreo(nickname + "@example.com");
        u.setNickname(nickname);
        u.setContrasena("1234");
        u.setRol((short) 1);
        return usuarioRepository.save(u);
    }

    private Cancion crearCancion(String titulo, String claveGenero, String nombreGenero, String claveArtista, String nombreArtista) {
        Genero genero = new Genero();
        genero.setClave(claveGenero);
        genero.setNombre(nombreGenero);
        genero = generoRepository.save(genero);

        Artista artista = new Artista();
        artista.setClave(claveArtista);
        artista.setNombre(nombreArtista);
        artista = artistaRepository.save(artista);

        Cancion c = new Cancion();
        c.setTitulo(titulo);
        c.setAudio("cancion.mp3");
        c.setDuracion(180);
        c.setFechaAlta(LocalDate.now());
        c.setGenero(genero);
        c.setArtista(artista);
        return cancionRepository.save(c);
    }

    private UsuarioCancion crearDescarga(String nickname, String titulo, String claveGenero, String nombreGenero, String claveArtista, String nombreArtista) {
        Usuario usuario = crearUsuario(nickname);
        Cancion cancion = crearCancion(titulo, claveGenero, nombreGenero, claveArtista, nombreArtista);

        UsuarioCancion descarga = new UsuarioCancion();
        descarga.setUsuario(usuario);
        descarga.setCancion(cancion);
        descarga.setFechaDescarga(LocalDate.now());

        return usuarioCancionRepository.save(descarga);
    }

    @Test
    void testFindByUsuarioId() {
        UsuarioCancion uc = crearDescarga("lmanggz", "Karma Police", "ROCK", "Rock", "RH001", "Radiohead");
        List<UsuarioCancion> resultado = usuarioCancionRepository.findByUsuario_Id(uc.getUsuario().getId());

        assertEquals(1, resultado.size());
        assertEquals("Karma Police", resultado.get(0).getCancion().getTitulo());
    }

    @Test
    void testFindByCancionTituloContainingIgnoreCase() {
        crearDescarga("nick1", "No Surprises", "ALT", "Alternativo", "RH002", "Radiohead");
        List<UsuarioCancion> resultado = usuarioCancionRepository.findByCancion_TituloContainingIgnoreCase("surprises");

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getCancion().getTitulo().contains("Surprises"));
    }

    @Test
    void testBuscarPorNicknameUsuario() {
        crearDescarga("beatlefan", "Yesterday", "CLAS", "Clásico", "BEAT1", "The Beatles");
        List<UsuarioCancion> resultado = usuarioCancionRepository.buscarPorNicknameUsuario("beatlefan");

        assertEquals(1, resultado.size());
        assertEquals("Yesterday", resultado.get(0).getCancion().getTitulo());
    }

    @Test
    void testBuscarPorNombreArtista() {
        crearDescarga("fan123", "In Bloom", "GRUN", "Grunge", "NIRV1", "Nirvana");
        List<UsuarioCancion> resultado = usuarioCancionRepository.buscarPorNombreArtista("nirvana");

        assertEquals(1, resultado.size());
        assertEquals("In Bloom", resultado.get(0).getCancion().getTitulo());
    }

    @Test
    void testCancionesDescargadasPorUsuario() {
        crearDescarga("freak", "Freak on a Leash", "NU", "Nu Metal", "KORN1", "Korn");
        List<Cancion> canciones = usuarioCancionRepository.cancionesDescargadasPorUsuario("freak");

        assertEquals(1, canciones.size());
        assertEquals("Freak on a Leash", canciones.get(0).getTitulo());
    }
}
