/**
 *
 */
package com.kt.dpla.support.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static String toJson(Object obj) {
        String returnJson = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnJson = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Exception Occur. getJson {}", e.getMessage());
        }
        return returnJson;
    }

    public static String toJsonPretty(Object obj) {
        String returnJson = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Exception Occur. getJsonPretty {}", e.getMessage());
        }
        return returnJson;
    }

    public static void jsonToFile(Object obj, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), obj);
        } catch (IOException e) {
            log.error("Exception Occur. jsonToFile {}", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String jsonString) {
        Map<String, Object> resultMap = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            resultMap = mapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Exception Occur. getJsonToMap {}", e.getMessage());
        }
        return resultMap;
    }

    @SuppressWarnings("unchecked")
    public static <T> T jsonToType(String jsonString, Class<T> clazz) {
        T result = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            result = mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            log.error("Exception Occur. jsonToType error :: {}", e.getMessage());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonToList(String jsonString) {
        List<Map<String, Object>> resultList = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            resultList = mapper.readValue(jsonString, List.class);
        } catch (JsonProcessingException e) {
            log.error("Exception Occur. getJsonToList {}", e.getMessage());
        }
        return resultList;
    }
}