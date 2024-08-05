package org.githubwuzupkev.controllers.Auth;

import jakarta.validation.Valid;
import org.githubwuzupkev.models.requests.EmployeeRequest;
import org.githubwuzupkev.models.requests.auth.UserLoginRequest;
import org.githubwuzupkev.models.responses.auth.UserResponse;
import org.githubwuzupkev.services.impl.UserDetailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserDetailImp userDetailService;

    @PostMapping("/log-in")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid EmployeeRequest userRequest) throws Exception {
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }
}
