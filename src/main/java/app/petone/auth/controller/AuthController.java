package app.petone.auth.controller;

import app.petone.auth.model.AuxToken;
import app.petone.auth.model.Login;
import app.petone.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuxToken> login(@RequestBody Login login){
        return new ResponseEntity<>(this.authService.login(login), HttpStatus.CREATED);
    }

    /*
    @PostMapping("/login/test")
    public ResponseEntity<AuxToken> loginTest(@RequestBody Login login){
        return new ResponseEntity<>(this.authService.Test(), HttpStatus.CREATED);
    }
    */
}
