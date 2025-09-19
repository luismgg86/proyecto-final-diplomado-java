package mx.dgtic.unam.M7AP_Luis_Gonzalez.controller;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Lista;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.service.CancionService;
import mx.dgtic.unam.M7AP_Luis_Gonzalez.service.ListaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PrincipalController {

    private final CancionService cancionService;
    private final ListaService listaService;

    public PrincipalController(CancionService cancionService, ListaService listaService) {
        this.cancionService = cancionService;
        this.listaService = listaService;
    }

    @GetMapping("/")
    public String mostrarPrincipal(Model model) {
        model.addAttribute("canciones", cancionService.listarTodas());

        // Este atributo debe existir en el modelo
        model.addAttribute("contenido", "canciones/canciones :: fragment");

        return "layout/main";
    }

    @GetMapping("/playlists")
    public String mostrarPlaylists(Model model) {
        // Ejemplo: obtiene las playlists del usuario logueado
        List<Lista> playlists = listaService.obtenerListasPorUsuario(1);

        model.addAttribute("playlists", playlists);
        model.addAttribute("contenido", "playlists/playlists :: fragment");
        return "layout/main";
    }

}
