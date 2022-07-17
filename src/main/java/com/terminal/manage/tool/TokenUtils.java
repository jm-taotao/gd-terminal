package com.terminal.manage.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TAO
 * @date 2022/7/17 / 15:59
 */
public class TokenUtils {


    /**
     * @param key
     * @param value
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(String key, String value, long expire) throws Exception {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + expire);
        //秘钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(key);
        //设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("type", "JWT");
        header.put("alg", "HS256");
        //附带用户信息，生成token
        return JWT.create()
                .withHeader(header)
                .withClaim("userInfo", value)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
