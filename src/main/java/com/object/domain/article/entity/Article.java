package com.object.domain.article.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    int id;
    String title;
    String content;
    String regDate;
    int viewCount = 0;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isNew() {
        return id == 0;
    }
}
