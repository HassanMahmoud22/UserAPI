package com.example.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCreator {
     private final static String SALT = "450d0b0db2bcf4adde5032eca1a7c416e560cf44";

    //this function takes id as parameter and hashes it using SHa1 hashing
    public static String encryptSHA1Hash(String id) throws NoSuchAlgorithmException {
        String sha1Hash = computeSHA1Hash(id);
        return sha1Hash;
    }

    private static String computeSHA1Hash(String id) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");   //first we obtain an instance of the MessageDigest class using the "SHA-1" algorithm.
        //then compute the hash by calling the digest method on the MessageDigest instance, passing the input string's bytes encoded in UTF-8.
        byte[] hashBytes = messageDigest.digest((id + SALT).getBytes(StandardCharsets.UTF_8));

        //Finally, we convert the byte array to a hexadecimal string representation and return it.
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
