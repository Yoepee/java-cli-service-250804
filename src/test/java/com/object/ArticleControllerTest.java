package com.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleControllerTest {
    @Test
    @DisplayName("게시글 등록 후 목록")
    public void t1() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                list
                """);

        assertThat(rs)
                .contains("번호 | 제목       | 등록일")
                .contains("-------------------------")
                .contains("1 | 자바 공부 | ");
    }

    @Test
    @DisplayName("게시글 2개 등록 후 목록")
    public void t2() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                write
                자바 확인
                자바 텍스트 게시판 클릭
                list
                """);

        assertThat(rs)
                .contains("번호 | 제목       | 등록일")
                .contains("-------------------------")
                .contains("2 | 자바 확인 | ")
                .contains("1 | 자바 공부 | ");
    }
    
    @Test
    @DisplayName("게시글 등록 후 삭제")
    public void t3() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                delete 1
                list
                """);

        assertThat(rs)
                .contains("=> 게시글이 삭제되었습니다.")
                .doesNotContain("1 | 자바 공부 | ");
    }
}
