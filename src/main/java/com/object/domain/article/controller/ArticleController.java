package com.object.domain.article.controller;

import com.object.AppContext;
import com.object.Rq;
import com.object.domain.article.entity.Article;
import com.object.domain.article.service.ArticleService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ArticleController {
    private final Scanner scanner;
    private final ArticleService articleService;
    private final Set<String> allowedSortTypes = Set.of("id", "title", "regDate", "count");
    public ArticleController() {
        scanner = AppContext.scanner;
        articleService = AppContext.articleService;
    }

    public void writeArticle(){
        System.out.print("제목: ");
        String title = scanner.nextLine();

        if (title.isEmpty()) {
            printMessage("제목을 입력해 주세요.");
            return;
        }

        System.out.print("내용: ");
        String content = scanner.nextLine();

        if (content.isEmpty()) {
            printMessage("내용을 입력해 주세요.");
            return;
        }

        articleService.writeArticle(title, content);
        printMessage("게시글이 등록되었습니다.");
    }

    public void updateArticle(Rq rq) {
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            printMessage("다시 입력해주세요. (형식: update [id])");
            return;
        }

        Article targetArticle = articleService.findById(id);
        if(targetArticle == null) {
            printMessage("%d번 게시글이 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.printf("제목 (현재: %s): ", targetArticle.getTitle());
        String newTitle = scanner.nextLine().trim();
        System.out.printf("내용 (현재: %s): ", targetArticle.getContent());
        String newContent = scanner.nextLine().trim();

        articleService.updateArticle(targetArticle, newTitle, newContent);

        printMessage("게시글이 수정되었습니다.");
    }

    public void deleteArticle(Rq rq){
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            printMessage("다시 입력해주세요. (형식: delete [id])");
            return;
        }

        boolean isDeleted = articleService.deleteArticle(id);
        if (isDeleted) {
            printMessage("게시글이 삭제되었습니다.");
        } else {
            printMessage("%d번 게시글이 존재하지 않습니다.".formatted(id));
        }
    }

    public void listArticles() {
        printArticle(articleService.getArticles());
    }

    public void searchArticles(Rq rq) {
        String keyword = rq.getParam(0, "");
        if (keyword.isEmpty()) {
            printMessage("검색 키워드를 입력해주세요.");
            return;
        }

        printArticle(articleService.getArticles(keyword));
    }

    public void sortArticles(Rq rq) {
        String sortType = rq.getParam(0, "id"); // 기본값: id
        String sortOrder = rq.getParam(1, "desc");    // 기본값: desc

        if (!allowedSortTypes.contains(sortType)) {
            printMessage("허용되지 않은 정렬 기준입니다. (id, title, regDate, count)");
            return;
        }

        if (!sortOrder.equals("asc") && !sortOrder.equals("desc")) {
            printMessage("정렬 순서는 'asc' 또는 'desc' 만 가능합니다.");
            return;
        }

        printArticle(articleService.getSortedArticles(sortType, sortOrder));
    }

    public void printArticle(List<Article> articles) {
        if (articles.isEmpty()) {
            printMessage("검색 결과가 없습니다.");
            return;
        }

        System.out.println("번호 | 제목       | 등록일          | 조회수");
        System.out.println("--------------------------------------------");
        for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("%d | %s | %s | %d\n", article.getId(), article.getTitle(), article.getRegDate(), article.getCount());
        }
        System.out.println();
    }

    public void showDetail(Rq rq) {
        int id = rq.getParamAsInt(0, -1);
        if (id == -1) {
            printMessage("다시 입력해주세요. (형식: detail [id])");
            return;
        }

        Article article = articleService.findAndIncreaseViewCount(id);
        if (article == null) {
            printMessage("%d번 게시글이 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.println("번호: " + article.getId());
        System.out.println("제목: " + article.getTitle());
        System.out.println("내용: " + article.getContent());
        System.out.println("등록일: " + article.getRegDate());
        System.out.println("조회수: " + article.getCount() + "\n");
    }

    public void saveArticles() {
        try {
            articleService.save();
            printMessage("저장되었습니다.");
        } catch (IOException e) {
            handleIOException("저장", e);
        }
    }

    public void loadArticlesAndLastId() {
        try {
            articleService.load();
            printMessage("데이터가 로드되었습니다.");
        } catch (IOException e) {
            handleIOException("데이터 로드", e);
        }
    }

    private void handleIOException(String action, IOException e) {
        System.out.println("=> %s에 실패했습니다.".formatted(action));
        System.out.println("오류 메세지: " + e.getMessage() + "\n");
    }

    private void printMessage(String message) {
        System.out.println("=> %s\n".formatted(message));
    }
}
