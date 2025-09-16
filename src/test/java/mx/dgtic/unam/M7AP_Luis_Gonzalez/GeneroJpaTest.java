package mx.dgtic.unam.M7AP_Luis_Gonzalez;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.ArtistaRepository;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.CancionRepository;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.GeneroRepository;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Artista;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Genero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GeneroJpaTest {

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    CancionRepository cancionRepository;

    @Autowired
    ArtistaRepository artistaRepository;

    private Genero crearGenero(String clave, String nombre) {
        Genero g = new Genero();
        g.setClave(clave);
        g.setNombre(nombre);
        return generoRepository.save(g);
    }

    private Artista crearArtista(String clave, String nombre) {
        Artista a = new Artista();
        a.setClave(clave);
        a.setNombre(nombre);
        return artistaRepository.save(a);
    }

    private void crearCancion(String titulo, String claveGenero, String nombreGenero, String claveArtista, String nombreArtista) {
        Genero genero = crearGenero(claveGenero, nombreGenero);
        Artista artista = crearArtista(claveArtista, nombreArtista);

        Cancion c = new Cancion();
        c.setTitulo(titulo);
        c.setAudio("archivo.mp3");
        c.setDuracion(200);
        c.setFechaAlta(LocalDate.now());
        c.setGenero(genero);
        c.setArtista(artista);

        genero.agregarCancion(c);
        artista.agregarCancion(c);
    }

    @Test
    void findByClave() {
        crearGenero("RCK", "Rock");

        Optional<Genero> result = generoRepository.findByClave("RCK");

        assertTrue(result.isPresent());
        assertEquals("Rock", result.get().getNombre());
    }

    @Test
    void findByClaveIgnoreCase() {
        crearGenero("POP", "Pop");

        List<Genero> result = generoRepository.findByClaveIgnoreCase("pop");

        assertEquals(1, result.size());
        assertEquals("POP", result.get(0).getClave());
    }

    @Test
    void findByNombreContainingIgnoreCase() {
        crearGenero("JAZ", "Jazz");

        List<Genero> result = generoRepository.findByNombreContainingIgnoreCase("Jazz");

        assertEquals(1, result.size());
        assertEquals("JAZ", result.get(0).getClave());
    }

    @Test
    void buscarPorClaveNativa() {
        crearGenero("ALT", "Alternativo");

        Optional<Genero> result = generoRepository.buscarPorClave("ALT");

        assertTrue(result.isPresent());
        assertEquals("Alternativo", result.get().getNombre());
    }

    @Test
    void buscarPorNombreNativa() {
        crearGenero("CLS", "Clasica");

        List<Genero> result = generoRepository.buscarPorNombre("sica");

        assertFalse(result.isEmpty());
        assertEquals("CLS", result.get(0).getClave());
    }

    @Test
    void detalleConCancionesPorClaveJoin() {
        crearCancion("Bohemian Rhapsody", "RCK", "Rock", "Q01", "Queen");

        Optional<Genero> result = generoRepository.detalleConCancionesPorClave("RCK");

        assertTrue(result.isPresent());
        assertFalse(result.get().getCanciones().isEmpty());
        assertEquals("Bohemian Rhapsody", result.get().getCanciones().get(0).getTitulo());
    }
}
