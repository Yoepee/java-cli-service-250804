package com.object;

import com.object.standard.util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String cmd) {
        Scanner scanner = TestUtil.genScanner(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        AppContext.init(scanner);
        new App().run();

        return output.toString();
    }
}
