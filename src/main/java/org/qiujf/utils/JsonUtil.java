package org.qiujf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {
    public static  <T> T  paseJson(T t, String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (T) objectMapper.readValue(json,t.getClass());
    }
    public static <T> String toJson(T t) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(t);
    }
}
