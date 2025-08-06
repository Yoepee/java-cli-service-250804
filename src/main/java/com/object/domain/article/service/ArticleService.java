package com.object.domain.article.service;

import com.object.AppContext;
import com.object.domain.article.entity.Article;
import com.object.domain.article.repository.ArticleRepository;

import java.io.IOException;
import java.util.List;

public class ArticleService {
    private ArticleRepository repository;

    public ArticleService() {
        this.repository = AppContext.articleRepository;
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

    public List<Article> getArticles(String keyword) {
        return repository.getArticles(keyword);
    }

    public void save() throws IOException {
        repository.save();
    }

    public void load() throws IOException {
        repository.load();
    }
}
