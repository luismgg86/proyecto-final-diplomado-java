package mx.dgtic.unam.M7AP_Luis_Gonzalez.service;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Cancion;

import java.util.List;

public interface CancionService {

    List<Cancion> listarTodas();

    Cancion buscarPorId(Integer id);

    Cancion guardar(Cancion cancion);

    void eliminar(Integer id);

    boolean existsById(Integer id);

}
