package com.object.domain.system.controller;

import com.object.AppContext;
import com.object.Rq;

import java.util.Scanner;

public class SystemController {
    private static Scanner scanner;
    public SystemController() {
        this.scanner = AppContext.scanner;
    }
    public void start(){
        System.out.println("시작");
    }

    public void stop(){
        scanner.close();
    }

    public Rq getCommand() {
        System.out.print("명령어: ");
        String cmd = scanner.nextLine().trim();

        return new Rq(cmd);
    }
}
