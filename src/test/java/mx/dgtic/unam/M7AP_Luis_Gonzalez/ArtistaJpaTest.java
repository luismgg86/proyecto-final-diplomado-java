package mx.dgtic.unam.M7AP_Luis_Gonzalez;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Artista;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Genero;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.ArtistaRepository;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.CancionRepository;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.GeneroRepository;
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
public class ArtistaJpaTest {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Test
    void encontrarPorClaveDerivada() {
        Artista a1 = new Artista();
        a1.setClave("a008");
        a1.setNombre("Luis Miguel");
        artistaRepository.save(a1);

        var opt = artistaRepository.findByClave("a008");

        assertTrue(opt.isPresent());
        assertEquals("Luis Miguel", opt.get().getNombre());
    }

    @Test
    void existeArtista() {
        Artista a1 = new Artista();
        a1.setClave("a008");
        a1.setNombre("Luis Miguel");
        artistaRepository.save(a1);

        boolean existe = artistaRepository.existsByClave(a1.getClave());

        assertTrue(existe);
    }

    @Test
    void buscarPorNombreContainingIgnoreCaseDerivada() {
        artistaRepository.save(new Artista(null, "A009", "Luis Miguel", null));
        artistaRepository.save(new Artista(null, "A010", "Caifanes", null));

        List<Artista> lista = artistaRepository.findByNombreContainingIgnoreCase("luis");

        assertFalse(lista.isEmpty());
        assertEquals("Luis Miguel", lista.get(0).getNombre());
    }

//    @Test
//    void nativaPorClave() {
//        Artista a2 = new Artista();
//        a2.setClave("A011");
//        a2.setNombre("Caifanes");
//        artistaRepository.save(a2);
//
//        var opt = artistaRepository.buscarPorClaveNativa("A011");
//
//        assertTrue(opt.isPresent());
//        assertEquals("Caifanes", opt.get().getNombre());
//    }

//    @Test
//    void nativaPorNombrelike() {
//        artistaRepository.save(new Artista(null, "A012", "Juan Gabriel", null));
//
//        List<Artista> lista = artistaRepository.buscarPorNombreNativa("gab");
//
//        assertFalse(lista.isEmpty());
//        assertTrue(lista.get(0).getNombre().toLowerCase().contains("gab"));
//    }

    @Test
    void detalleConCancionesPorClave() {

        Artista a1 = new Artista();
        a1.setClave("A009");
        a1.setNombre("Luis Miguel");
        a1 = artistaRepository.save(a1);

        Genero g = new Genero();
        g.setClave("BAL");
        g.setNombre("Balada");
        g = generoRepository.save(g);

        Cancion c1 = new Cancion();
        c1.setTitulo("La Incondicional");
        c1.setFechaAlta(LocalDate.now());
        c1.setDuracion(240);
        c1.setGenero(g);

        a1.agregarCancion(c1);

        artistaRepository.save(a1);

        Optional<Artista> opt = artistaRepository.detalleConCancionesPorClave("A009");

        assertTrue(opt.isPresent());
        Artista artista = opt.get();

        assertNotNull(artista.getCanciones());
        assertFalse(artista.getCanciones().isEmpty());

        Cancion primera = artista.getCanciones().get(0);
        assertEquals("La Incondicional", primera.getTitulo());
        assertEquals("Balada", primera.getGenero().getNombre());
    }
}
