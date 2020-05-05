package org.enast.hummer.perfectmat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.enast.hummer.common.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author zhujinming6
 * @create 2019-11-18 15:10
 * @update 2019-11-18 15:10
 **/
public class JsonNodeUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonNodeUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public static String getString(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return node.asText();
    }

    public static Integer getInteger(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return node.asInt();
    }

    public static Long getLong(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return node.asLong();
    }

    public static Float getFloat(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return node.floatValue();
    }


    public static Double getDouble(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return node.doubleValue();
    }

    public static BigDecimal getBigDecimal(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return node.decimalValue();
    }

    public static Date getDateIOS8601_XXX(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return TimeUtils.str2DateIOS8601XXX(node.asText());
    }

    public static Date getDate(JsonNode jsonNode, String key) {
        if (jsonNode == null) {
            return null;
        }
        JsonNode node = jsonNode.get(key);
        if (node == null) {
            return null;
        }
        return TimeUtils.castToDate(node.asText());
    }

    public static boolean isEmptyArray(JsonNode jsonNode) {
        return !isNotEmptyArray(jsonNode);
    }

    public static boolean isNotEmptyArray(JsonNode jsonNode) {
        if (jsonNode != null && jsonNode.isArray() && jsonNode.size() > 0) {
            return true;
        }
        return false;
    }

    public static Object getObject(JsonNode valueObject) {
        if (valueObject.isNull()) {
            return null;
        }
        if (valueObject.isTextual()) {
            return valueObject.asText();
        } else if (valueObject.isDouble()) {
            return valueObject.asDouble();
        } else if (valueObject.isFloat()) {
            return valueObject.floatValue();
        } else if (valueObject.isBigDecimal()) {
            return valueObject.decimalValue();
        } else if (valueObject.isBoolean()) {
            return valueObject.asBoolean();
        } else if (valueObject.isLong()) {
            return valueObject.asLong();
        } else if (valueObject.isInt()) {
            return valueObject.asInt();
        } else if (valueObject.isBinary()) {
            try {
                return valueObject.binaryValue();
            } catch (IOException e) {
                log.error( "", e.getMessage());
            }
        }else {
            try {
                return objectMapper.writeValueAsString(valueObject);
            } catch (JsonProcessingException e) {
                log.error( "", e.getMessage());
            }
        }
        return null;
    }

    public static <T> List<T> nodeToArray(JsonNode jsonNode, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(jsonNode), typeReference);
        } catch (JsonProcessingException e) {
            log.error( "", e);
        } catch (IOException e) {
            log.error( "", e);
        }
        return Collections.EMPTY_LIST;
    }

    public static <T> List<T> nodeToArray(String str, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (JsonProcessingException e) {
            log.error( "", e);
        } catch (IOException e) {
            log.error( "", e);
        }
        return Collections.EMPTY_LIST;
    }


    public static <T> T nodeToObject(JsonNode jsonNode, Class<T> valueType) {
        try {
            return objectMapper.treeToValue(jsonNode, valueType);
        } catch (JsonProcessingException e) {
            log.error( "", e);
        } catch (IOException e) {
            log.error( "", e);
        }
        return null;
    }

    public static <T> T readValue(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }
}
