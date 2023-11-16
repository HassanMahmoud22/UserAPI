package com.example.servicesImpl;

import com.example.constants.Messages;
import com.example.dtos.ResponseMessage;
import com.example.dtos.UserResponse;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.security.JwtTokenUtil;
import com.example.services.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.*;
@Service
public class UserServicesImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private ResponseMessage responseMessage;

    public UserServicesImpl() {
        responseMessage = new ResponseMessage();
    }

    public ResponseEntity<?> createUser(User userRequest) throws NoSuchAlgorithmException {
        User existingUser = userRepository.findByEmail(userRequest.getEmail()); //after controller checked on data format, this function contact with database to check if the user exists
        if (existingUser != null) { //if it exists it return conflict http status with a message
            responseMessage.setMessage(Messages.EMAILEXIST);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseMessage);
        }
        //else it creates new user and save it to database
        User newUser = new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.isMarketingConsent());
        userRepository.save(newUser);
        System.out.println(newUser.toString());
        //then it generate token for this user and return it with userResponse object
        UserResponse userResponse = new UserResponse(newUser.getId(), jwtTokenUtil.generateToken(newUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    public ResponseEntity<?> getUser(String id, String token) {
        //first it get user data from database by id
        User user = userRepository.findById(id).get();
        //if it doesn't exist it return 404 not found http status
        if(user.getId() == null) {
            responseMessage.setMessage(Messages.USERNOTEXIST);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        //else it returns the user with token in header
        return ResponseEntity.ok().header("Authorization", token).body(user);
    }
}
