package com.example.security;

import com.example.constants.Messages;
import com.example.entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private boolean isValidEmail(String email){
        if(email.length() == 0)
            return false;
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        if (name.isEmpty() || name.isBlank())
            return false;
        char[] characters = name.toCharArray();
        for (char c : characters) {
            if(Character.isDigit(c))
                return false;
        }
        return true;
    }

    public String userDataValidation(User user) {
        if(!isValidName(user.getFirstName())) {
            return Messages.FIRSTNAMENOTVALID;
        } else if(!isValidName(user.getLastName())) {
            return Messages.LASTNAMENOTVALID;
        } else if(!isValidEmail(user.getEmail())){
            return Messages.EMAILNOTVALID;
        }
        return Messages.VALID;
    }
}