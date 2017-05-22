package com.zachary_moore.messageencryption.backend;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zsmoore on 5/22/17.
 */

public class User {

    private static final String TAG = "User";

    private ArrayList<Message> _messages;
    private String _userName;
    private String _password;
    private SecretKeySpec _masterKey;


    public User(String name, String password){
        _userName = name;
        _password = password;
        _messages = new ArrayList<>();
        _masterKey = genKey("SHA-1");
    }

    public void setPassword(String pass){
        _password = pass;
    }

    public SecretKeySpec genKey(String hashMethod){
        String randomize = "Sally Sold Sea Shells By the Seashore";
        byte[] secretKey = (_userName + _password + randomize).getBytes();

        MessageDigest sha = null;

        try {
            sha = MessageDigest.getInstance(hashMethod);
        } catch(NoSuchAlgorithmException e){
            Log.e(TAG, "Invalid HashMethod Using SHA-1.");
            try {
                sha = MessageDigest.getInstance("SHA-1");
            } catch(NoSuchAlgorithmException e2){
                Log.e(TAG, "Sha-1 Went Wrong.");
            }
        }

        secretKey = sha.digest(secretKey);
        secretKey = Arrays.copyOf(secretKey, 16);

        return new SecretKeySpec(secretKey, "AES");
    }

    public void addMessage(Message toAdd){
        _messages.add(toAdd);
    }

    public SecretKeySpec getMasterKey(){
        return _masterKey;
    }

}
