package com.object.domain.article.service;

import com.object.AppContext;
import com.object.domain.article.entity.Article;
import com.object.domain.article.repository.ArticleRepository;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService() {
        repository = AppContext.articleRepository;
    }

    public Article writeArticle(String title, String content) {
        Article article = new Article(title, content);
        repository.saveArticle(article);
        return article;
    }

    public Article update(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        return repository.saveArticle(article);
    }

    public boolean deleteArticle(int id) {
        Article article = repository.getArticleById(id);
        if (article == null) return false;
        repository.deleteArticle(article);
        return true;
    }

    public Article findById(int id) {
        return repository.getArticleById(id);
    }

    public Article findAndIncreaseViewCount(int id) {
        Article article = findById(id);
        if (article != null) {
            article.increaseCount();
        }

        return article;
    }

    public List<Article> getArticles(String keyword) {
        return repository.getArticles(keyword);
    }
    public List<Article> getArticles() {
        return getArticles("");
    }
    public List<Article> getSortedArticles(String sortType, String sortOrder) {
        List<Article> articles = getArticles(); // 복사해서 정렬

        Comparator<Article> comparator = switch (sortType) {
            case "id" -> Comparator.comparing(Article::getId);
            case "title" -> Comparator.comparing(Article::getTitle);
            case "regDate" -> Comparator.comparing(Article::getRegDate);
            case "count" -> Comparator.comparingInt(Article::getCount);
            default -> throw new IllegalArgumentException("지원하지 않는 정렬 기준: " + sortType);
        };

        if (sortOrder.equals("asc")) {
            comparator = comparator.reversed();
        }

        articles.sort(comparator);
        return articles;
    }

    public void save() throws IOException {
        repository.save();
    }

    public void load() throws IOException {
        repository.load();
    }
}
