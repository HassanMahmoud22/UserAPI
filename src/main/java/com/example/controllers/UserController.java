package com.example.controllers;

import com.example.constants.Messages;
import com.example.dtos.ResponseMessage;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.security.JwtTokenUtil;
import com.example.security.Validation;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/userapi/v1")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private Validation validation;
    private JwtTokenUtil jwtTokenUtil;
    private ResponseMessage responseMessage;


    public UserController() {
        validation = new Validation();
        jwtTokenUtil = new JwtTokenUtil();
        responseMessage = new ResponseMessage();
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User userRequest) throws NoSuchAlgorithmException {
        String userValidationResponse = validation.userDataValidation(userRequest);   //first it validates the requested user data to check its format
        if (userValidationResponse.equals(Messages.VALID))
            return userService.createUser(userRequest); //if it's ok, create this user
        responseMessage.setMessage(userValidationResponse);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);  //else return internal server error with message
    }

    @GetMapping("/get{id}")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam("id") String id,
                                                @RequestHeader("Authorization") String token) {
        if (!jwtTokenUtil.validateToken(token, id)) {
            responseMessage.setMessage(Messages.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);  //if token isn't valid then it return unauthorized http status
        }
        return userService.getUser(id, token); //else it returns user data
    }
}