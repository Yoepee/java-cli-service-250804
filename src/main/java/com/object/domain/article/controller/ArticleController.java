package com.object.domain.article.controller;

import com.object.AppContext;
import com.object.Rq;
import com.object.domain.article.entity.Article;
import com.object.domain.article.service.ArticleService;

import java.util.List;
import java.util.Scanner;

public class ArticleController {
    private Scanner scanner;
    private ArticleService articleService;
    public ArticleController() {
        scanner = AppContext.scanner;
        this.articleService = AppContext.articleService;
    }

    public void writeArticle(){
        System.out.print("제목:");
        String title = scanner.nextLine().trim();
        System.out.print("내용:");
        String content = scanner.nextLine().trim();

        Article article = articleService.writeArticle(title, content);
        System.out.println("=> 게시글이 등록되었습니다.");
        System.out.println();
    }

    public void deleteArticle(Rq rq){
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            System.out.println("유효하지 않은 id 입니다.");
            return;
        }

        boolean isDeleted = articleService.deleteArticle(id);
        if (isDeleted) {
            System.out.println("=> 게시글이 삭제되었습니다.");
        } else {
            System.out.println("=> 게시글이 존재하지 않습니다.");
        }
    }

    public void listArticles(Rq rq) {
        String keyword = rq.getParam(0, null);
        List<Article> articles = articleService.getArticles(keyword);
        if (articles.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }

        System.out.println("번호 | 제목       | 등록일");
        System.out.println("-------------------------");
        for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("%d | %s | %s%n", article.getId(), article.getTitle(), article.getRegDate());
        }
        System.out.println();
    }
}
