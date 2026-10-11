package com.yuuu.utils;


import java.util.UUID;

/**
 * @author: yuuu
 * Description:
 */
public class VisitorUtil {

    public static String generateGuestUsername() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String uniqueIdentifier = uuidStr.substring(0, 8);
        String username = "游客" + uniqueIdentifier;
        return username;
    }

    public static String emailDesensitization(String s){
        String maskedEmail = s.replaceAll("^(.{3}).*(.{1}@.*)$", "$1****$2");
        return maskedEmail;
    }
}
