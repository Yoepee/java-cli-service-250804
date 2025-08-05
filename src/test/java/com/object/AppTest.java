package com.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    @DisplayName("시작")
    public void t1() {
        String rs = AppTestRunner.run("""
                exit
                """);

        // Check if the output contains the expected welcome message
        assertThat(rs).contains("시작")
                .contains("명령어: ")
                .contains("종료");
    }
}
