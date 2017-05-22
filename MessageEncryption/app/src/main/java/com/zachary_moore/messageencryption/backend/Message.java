package com.zachary_moore.messageencryption.backend;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zsmoore on 5/22/17.
 */

public class Message {

    private String _messageText;
    private String _encryptedText;
    private User _owner;

    public Message(String inputText, User owner){
        _owner = owner;
        _messageText = inputText;
        _encryptedText = "";
    }

    public void encrypt(){
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("AES");
        } catch(NoSuchPaddingException e){
            System.out.println("Something went wrong, improper padding method.");
        } catch(NoSuchAlgorithmException e){
            System.out.println("Something went wrong, improper encryption algorithm.");
        }

        try{
            cipher.init(Cipher.ENCRYPT_MODE, _owner.getMasterKey());
        } catch(InvalidKeyException e){
            System.out.println("Owner Master Key Error.");
        }

        try{
            _encryptedText = new String(cipher.doFinal(_messageText.getBytes()));
        } catch (IllegalBlockSizeException e){
            System.out.println("Encryption Error Block Size.");
        } catch(BadPaddingException e){
            System.out.println("Encryption Error Bad Padding.");
        }
    }

    public String getMessage(){
        return _messageText;
    }

    public String getEncryptedTest(){
        return _encryptedText;
    }

}
