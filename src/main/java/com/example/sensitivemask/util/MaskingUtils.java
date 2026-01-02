
package com.example.sensitivemask.util;

import com.example.sensitivemask.enums.MaskType;
import org.apache.commons.lang3.StringUtils;

public class MaskingUtils {

    public static String mask(String input, MaskType type, String customPattern) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return switch (type) {
            case MOBILE -> maskMobile(input);
            case ID_CARD -> maskIdCard(input);
            case EMAIL -> maskEmail(input);
            case BANK_CARD -> maskBankCard(input);
            case CUSTOM -> applyCustomPattern(input, customPattern);
            default -> input;
        };
    }

    private static String maskMobile(String mobile) {
        if (mobile.length() >= 11) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7);
        }
        return "****";
    }

    private static String maskIdCard(String id) {
        if (id.length() >= 18) {
            return id.substring(0, 6) + "**********" + id.substring(14);
        }
        return "****************";
    }

    private static String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            String prefix = email.substring(0, atIndex);
            if (prefix.length() <= 2) {
                return StringUtils.repeat("*",prefix.length()) + email.substring(atIndex);
            }
            return prefix.charAt(0) + "***" + email.substring(atIndex);
        }
        return "***@***";
    }

    private static String maskBankCard(String card) {
        String clean = card.replaceAll("\\s+", "");
        if (clean.length() >= 12) {
            int len = clean.length();
            String last4 = clean.substring(len - 4);
            return "**** **** **** " + last4;
        }
        return "**** **** **** ****";
    }

    private static String applyCustomPattern(String input, String pattern) {
        // 简单示例：pattern = "3,4" 表示保留前3后4，中间掩码
        if (pattern != null && pattern.contains(",")) {
            String[] parts = pattern.split(",");
            try {
                int keepStart = Integer.parseInt(parts[0]);
                int keepEnd = Integer.parseInt(parts[1]);
                if (input.length() <= keepStart + keepEnd) {
                    return StringUtils.repeat("*", input.length());
                }
                String start = input.substring(0, keepStart);
                String end = input.substring(input.length() - keepEnd);
                String middle = StringUtils.repeat("*",input.length() - keepStart - keepEnd);
                return start + middle + end;
            } catch (Exception e) {
                return "***";
            }
        }
        return "***";
    }
}