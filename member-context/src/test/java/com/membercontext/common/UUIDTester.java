package com.membercontext.common;

import java.util.regex.Pattern;

public class UUIDTester {

    static Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    public static boolean isUUID(String uuid) {
        return UUID_REGEX.matcher(uuid).matches();
    }
}
