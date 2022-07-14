package com.terminal.manage.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author TAO
 * @date 2022/7/8 / 14:55
 */
@Configuration
public class ConfigModel {

    public static String KEY_PREFIX = "gd:terminal:";

    public static String TOKEN_PREFIX = "gd:terminal:user:";


    public static Long TOKEN_TIMEOUT = 1000*60*60*5L;

}
