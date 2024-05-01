package com.yuuu.utils;

public class PhoneNumberEncryptionUtil {
    
    public static String encryptPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 7) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        StringBuilder encrypted = new StringBuilder();
        encrypted.append(phoneNumber.substring(0, 3)); // 前三位
        for (int i = 3; i < phoneNumber.length() - 4; i++) {
            encrypted.append("*"); // 中间位置用*代替
        }
        encrypted.append(phoneNumber.substring(phoneNumber.length() - 4)); // 后四位
        return encrypted.toString();
    }

    public static void main(String[] args) {
        String phoneNumber = "12345678901"; // 示例电话号码
        String encrypted = encryptPhoneNumber(phoneNumber);
        System.out.println(encrypted);
    }
}
