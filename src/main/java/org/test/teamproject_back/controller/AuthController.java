package org.test.teamproject_back.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.test.teamproject_back.dto.request.ReqAccessDto;
import org.test.teamproject_back.dto.request.ReqSigninDto;
import org.test.teamproject_back.dto.request.ReqSignupDto;
import org.test.teamproject_back.exception.SignupException;
import org.test.teamproject_back.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.test.teamproject_back.service.TokenService;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto) throws SignupException {
//        System.out.println(dto);
        String roleName = "ROLE_USER";
        return ResponseEntity.ok().body(authService.signup(dto, roleName));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody ReqSigninDto dto) {
//        System.out.println(dto);
        return ResponseEntity.ok().body(authService.signin(dto));
    }

    @GetMapping("/access")
    public ResponseEntity<?> access(ReqAccessDto dto) {
        return ResponseEntity.ok().body(tokenService.isValidAccessToken(dto.getAccessToken()));
    }

}
