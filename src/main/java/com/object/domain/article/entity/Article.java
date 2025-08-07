package com.object.domain.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Article {
    int id;
    String title;
    String content;
    String regDate;
    int count = 0;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @JsonIgnore
    public boolean isNew() {
        return id == 0;
    }

    public void increaseCount() {
        this.count++;
    }
}
