package com.object;

import com.object.domain.system.controller.SystemController;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner;
    public static SystemController systemController;

    public static void init(Scanner _scanner) {
        scanner = _scanner;
        systemController = new SystemController();
    }

    public static void init() {
        init(new Scanner(System.in));
    }
}
