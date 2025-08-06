package com.object;

import com.object.domain.article.controller.ArticleController;
import com.object.domain.article.repository.ArticleRepository;
import com.object.domain.article.service.ArticleService;
import com.object.domain.system.controller.SystemController;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner;
    public static String filePath;
    public static ArticleRepository articleRepository;
    public static ArticleService articleService;
    public static ArticleController articleController;
    public static SystemController systemController;

    public static void renew(Scanner _scanner, String _filePath) {
        scanner = _scanner;
        filePath = _filePath;

        articleRepository = new ArticleRepository();
        articleService = new ArticleService();
        articleController = new ArticleController();
        systemController = new SystemController();
    }

    public static void renew() {
        renew(new Scanner(System.in), "db/articles.json");
    }
}
