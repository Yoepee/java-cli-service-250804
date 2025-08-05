package com.object;

import com.object.domain.system.controller.SystemController;

import java.util.Scanner;

public class App {
    private Scanner scanner;
    private SystemController systemController;
    App(){
        systemController = AppContext.systemController;
    }
    public void run() {
        System.out.println("App is running!");
        systemController.start();
        while(true) {
            Rq rq = systemController.getCommand();
            String cmd = rq.getActionName();

            if (cmd.equals("exit")) {
                System.out.println("종료");
                break;
            }

            // 여기에 명령어 처리 로직을 추가할 수 있습니다.
            System.out.println("명령어: " + cmd);
        }
        systemController.stop();
    }
}
