package com.object.domain.article.controller;

import com.object.AppContext;
import com.object.Rq;
import com.object.domain.article.entity.Article;
import com.object.domain.article.service.ArticleService;

import java.io.IOException;
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
        System.out.print("제목: ");
        String title = scanner.nextLine();

        System.out.print("내용: ");
        String content = scanner.nextLine();

        if (title.isEmpty()) {
            System.out.println("=> !!!제목을 입력해 주세요!!!\n");
            return;
        }

        if (content.isEmpty()) {
            System.out.println("=> !!!내용을 입력해 주세요!!!\n");
            return;
        }

        articleService.writeArticle(title, content);
        System.out.println("=> 게시글이 등록되었습니다.\n");
    }

    public void updateArticle(Rq rq) {
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            printMessage("다시 입력해주세요. (형식: update [id])");
            return;
        }


        Article targetArticle = articleService.findById(id);
        if(targetArticle == null) {
            printMessage(String.format("%d번 게시글이 존재하지 않습니다.", id));
            return;
        }


        System.out.printf("제목 (현재: %s): ", targetArticle.getTitle());
        String newTitle = scanner.nextLine().trim();
        System.out.printf("내용 (현재: %s): ", targetArticle.getContent());
        String newContent = scanner.nextLine().trim();


        articleService.update(targetArticle, newTitle, newContent);

        printMessage("게시글이 수정되었습니다.");
    }

    public void deleteArticle(Rq rq){
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            System.out.println("유효하지 않은 id 입니다.");
            return;
        }

        boolean isDeleted = articleService.deleteArticle(id);
        if (isDeleted) {
            printMessage("게시글이 삭제되었습니다.");
        } else {
            printMessage("게시글이 존재하지 않습니다.");
        }
    }

    public void listArticles(Rq rq) {
        String keyword = rq.getParam(0, "");
        if (keyword.isEmpty()) {
            System.out.println("검색 키워드를 입력해주세요.");
            return;
        }

        List<Article> articles = articleService.getArticles(keyword);
        if (articles.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
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

    public void showDetail(Rq rq) {
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            System.out.println("유효하지 않은 id 입니다.");
            return;
        }

        Article articleEntity = articleService.findById(id);
        if (articleEntity == null) {
            System.out.println("해당 게시글이 존재하지 않습니다.");
            return;
        }

        System.out.println("번호: " + articleEntity.getId());
        System.out.println("제목: " + articleEntity.getTitle());
        System.out.println("내용: " + articleEntity.getContent());
        System.out.println("등록일: " + articleEntity.getRegDate());
    }

    public void saveArticles() {
        try {
            articleService.save();
            printMessage("저장되었습니다.");
        } catch (IOException e) {
            handleIOException("저장");
        }
    }

    public void loadArticlesAndLastId() {
        try {
            articleService.load();
            printMessage("데이터가 로드되었습니다.");
        } catch (IOException e) {
            handleIOException("데이터 로드");
        }
    }

    private void printMessage(String message) {
        System.out.println("=> " + message + "\n");
    }

    private void handleIOException(String action) {
        System.out.printf("=> %s에 실패했습니다.\n\n", action);
    }
}
