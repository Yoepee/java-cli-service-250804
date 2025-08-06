package com.object;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.object.domain.article.entity.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("파일 저장/로드 테스트")
class FileTest {

    String filePath = "testDb/fileTest.json";

    @BeforeEach
    void clearFile() {
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
    }

    @Test
    @DisplayName("저장")
    void t1_save_test() throws IOException {
        String rs = AppTestRunner.run("""
                write
                테스트 제목
                테스트 내용
                save
                """, filePath);

        assertThat(rs)
                .contains("=> 저장되었습니다.");

        // 저장된 파일 검증
        File file = new File(filePath);
        assertThat(file.exists()).isTrue();
        assertThat(file.length()).isGreaterThan(0);

        // 파일 내부 내용 검증
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> articles = objectMapper.readValue(file, new TypeReference<>() {});
        assertThat(articles.stream().anyMatch(a ->
                a.getTitle().equals("테스트 제목") &&
                        a.getContent().equals("테스트 내용")
        )).isTrue();
    }

    @Test
    @DisplayName("로드")
    void t2_load_test() {

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

        assertThat(rs2)
                .contains("데이터가 로드되었습니다.")
                .contains("1 | 테스트 제목1 | ")
                .contains("2 | 테스트 제목2 | ");

        int lastId = AppContext.articleRepository.getLastId();
        assertThat(lastId).isEqualTo(2);
    }
}
