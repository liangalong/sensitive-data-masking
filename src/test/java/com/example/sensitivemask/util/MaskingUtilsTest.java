package com.example.sensitivemask.util;

import com.example.sensitivemask.enums.MaskType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MaskingUtilsTest {

    @Test
    void testMaskMobile() {
        assertThat(MaskingUtils.mask("13812345678", MaskType.MOBILE, ""))
                .isEqualTo("138****5678");
    }

    @Test
    void testMaskIdCard() {
        assertThat(MaskingUtils.mask("110101199003071234", MaskType.ID_CARD, ""))
                .isEqualTo("110101**********1234");
    }

    @Test
    void testMaskEmail() {
        assertThat(MaskingUtils.mask("alice@example.com", MaskType.EMAIL, ""))
                .isEqualTo("a***@example.com");
    }

    @Test
    void testMaskBankCard() {
        assertThat(MaskingUtils.mask("6222081234567890", MaskType.BANK_CARD, ""))
                .isEqualTo("**** **** **** 7890");
    }

    @Test
    void testCustomPattern() {
        assertThat(MaskingUtils.mask("ORDER123456789", MaskType.CUSTOM, "5,4"))
                .isEqualTo("ORDER*****6789");
    }
}