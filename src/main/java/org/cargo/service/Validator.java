package org.cargo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Validator {

    public static boolean validateRegistration(String username, String password, String email) {
        if (Objects.isNull(username) || Objects.isNull(password) || Objects.isNull(email)) {
            return false;
        }
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            return false;
        }
        if (!username.matches("([A-Za-z0-9-]+)") || !password.matches("((?=.*\\d)(?=.*[A-Za-z]).{8,15})") ||
                !email.matches("(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})")) {
            return false;
        }
        return true;
    }

    public static boolean validateProfileUpdate(String password, String email) {
        if (Objects.isNull(password) || Objects.isNull(email) || password.isEmpty() || email.isEmpty()) {
            return false;
        }

        if (!password.matches("((?=.*\\d)(?=.*[A-Za-z]).{8,15})") ||
                !email.matches("(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})")) {
            return false;
        }
        return true;
    }

    public static Map<String, String> validatePageRequest(String page, String size, String sortDirection, String sortBy) {
        Map<String, String> pageParams = new HashMap<>();
        pageParams.put("p", page);
        pageParams.put("s", size);
        pageParams.put("sortBy", sortBy);
        pageParams.put("sortDirection", sortDirection);

        return pageParams;
    }
}
