package com.example.services;
import com.example.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;

@Service
public interface UserService {
    public ResponseEntity<?> createUser(User userRequest) throws NoSuchAlgorithmException;
    public ResponseEntity<?> getUser(String id, String token);
}