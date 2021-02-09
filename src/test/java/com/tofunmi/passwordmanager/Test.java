package com.tofunmi.passwordmanager;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Created By tofunmi on 01/02/2021
 */
public class Test {
    public static void main(String[] argsl) throws Exception {
        //Creating a KeyGenerator object
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        //Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();

        //Initializing the KeyGenerator
        keyGen.init(secRandom);

        //Creating/Generating a key
        Key key = keyGen.generateKey();

        System.out.println(key);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(cipher.ENCRYPT_MODE, key);

        String msg = new String("Hi how are you");
        byte[] bytes = cipher.doFinal(msg.getBytes());
        System.out.println(bytes);
    }
}
