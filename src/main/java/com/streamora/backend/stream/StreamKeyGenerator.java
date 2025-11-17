package com.streamora.backend.stream;

import java.security.SecureRandom;

public class StreamKeyGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 32;

    public static String generate() {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            key.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return key.toString();
    }
}
