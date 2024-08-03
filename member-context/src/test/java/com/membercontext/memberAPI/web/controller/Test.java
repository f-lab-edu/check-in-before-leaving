package com.membercontext.memberAPI.web.controller;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        String test = "test";
        String test2 = new String("test");

        assertEquals(test.intern(), test2.intern());
        System.out.println(test == test2);
        assertEquals(test, test2);
        assertNotSame(test, test2);
    }
}
