package com.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("게시글 수정 테스트")
public class UpdateTest {

    @Test
    @DisplayName("작성")
    void t1_write_test() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                """);

        assertThat(rs)
                .contains("제목: ")
                .contains("내용: ")
                .contains("=> 게시글이 등록되었습니다.");
    }

    @Test
    @DisplayName("수정")
    void t2_update_test() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                update 1
                Java 게시판
                콘솔 기반으로 구현
                """);

        assertThat(rs)
                .contains("제목 (현재: 자바 공부): ")
                .contains("내용 (현재: 자바 텍스트 게시판 만들기): ")
                .contains("=> 게시글이 수정되었습니다.");
    }

    @Test
    @DisplayName("수정 - 명령어 오류")
    void t3_update_exception_test1() {
        String rs = AppTestRunner.run("""
                update ~~~
                """);
        assertThat(rs).contains("다시 입력해주세요. (형식: update [id]");
    }

    @Test
    @DisplayName("수정 - 존재하지 않는 id")
    void t4_update_exception_test2() {
        String rs = AppTestRunner.run("""
                update 999
                """);
        assertThat(rs).contains("999번 게시글이 존재하지 않습니다.");
    }
}