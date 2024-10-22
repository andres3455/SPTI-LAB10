package eci.cvds.armario.controller;

import eci.cvds.armario.model.Prenda;
import eci.cvds.armario.model.User;
import eci.cvds.armario.model.PrendaUsuario;
import eci.cvds.armario.repository.PrendaRepository;
import eci.cvds.armario.repository.PrendaUsuarioRepository;
import eci.cvds.armario.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://witty-field-0ab72731e.5.azurestaticapps.net"})
@RequestMapping(value = "/user")
public class PrendaController {
    private PrendaRepository prendaRepository;
    private PrendaUsuarioRepository prendaUsuarioRepository;
    private SessionRepository sessionRepository;

    @Autowired
    public PrendaController(PrendaRepository prendaRepository, PrendaUsuarioRepository prendaUsuarioRepository, SessionRepository sessionRepository) {
        this.prendaRepository = prendaRepository;
        this.prendaUsuarioRepository = prendaUsuarioRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/prendas")
    public List<Prenda> getAllPrendas() {
        return prendaRepository.findAll();
    }

    @GetMapping("/client/prenda/{idPrenda}")
    public ResponseEntity<Prenda> getPrendaById(@RequestHeader("authToken") UUID authToken, @PathVariable("idPrenda") UUID idPrenda) {
        User user = this.sessionRepository.findByToken(authToken).getUser();
        Prenda prenda = prendaRepository.findById(idPrenda).get();
        if (user != null) {
            return new ResponseEntity<>(prenda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/admin/prenda")
    public Prenda addPrenda(@RequestBody Prenda prenda, @RequestHeader("authToken") UUID authToken) {
        User user = this.sessionRepository.findByToken(authToken).getUser();
        Prenda savedPrenda = prendaRepository.save(prenda);

        prendaUsuarioRepository.save(new PrendaUsuario(savedPrenda, user));
        return savedPrenda;
    }
    @DeleteMapping("/admin/prenda/{idPrenda}")
    public ResponseEntity<String> deletePrenda(@RequestHeader("authToken") UUID authToken, @PathVariable("idPrenda") UUID idPrenda) {
        User user = this.sessionRepository.findByToken(authToken).getUser();
        if (user != null) {
            Prenda prenda = prendaRepository.findById(idPrenda).orElse(null);
            if (prenda != null) {
                PrendaUsuario prendaUsuario = prendaUsuarioRepository.findByPrenda(prenda);
                prendaUsuarioRepository.delete(prendaUsuario);
                prendaRepository.delete(prenda);
                return new ResponseEntity<>("Prenda eliminada correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("La prenda no existe", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No autorizado", HttpStatus.FORBIDDEN);
        }
    }
}

