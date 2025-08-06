package com.object;

import com.object.standard.util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String input) {
        String defaultFilePath = "testDb/articles.json";
        return run(input, defaultFilePath);
    }

    //테스트를 위해 run 메서드 오버로딩
    public static String run(String input, String filePath) {
        Scanner scanner = TestUtil.genScanner(input + "\nexit");
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        AppContext.renew(scanner, filePath);
        new App().run();

        return output.toString();
    }
}
