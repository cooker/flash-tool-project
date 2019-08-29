package org.grant.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 22/8/2019 3:21 PM
 * 描述：json 工具
 */
@Slf4j
public final class JsonUtils {
    static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("toJson error", e);
            return null;
        }
    }

    public <T> T toObject(String str, Class<T> cl) {
        try {
            return objectMapper.readValue(str, cl);
        } catch (IOException e) {
            log.error("toObject error", e);
            return null;
        }
    }

    public <T> List<T> toCollections(String str, Class<T> cl) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, cl);
            return (List<T>) objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.error("toObject error", e);
            return null;
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

    }
}
