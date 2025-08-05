package com.object;

import com.object.domain.article.controller.ArticleController;
import com.object.domain.article.repository.ArticleRepository;
import com.object.domain.article.service.ArticleService;
import com.object.domain.system.controller.SystemController;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner;
    public static ArticleRepository articleRepository;
    public static ArticleService articleService;
    public static ArticleController articleController;
    public static SystemController systemController;

    public static void init(Scanner _scanner) {
        scanner = _scanner;
        articleRepository = new ArticleRepository();
        articleService = new ArticleService();
        articleController = new ArticleController();
        systemController = new SystemController();
    }

    public static void init() {
        init(new Scanner(System.in));
    }
}
