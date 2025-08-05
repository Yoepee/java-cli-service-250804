package com.object;

import com.object.domain.article.controller.ArticleController;
import com.object.domain.system.controller.SystemController;

import java.util.Scanner;

public class App {
    private Scanner scanner;
    private SystemController systemController;
    private ArticleController articleController;
    App(){
        systemController = AppContext.systemController;
        articleController = AppContext.articleController;
    }
    public void run() {
        systemController.start();
        while(true) {
            Rq rq = systemController.getCommand();
            String actionName = rq.getActionName();

            switch (actionName) {
                case "exit" -> {
                    systemController.stop();
                    return;
                }
                case "write" -> articleController.writeArticle();
                case "list" -> articleController.listArticles(rq);
                case "delete" -> articleController.deleteArticle(rq);
            }
        }
    }
}
