package com.object.standard.util;

public class TestUtil {
    public static java.util.Scanner genScanner(String input) {
        return new java.util.Scanner(input+ "\nexit");
    }

    public static java.io.ByteArrayOutputStream setOutToByteArray() {
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(output));
        return output;
    }
}
