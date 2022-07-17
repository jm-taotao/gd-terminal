package com.terminal.manage.tool;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author TAO
 * @date 2022/7/17 / 16:37
 */
public class Encryption {

    private Encryption() {}
    private static final Encryption instance = new Encryption();
    public static Encryption getInstance() {
        return instance;
    }

    /**
     * 生成Token
     * @return
     */
    public String makeToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
