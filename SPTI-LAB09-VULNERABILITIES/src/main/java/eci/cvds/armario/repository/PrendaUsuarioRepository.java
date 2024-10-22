package eci.cvds.armario.repository;
import eci.cvds.armario.model.Prenda;
import eci.cvds.armario.model.PrendaUsuario;
import eci.cvds.armario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PrendaUsuarioRepository extends JpaRepository<PrendaUsuario, UUID>{
    List<PrendaUsuario> findByUser(User user);
    PrendaUsuario findByPrenda(Prenda prenda);
}