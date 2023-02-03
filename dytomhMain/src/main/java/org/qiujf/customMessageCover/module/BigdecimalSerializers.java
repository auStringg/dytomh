package org.qiujf.customMessageCover.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.qiujf.customMessageCover.BigDecimal2StringJsonSerializer;

import java.math.BigDecimal;

public class BigdecimalSerializers extends Serializers.Base implements Serializers {
    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class<?> raw = type.getRawClass();
        if (BigDecimal.class.isAssignableFrom(raw)) {
            return new BigDecimal2StringJsonSerializer();
        }
        return null;
    }


}
