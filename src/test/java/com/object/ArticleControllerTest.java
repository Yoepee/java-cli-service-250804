package com.object;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.object.domain.article.entity.Article;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleControllerTest {
    String filePath = "testDb/fileTest.json";

    @BeforeEach
    void clearFileBefore() {
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void clearFileAfter() {
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * List, Delete Test
     */
    @Test
    @DisplayName("게시글 등록 후 목록")
    public void t1_list_test() {
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
    public void t2_list_two_articles_test() {
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
    public void t3_delete_test() {
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

    /**
     * Count, Search Test
     */
    @Test
    @DisplayName("게시글 상세보기 - 조회수 증가")
    public void t4_view_count_increase_test() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            detail 1
            """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("번호: 1")
                .contains("제목: 자바 공부")
                .contains("내용: 자바 텍스트 게시판 만들기")
                .contains("조회수: 1"); // 증가 확인
    }

    @Test
    @DisplayName("게시글 상세보기 - 여러 번 호출 시 조회수 누적")
    public void t5_view_count_accumulate_test() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            detail 1
            detail 1
            """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("조회수: 1") // 첫 호출
                .contains("조회수: 2"); // 두 번째 호출
    }

    @Test
    @DisplayName("게시글 상세보기 - 존재하지 않는 ID")
    public void t6_view_not_found_test() {
        String rs = AppTestRunner.run("""
            detail 999
            """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("=> 해당 게시글이 존재하지 않습니다.")
                .doesNotContain("조회수:");
    }

// 검색 기능 테스트 3가지 (search [keyword] 호출 후 출력 확인)

    @Test
    @DisplayName("게시글 검색 - 키워드 맞는 게시글 출력")
    public void t7_search_match_test() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            write
            파이썬 학습
            파이썬 데이터 분석
            search 자바
            """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("1 | 자바 공부 | ")
                .doesNotContain("2 | 파이썬 학습 | ");
    }

    @Test
    @DisplayName("게시글 검색 - 키워드 없는 경우 빈 결과")
    public void t8_search_empty_result_test() {
        String rs = AppTestRunner.run("""
            write
            자바 공부
            자바 텍스트 게시판 만들기
            search 루비
            """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("검색 결과가 없습니다.");
    }

    @Test
    @DisplayName("게시글 검색 - 빈 키워드 입력 시 에러 메시지")
    public void t9_search_empty_keyword_test() {
        String rs = AppTestRunner.run("""
            search
            """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("검색 키워드를 입력해주세요.");
    }

    /**
     * update, save, load Test
     */
    @Test
    @DisplayName("작성")
    void t10_write_test() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("제목: ")
                .contains("내용: ")
                .contains("=> 게시글이 등록되었습니다.");
    }

    @Test
    @DisplayName("수정")
    void t11_update_test() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                자바 텍스트 게시판 만들기
                update 1
                Java 게시판
                콘솔 기반으로 구현
                """);

        AssertionsForClassTypes.assertThat(rs)
                .contains("제목 (현재: 자바 공부): ")
                .contains("내용 (현재: 자바 텍스트 게시판 만들기): ")
                .contains("=> 게시글이 수정되었습니다.");
    }

    @Test
    @DisplayName("수정 - 명령어 오류")
    void t12_update_exception_test1() {
        String rs = AppTestRunner.run("""
                update ~~~
                """);
        AssertionsForClassTypes.assertThat(rs).contains("다시 입력해주세요. (형식: update [id]");
    }

    @Test
    @DisplayName("수정 - 존재하지 않는 id")
    void t13_update_exception_test2() {
        String rs = AppTestRunner.run("""
                update 999
                """);
        AssertionsForClassTypes.assertThat(rs).contains("999번 게시글이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("저장")
    void t14_save_test() throws IOException {
        String rs = AppTestRunner.run("""
                write
                테스트 제목
                테스트 내용
                save
                """, filePath);

        AssertionsForClassTypes.assertThat(rs)
                .contains("=> 저장되었습니다.");

        // 저장된 파일 검증
        File file = new File(filePath);
        AssertionsForClassTypes.assertThat(file.exists()).isTrue();
        AssertionsForClassTypes.assertThat(file.length()).isGreaterThan(0);


        // 파일 내부 내용 검증
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> articles = objectMapper.readValue(file, new TypeReference<>() {});
        AssertionsForClassTypes.assertThat(articles.stream().anyMatch(a ->
                a.getTitle().equals("테스트 제목") &&
                        a.getContent().equals("테스트 내용")
        )).isTrue();
    }

    @Test
    @DisplayName("로드")
    void t15_load_test() {

        String rs1 = AppTestRunner.run("""
                write
                테스트 제목1
                테스트 내용1
                write
                테스트 제목2
                테스트 내용2
                save
                """, filePath);

        String rs2 = AppTestRunner.run("""
                list
                """, filePath);

        AssertionsForClassTypes.assertThat(rs2)
                .contains("데이터가 로드되었습니다.")
                .contains("1 | 테스트 제목1 | ")
                .contains("2 | 테스트 제목2 | ");

        int lastId = AppContext.articleRepository.getLastId();
        AssertionsForClassTypes.assertThat(lastId).isEqualTo(2);
    }

    @Test
    @DisplayName("삭제 - 잘못된 id")
    void t19_delete_idError() {
        String rs = AppTestRunner.run("""
                delete ~~~
                """);
        AssertionsForClassTypes.assertThat(rs).contains("=> 다시 입력해주세요. (형식: delete [id])");
    }

    @Test
    @DisplayName("삭제 - 존재하지않는 id")
    void t20_delete_id_not_exist() {
        String rs = AppTestRunner.run("""
                delete 999
                """);
        AssertionsForClassTypes.assertThat(rs).contains("=> 999번 게시글이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("작성 - 빈 제목")
    void t21_write_blank_title() {
        String rs = AppTestRunner.run("""
                write
                
                """);
        AssertionsForClassTypes.assertThat(rs).contains("=> !!!제목을 입력해 주세요!!!");
    }

    @Test
    @DisplayName("작성 - 빈 내용")
    void t22_write_blank_content() {
        String rs = AppTestRunner.run("""
                write
                자바 공부
                
                """);
        AssertionsForClassTypes.assertThat(rs).contains("=> !!!내용을 입력해 주세요!!!");
    }
}
