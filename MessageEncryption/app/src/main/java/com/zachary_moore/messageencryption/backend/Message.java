package com.zachary_moore.messageencryption.backend;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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

    private static final int PLAIN = 0;
    private static final int ENCRYPTED = 1;
    private static final String TAG = "Message";

    private byte[] _displayText;
    private int _currentState;
    private User _owner;

    /**
     * Basic Constructor for Message.
     * @param inputText Text Message will hold.
     * @param owner Owner of the Message.
     */
    public Message(String inputText, User owner){
        _owner = owner;
        try {
            _displayText = inputText.getBytes("UTF8");
        } catch(UnsupportedEncodingException e){
            Log.e(TAG, "Unsupported Encoding");
        }
        _currentState = PLAIN;
    }

    /**
     * Encrypts input message Using Secret Key from Owner of message.
     * @param toEncrypt Message that will be encrypted.
     * @return Encrypted Message.
     */
    public byte[] encrypt(byte[] toEncrypt){

        Cipher cipher = getCipher();
        if(cipher == null){
            Log.e(TAG, "Error in Cipher Creation.");
            return new byte[0];
        }

        try{
            cipher.init(Cipher.ENCRYPT_MODE, _owner.getMasterKey());
        } catch(InvalidKeyException e){
            Log.e(TAG, "Owner Master Key Error.");
        }

        return executeCipher(cipher, toEncrypt);
    }

    /**
     * Decrypts a Message.
     * @param toDecrypt String that will be Decrypted.
     * @return The Decrypted String.
     */
    public byte[] decrypt(byte[] toDecrypt){

        Cipher cipher = getCipher();
        if(cipher == null){
            Log.e(TAG, "Error in Cipher Creating");
            return new byte[0];
        }

        try{
            cipher.init(Cipher.DECRYPT_MODE, _owner.getMasterKey());
        } catch(InvalidKeyException e){
            Log.e(TAG, "Owner Master Key Error.");
        }

        return executeCipher(cipher, toDecrypt);
    }

    /**
     * Wrapper for Cipher doFinal. Mainly to clean up code as a result
     * of exception handling.
     * @param inCipher Cipher to be used for execution.
     * @param inByte Input bytes for Cipher.
     * @return The result of the execution of the string on the Cipher.
     */
    public byte[] executeCipher(Cipher inCipher, byte[] inByte){
        byte[] ret = new byte[0];

        try{
            ret = inCipher.doFinal(inByte);
        } catch (IllegalBlockSizeException e){
            Log.e(TAG, "Encryption Error Block Size");
            Log.e(TAG, Log.getStackTraceString(e));
        } catch(BadPaddingException e){
            Log.e(TAG, "Encryption Error Bad Padding.");
        }

        return ret;
    }

    /**
     * Gets a Cipher that will be created with AES style encryption.
     * @return Instance of Cipher set up with AES Method.
     */
    public Cipher getCipher(){
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("AES");
        } catch(NoSuchPaddingException e){
            Log.e(TAG, "Something went wrong, improper padding method.");
        } catch(NoSuchAlgorithmException e){
            Log.e(TAG, "Something went wrong, improper encryption algorithm.");
        }

        return cipher;
    }

    /**
     * Swaps the current display text to be either encrypted or decrypted.
     */
    public void swapText(){
        if(_currentState == ENCRYPTED){
            _displayText = decrypt(_displayText);
            _currentState = PLAIN;
        } else if(_currentState == PLAIN){
            _displayText = encrypt(_displayText);
            _currentState = ENCRYPTED;
        }
    }

    /**
     * Gets the current text that is displayed in the message.
     * @return Displayed Text
     */
    public String getDisplayText(){
        return new String(_displayText, Charset.forName("UTF8"));
    }

}