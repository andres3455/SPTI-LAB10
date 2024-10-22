package eci.cvds.armario.controller;

import eci.cvds.armario.model.Session;
import eci.cvds.armario.model.User;
import eci.cvds.armario.repository.SessionRepository;
import eci.cvds.armario.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:3000", "https://witty-field-0ab72731e.5.azurestaticapps.net"})
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private final UserService userService;
    private final SessionRepository sessionRepository;

    @Autowired
    public LoginController(UserService userService, SessionRepository sessionRepository) {
        this.userService = userService;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("")
    public ResponseEntity<Session> loginSubmit(@RequestBody User userSend) {
        if (!userService.validarUsuario(userSend)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            User user = userService.getUserByUsername(userSend.getUsername());
            Session session = new Session(UUID.randomUUID(), Instant.now(), user);
            sessionRepository.save(session);
            return new ResponseEntity<>(session, HttpStatus.OK);
        }
    }

    @PostMapping("logout")
    public ResponseEntity<String> logoutSubmit(HttpServletRequest request, HttpServletResponse response) {
        UUID authToken = (!request.getHeader("authToken").isEmpty() ? UUID.fromString(request.getHeader("authToken")) : null);
        if (authToken == null) {
            return new ResponseEntity<>("No esta autorizado para cerrar sesion ", HttpStatus.UNAUTHORIZED);
        }
        try {
            Session session = sessionRepository.getReferenceById(authToken);
            sessionRepository.delete(session);
            return new ResponseEntity<>("La sesión se cerro correctamente", HttpStatus.OK);
        }catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>("No se encontro sesion abierta", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> registerSubmit(@RequestBody User userSend) {
        userService.adicionar(userSend);
        return new ResponseEntity<>("Usuario registrado correctamente \n", HttpStatus.OK);
    }

    @DeleteMapping("/eliminarSesiones")
    public void eliminarSesiones(){
        sessionRepository.deleteAll();
    }
}