package com.example.sensitivemask.annotation;

import com.example.sensitivemask.enums.MaskType;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = com.example.sensitivemask.serializer.SensitiveDataSerializer.class)
public @interface Mask {
    MaskType type() default MaskType.CUSTOM;
    String customPattern() default ""; // 用于自定义掩码规则
}
