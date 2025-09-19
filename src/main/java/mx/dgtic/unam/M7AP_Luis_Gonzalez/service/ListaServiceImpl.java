package mx.dgtic.unam.M7AP_Luis_Gonzalez.service;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Lista;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.CancionRepository;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.repository.ListaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ListaServiceImpl implements ListaService {

    private final ListaRepository listaRepository;
    private final CancionRepository cancionRepository;

    public ListaServiceImpl(ListaRepository listaRepository, CancionRepository cancionRepository) {
        this.listaRepository = listaRepository;
        this.cancionRepository = cancionRepository;
    }

    @Override
    public List<Lista> obtenerListasPorUsuario(Integer usuarioId) {
        return listaRepository.findByUsuario_Id(usuarioId);
    }

    @Override
    public Lista guardar(Lista lista) {
        return listaRepository.save(lista);
    }

    @Override
    public Lista obtenerPorIdConCanciones(Integer id) {
        return listaRepository.findByIdConRelaciones(id);
    }

    @Override
    public void agregarCanciones(Integer listaId, List<Integer> cancionesIds) {
        Lista lista = obtenerPorIdConCanciones(listaId);
        List<Cancion> canciones = cancionRepository.findAllById(cancionesIds);
        lista.getCanciones().addAll(canciones);
        listaRepository.save(lista);
    }
}
