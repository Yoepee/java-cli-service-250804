package com.object.standard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilTest {
    @Test
    @DisplayName("입력 테스트")
    public void t1() {
        Scanner scanner = TestUtil.genScanner("1");
        String cmd = scanner.nextLine();
        assertThat(cmd).isEqualTo("1");
    }
}
