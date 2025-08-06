package com.object;

import com.object.domain.article.controller.ArticleController;
import com.object.domain.system.controller.SystemController;

public class App {
    private SystemController systemController;
    private ArticleController articleController;
    App(){
        systemController = AppContext.systemController;
        articleController = AppContext.articleController;
    }
    public void run() {
        systemController.start();
        //초기 데이터 로드
        articleController.loadArticlesAndLastId();

        while(true) {
            Rq rq = systemController.getCommand();

            switch (rq.getActionName()) {
                case "exit" -> {
                    systemController.stop();
                    return;
                }
                case "write" -> articleController.writeArticle();
                case "list" -> articleController.listArticles(rq);
                case "search" -> articleController.searchArticles(rq);
                case "detail" -> articleController.showDetail(rq);
                case "update" -> articleController.updateArticle(rq);
                case "delete" -> articleController.deleteArticle(rq);
                case "sort" -> articleController.sortArticles(rq);
                case "save" -> articleController.saveArticles();
            }
        }
    }
}
