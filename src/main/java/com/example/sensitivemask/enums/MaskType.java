package com.example.sensitivemask.enums;

public enum MaskType {
    /**
     * 手机号：138****1234
     */
    MOBILE,

    /**
     * 身份证：110***********1234
     */
    ID_CARD,

    /**
     * 邮箱：a***@example.com
     */
    EMAIL,

    /**
     * 银行卡：6222 **** **** 1234
     */
    BANK_CARD,

    /**
     * 自定义模式（由用户通过 customPattern 指定）
     */
    CUSTOM
}