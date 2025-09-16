package mx.dgtic.unam.M7AP_Luis_Gonzalez;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.*;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ListaJpaTest {

    @Autowired
    ListaRepository listaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CancionRepository cancionRepository;

    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    GeneroRepository generoRepository;

    private Lista crearLista(String nombre, String nickname) {
        Usuario u = new Usuario();
        u.setNombre("Luis");
        u.setApPaterno("González");
        u.setApMaterno("Gutiérrez");
        u.setCorreo("lmgg@example.com");
        u.setNickname(nickname);
        u.setContrasena("123");
        u.setRol((short) 1);
        usuarioRepository.save(u);

        Lista l = new Lista();
        l.setNombre(nombre);
        l.setFechaCreacion(LocalDate.now());
        l.setUsuario(u);
        return listaRepository.save(l);
    }

    private Cancion crearCancion(String titulo) {
        Artista a = new Artista(null, "R01", "Radiohead", null);
        artistaRepository.save(a);

        Genero g = new Genero(null, "ALT", "Alternativo", null);
        generoRepository.save(g);

        Cancion c = new Cancion();
        c.setTitulo(titulo);
        c.setAudio("archivo.mp3");
        c.setDuracion(200);
        c.setFechaAlta(LocalDate.now());
        c.setArtista(a);
        c.setGenero(g);

        g.agregarCancion(c);
        a.agregarCancion(c);

        return cancionRepository.save(c);
    }

    @Test
    void testFindByUsuario_Nickname() {
        crearLista("Mis canciones", "lmanggz");

        List<Lista> listas = listaRepository.findByUsuario_Nickname("lmanggz");

        assertEquals(1, listas.size());
        assertEquals("Mis canciones", listas.get(0).getNombre());
    }

    @Test
    void testFindByUsuario_Id() {
        Lista l = crearLista("Favoritas", "nicku");
        Integer id = l.getUsuario().getId();

        List<Lista> listas = listaRepository.findByUsuario_Id(id);

        assertEquals(1, listas.size());
        assertEquals("Favoritas", listas.get(0).getNombre());
    }

    @Test
    void testFindByNombreContainingIgnoreCase() {
        crearLista("Canciones Tristes", "triste");

        List<Lista> listas = listaRepository.findByNombreContainingIgnoreCase("tristes");

        assertEquals(1, listas.size());
        assertEquals("Canciones Tristes", listas.get(0).getNombre());
    }

    @Test
    void testBuscarPorCorreoUsuario() {
        Lista l = crearLista("Éxitos", "correoNick");

        List<Lista> listas = listaRepository.buscarPorCorreoUsuario("lmgg@example.com");

        assertEquals(1, listas.size());
        assertEquals("Éxitos", listas.get(0).getNombre());
    }

    @Test
    void testBuscarPorTituloDeCancion() {
        Lista l = crearLista("Playlist de rock", "usuario");
        Cancion c = crearCancion("Paranoid Android");

        l.setCanciones(new ArrayList<>(List.of(c)));
        listaRepository.save(l);

        List<Lista> listas = listaRepository.buscarPorTituloDeCancion("Android");

        assertEquals(1, listas.size());
        assertEquals("Playlist de rock", listas.get(0).getNombre());
    }

    @Test
    void testNativaPorUsuarioNickname() {
        crearLista("Mix Alternativo", "altnick");

        List<Lista> listas = listaRepository.nativaPorUsuarioNickname("altnick");

        assertEquals(1, listas.size());
        assertEquals("Mix Alternativo", listas.get(0).getNombre());
    }
}
