package com.example.post_api.service;

import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;
@RequiredArgsConstructor
public class Validation {
    public static boolean isValidUsername(String username){
        final int MAX = 10;
        final int MIN = 4;

        // 소문자, 숫자 조합
        final String REGEX = "^[a-z0-9]{" + MIN + "," + MAX + "}$";

        return Pattern.matches(REGEX, username);
    }
    public static boolean isValidPassword(String password){
        final int MAX = 15;
        final int MIN = 8;

        // 영어, 숫자, 특수문자 조합
        final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{" + MIN + "," + MAX + "}$";

        return Pattern.matches(REGEX, password);
    }
}
