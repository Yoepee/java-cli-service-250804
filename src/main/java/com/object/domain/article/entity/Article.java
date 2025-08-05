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
}
