package mx.dgtic.unam.M7AP_Luis_Gonzalez.repository;

import mx.dgtic.unam.M7AP_Luis_Gonzalez.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}

