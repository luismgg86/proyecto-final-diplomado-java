package mx.dgtic.unam.M7AP_Luis_Gonzalez.service;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Lista;

import java.util.List;

public interface ListaService {
    List<Lista> obtenerListasPorUsuario(Integer usuarioId);
    Lista guardar(Lista lista);
    Lista obtenerPorIdConCanciones(Integer id);
    void agregarCanciones(Integer listaId, List<Integer> cancionesIds);
}
