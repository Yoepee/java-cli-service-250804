package com.object.domain.article.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.object.AppContext;
import com.object.domain.article.entity.Article;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class ArticleRepository {
    private final String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    List<Article> articles;
    int lastId;

    public ArticleRepository() {
        filePath = AppContext.filePath;
        articles = new ArrayList<>();
        lastId = 0;
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
        if (keyword == null || keyword.isBlank()) {
            return articles;
        }

        String lowerKeyword = keyword.toLowerCase();
        return articles.stream()
                .filter(article -> article.getTitle().toLowerCase().contains(lowerKeyword) ||
                                   article.getContent().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    //파일로 저장
    public void save() throws IOException {
        //폴더 확인해서 없으면 생성
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if(parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if(!created) throw new IOException();
        }

        //내림차순 정렬
        List<Article> sortedArticles = new ArrayList<>(articles);
        sortedArticles.sort((Comparator.comparing(Article::getId)).reversed());

        //파일에 저장
        objectMapper.writeValue(file, sortedArticles);
    }

    //초기에 자동 로드
    public void load() throws IOException {
        //파일 확인
        File file = new File(filePath);
        if(!file.exists()) {
            lastId = 0;
            return;
        }

        //데이터 로드
        List<Article> loadedArticles = objectMapper.readValue(file, new TypeReference<List<Article>>() {});
        articles.clear();
        articles.addAll(loadedArticles);

        //마지막 id 가져오기
        if(articles.isEmpty()) lastId = 0;
        else lastId = articles.get(0).getId();
    }

    public int getLastId(){
        return lastId;
    }
}
