package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import model.Password;

public class UPassword {
    private static final int MAX_ITERATIONS = 1000;
    
    public static Password generatePassword(String secret){
        Random rand = new Random();
        Password pass = new Password();
        Integer iterations = rand.nextInt(MAX_ITERATIONS) + 1;
        pass.setIterations(iterations);
        Integer salt = rand.nextInt();
        pass.setSalt(salt.toString());
        secret = secret + salt.toString();
        pass.setPassword(hashing(secret, iterations));
        return pass;
    }
    
    public static void updatePassword(Password pass, String secret){
        Random rand = new Random();
        Integer iterations = rand.nextInt(MAX_ITERATIONS) + 1;
        pass.setIterations(iterations);
        Integer salt = rand.nextInt();
        pass.setSalt(salt.toString());
        secret = secret + salt.toString();
        pass.setPassword(hashing(secret, iterations));
    }
    
    public static String passwordIterate(String word, String salt, int iterations){
        String secret = word + salt;
        return hashing(secret, iterations);
    }
    
    private static String hashing(String secret, int iterations){
        MessageDigest md;
        try {
            for (int i = 0; i < iterations; i++) {
                md = MessageDigest.getInstance("SHA-256");
                md.update(secret.getBytes());
                byte byteData[] = md.digest();
                StringBuilder hexString = new StringBuilder();
                for(int j = 0; j < byteData.length; j++) {
                    String buffer = Integer.toHexString(0xff & byteData[j]);
                    if(buffer.length()==1) hexString.append('0');
                    hexString.append(buffer);
                }
                secret = hexString.toString();
            }
        } catch (NoSuchAlgorithmException e){
            System.out.println(e);
        }
        return secret;
    }
}
