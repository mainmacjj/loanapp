package com.macj7.loanapp.service;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author macj7
 */

public class PassCodeGenerator {
    
    private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklnmopqrstuvwxyz0123456789";
    private final char[] valid_chars;
    
    public PassCodeGenerator() {
        this.valid_chars = characters.toCharArray();
    }
    
    
    public String getPassCode(int length){
        
        SecureRandom srandom = new SecureRandom();
        Random random = new Random();
        
        char[] buffer = new char[length];
        
        for (int index = 0; index<length; index++) {
            
            
            if ((index % 10 ) == 0) {
                random.setSeed(srandom.nextLong());
            }
            
            buffer[index] = valid_chars[random.nextInt(valid_chars.length)];
        }
        return new String(buffer);
    }
}
