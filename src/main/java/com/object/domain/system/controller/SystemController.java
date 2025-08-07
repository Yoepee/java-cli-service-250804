package com.object.domain.system.controller;

import com.object.AppContext;
import com.object.Rq;

import java.util.Scanner;

public class SystemController {
    private static Scanner scanner;
    public SystemController() {
        scanner = AppContext.scanner;
    }
    public void start(){
        System.out.println("프로그램을 시작합니다.");
    }

    public void stop(){
        System.out.println("프로그램을 종료합니다.");
        scanner.close();
    }

    public Rq getCommand() {
        System.out.print("명령어: ");
        String cmd = scanner.nextLine().trim();

        return new Rq(cmd);
    }
}
