package com.autentifpcar.mobile;

import java.util.Locale;

final class Plate {
    private Plate() { }

    static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT).replaceAll("\\s+", "");
    }

    static boolean isValid(String value) {
        return value.matches("[A-Z0-9-]{4,15}");
    }
}
