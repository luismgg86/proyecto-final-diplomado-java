package mx.dgtic.unam.M7AP_Luis_Gonzalez.service;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.CancionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CancionServiceImpl implements CancionService {

    private final CancionRepository cancionRepository;

    // Inyectamos el REPOSITORY, no el servicio
    public CancionServiceImpl(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    @Override
    public List<Cancion> listarTodas() {
        return cancionRepository.findAll();
    }

    @Override
    public Cancion buscarPorId(Integer id) {
        return cancionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("La canción con ID " + id + " no existe."));
    }

    @Override
    public Cancion guardar(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    @Override
    public void eliminar(Integer id) {
        if (!cancionRepository.existsById(id)) {
            throw new NoSuchElementException("La canción con ID " + id + " no existe.");
        }
        cancionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return cancionRepository.existsById(id);
    }
}
