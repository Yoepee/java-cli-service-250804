package com.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CountAndSearchTest {
    @Test
    @DisplayName("게시글 상세보기 - 조회수 증가")
    public void t1() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            detail 1
            """);

        assertThat(rs)
                .contains("번호: 1")
                .contains("제목: 자바 공부")
                .contains("내용: 자바 텍스트 게시판 만들기")
                .contains("조회수: 1"); // 증가 확인
    }

    @Test
    @DisplayName("게시글 상세보기 - 여러 번 호출 시 조회수 누적")
    public void t2() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            detail 1
            detail 1
            """);

        assertThat(rs)
                .contains("조회수: 1") // 첫 호출
                .contains("조회수: 2"); // 두 번째 호출
    }

    @Test
    @DisplayName("게시글 상세보기 - 존재하지 않는 ID")
    public void t3() {
        String rs = AppTestRunner.run("""
            detail 999
            """);

        assertThat(rs)
                .contains("=> 해당 게시글이 존재하지 않습니다.")
                .doesNotContain("조회수:");
    }

// 검색 기능 테스트 3가지 (search [keyword] 호출 후 출력 확인)

    @Test
    @DisplayName("게시글 검색 - 키워드 맞는 게시글 출력")
    public void t4() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            write
            파이썬 학습
            파이썬 데이터 분석
            search 자바
            """);

        assertThat(rs)
                .contains("1 | 자바 공부 | ")
                .doesNotContain("2 | 파이썬 학습 | ");
    }

    @Test
    @DisplayName("게시글 검색 - 키워드 없는 경우 빈 결과")
    public void t5() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            search 루비
            """);

        assertThat(rs)
                .contains("검색 결과가 없습니다.");
    }

    @Test
    @DisplayName("게시글 검색 - 빈 키워드 입력 시 에러 메시지")
    public void t6() {
        String rs = AppTestRunner.run("""
            search
            """);

        assertThat(rs)
                .contains("검색 키워드를 입력해주세요.");
    }
}
