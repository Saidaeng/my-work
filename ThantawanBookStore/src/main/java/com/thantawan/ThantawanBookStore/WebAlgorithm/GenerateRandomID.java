package com.thantawan.ThantawanBookStore.WebAlgorithm;

import java.util.Random;

public class GenerateRandomID {
    public static String generateRandomNumberString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));  // append a random digit (0-9)
        }
        return sb.toString();
    }
}
