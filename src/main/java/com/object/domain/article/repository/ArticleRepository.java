package com.object.domain.article.repository;

import com.object.domain.article.entity.Article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    List<Article> articles;
    int lastId;

    public ArticleRepository() {
        this.articles = new ArrayList<>();
        this.lastId = 0;
    }

    public Article getArticleById(int id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void deleteArticle(Article article) {
        articles.remove(article);
    }

    public Article saveArticle(Article article) {
        if (article.isNew()) {
            article.setId(++lastId);
            LocalDateTime now = LocalDateTime.now();
            article.setRegDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            articles.add(article);
        }

        return article;
    }

    public List<Article> getArticles(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return articles;
        }

        String lowerKeyword = keyword.toLowerCase();
        return articles.stream()
                .filter(article -> article.getTitle().toLowerCase().contains(lowerKeyword) ||
                                   article.getContent().toLowerCase().contains(lowerKeyword))
                .toList();
    }
}
