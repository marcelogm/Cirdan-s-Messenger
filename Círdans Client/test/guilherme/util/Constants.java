/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


public class Constants {
    
    public static final String EMAIL_SERVER = createRandomString(8) + ".com";
    public static final String LARGE_EMAIL_BODY = createRandomAlphaNum(100);
    public static final String SMALL_EMAIL_BODY = createRandomAlphaNum(25);
    public static final String NAME = createRandomString(25);
    public static final String NICK = createRandomAlphaNum(20);
    public static final String PASS = createRandomAlphaNum(12);
    public static final String REPASS = createRandomAlphaNum(12);
    public static final String FULL_SMALL_EMAIL = SMALL_EMAIL_BODY + "@" + EMAIL_SERVER;
    public static final String FRINED_LARGE_EMAIL_BODY = createRandomAlphaNum(200);
    public static final String FRIEND_SMALL_EMAIL_BODY = createRandomAlphaNum(25);
    public static final String FRIEND_NAME = createRandomString(25);
    public static final String FRIEND_NICK = createRandomAlphaNum(20);
    public static final String FRIEND_PASS = createRandomAlphaNum(12);
    public static final String FRIEND_REPASS = createRandomAlphaNum(12);
    public static final String FRIEND_FULL_SMALL_EMAIL = FRIEND_SMALL_EMAIL_BODY + "@" + EMAIL_SERVER;
    public static final String MESSAGE = createRandomAlphaNum(100);
    public static final String ONLINE_FRIEND_NAME = "User de teste";
    public static final String ONLINE_FRIEND_EMAIL = "teste@email.com";
    public static final String ONLINE_FRIEND_PASS = "teste";
    public static final int MESSAGE_AMOUNT = 50;
    public static final String EMAIL_TESTE = "teste@teste.com";
    public static final String EMAIL_TESTE2 = "teste2@teste.com";
    public static final String EMAIL_TESTANDO = "testando@teste.com";
    
    private static String createRandomString(int size) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < size) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    private static String createRandomAlphaNum(int size) {
        return new BigInteger(size * 4, new SecureRandom()).toString(32);
    }
    
}
