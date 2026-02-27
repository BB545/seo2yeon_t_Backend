package com.seo2yeon.students.global.util;

import java.util.Random;

public class RandomCodeGenerator {
    public static String generate6DigitCode() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
