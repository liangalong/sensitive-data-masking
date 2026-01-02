package com.example.sensitivemask.serializer;

import com.example.sensitivemask.annotation.Mask;
import com.example.sensitivemask.util.MaskingUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

public class SensitiveDataSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private Mask maskAnnotation;

    public SensitiveDataSerializer() {}

    public SensitiveDataSerializer(Mask mask) {
        this.maskAnnotation = mask;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (maskAnnotation != null) {
            String masked = MaskingUtils.mask(value, maskAnnotation.type(), maskAnnotation.customPattern());
            gen.writeString(masked);
        } else {
            gen.writeString(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        Mask mask = property.getAnnotation(Mask.class);
        return new SensitiveDataSerializer(mask);
    }
}