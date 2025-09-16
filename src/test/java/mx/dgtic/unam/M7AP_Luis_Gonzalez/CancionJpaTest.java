package mx.dgtic.unam.M7AP_Luis_Gonzalez;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.*;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CancionJpaTest {

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Cancion crearCancion(String titulo, String claveArtista, String nombreArtista,
                                 String claveGenero, String nombreGenero) {

        Artista artista = artistaRepository.findByClave(claveArtista)
                .orElseGet(() -> {
                    Artista a = new Artista();
                    a.setClave(claveArtista);
                    a.setNombre(nombreArtista);
                    return artistaRepository.save(a);
                });

        Genero genero = generoRepository.findByClave(claveGenero)
                .orElseGet(() -> {
                    Genero g = new Genero();
                    g.setClave(claveGenero);
                    g.setNombre(nombreGenero);
                    return generoRepository.save(g);
                });

        Cancion c = new Cancion();
        c.setTitulo(titulo);
        c.setAudio("archivo.mp3");
        c.setDuracion(200);
        c.setFechaAlta(LocalDate.now());
        c.setArtista(artista);
        c.setGenero(genero);

        return cancionRepository.save(c);
    }

    @Test
    void testFindByArtistaNombreContainingIgnoreCase() {
        crearCancion("Bonita", "A01", "Caifanes", "ROC", "Rock");

        List<Cancion> result = cancionRepository.findByArtista_NombreContainingIgnoreCase("caif");

        assertEquals(1, result.size());
        assertEquals("Bonita", result.get(0).getTitulo());
    }

    @Test
    void testFindByGeneroClave() {
        crearCancion("Rosas", "A02", "La Oreja", "POP", "Pop");

        List<Cancion> result = cancionRepository.findByGenero_Clave("POP");

        assertEquals(1, result.size());
        assertEquals("Rosas", result.get(0).getTitulo());
    }

    @Test
    void testFindByListas_Id() {

        Cancion cancion = crearCancion("There, There", "A03", "Radiohead", "ALT", "Alternativo");

        Usuario u = new Usuario();
        u.setNombre("Luis");
        u.setApPaterno("González");
        u.setApMaterno("Gutiérrez");
        u.setCorreo("lmgg@example.com");
        u.setNickname("lmanggz");
        u.setContrasena("123");
        u.setRol((short) 1);
        u = usuarioRepository.save(u);

        Lista lista = new Lista();
        lista.setNombre("Mis favoritas");
        lista.setFechaCreacion(LocalDate.now());
        lista.setUsuario(u);
        lista.getCanciones().add(cancion);

        lista = listaRepository.save(lista);

        List<Cancion> result = cancionRepository.findByListas_Id(lista.getId());

        assertEquals(1, result.size());
        assertEquals("There, There", result.get(0).getTitulo());
    }

    @Test
    void testFindByTituloContainingIgnoreCaseConPaginadoYOrdenamiento() {
        crearCancion("Yeah!", "USH", "Usher", "POP", "Pop");
        crearCancion("Yellow Submarine", "BEAT", "The Beatles", "ROC", "Rock");
        crearCancion("Yesterday", "BEAT", "The Beatles", "ROC", "Rock");

        Pageable pageable = PageRequest.of(0, 2, Sort.by("titulo").ascending());

        Page<Cancion> page = cancionRepository.findByTituloContainingIgnoreCase("ye", pageable);

        assertEquals(2, page.getContent().size());
        assertEquals("Yeah!", page.getContent().get(0).getTitulo());
        assertEquals("Yellow Submarine", page.getContent().get(1).getTitulo());
    }

    @Test
    void testBuscarPorNombreArtista_nativa() {
        crearCancion("Yellow", "C01", "Coldplay", "ALT", "Alternativo");

        List<Cancion> result = cancionRepository.buscarPorNombreArtista("cold");

        assertFalse(result.isEmpty());
        assertEquals("Yellow", result.get(0).getTitulo());
    }

    @Test
    void testNativaPorArtistaYGenero() {
        crearCancion("Obstacle 1", "I01", "Interpol", "ALT", "Alternativo");

        List<Cancion> result = cancionRepository.nativaPorArtistaYGenero("I01", "ALT");

        assertFalse(result.isEmpty());
        assertEquals("Obstacle 1", result.get(0).getTitulo());
    }

    @Test
    void testBuscarPorClaveGenero() {
        crearCancion("La Flaca", "J01", "Jarabe de Palo", "ROC", "Rock");

        List<Cancion> result = cancionRepository.buscarPorClaveGenero("ROC");

        assertEquals(1, result.size());
        assertEquals("La Flaca", result.get(0).getTitulo());
    }
}
