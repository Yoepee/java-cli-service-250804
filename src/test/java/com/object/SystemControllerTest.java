package com.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemControllerTest {
    @Test
    @DisplayName("시작 - 종료")
    public void t1() {
        String rs = AppTestRunner.run("""
                """);

        assertThat(rs)
                .contains("프로그램을 시작합니다.")
                .contains("명령어: ")
                .contains("프로그램을 종료합니다.");
    }
}
