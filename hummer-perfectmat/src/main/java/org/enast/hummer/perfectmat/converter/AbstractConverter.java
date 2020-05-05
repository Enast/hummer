package org.enast.hummer.perfectmat.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.enast.hummer.perfectmat.common.PConstant;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.enast.hummer.perfectmat.entity.BusinessType;
import org.enast.hummer.perfectmat.entity.ResourceCache;
import org.enast.hummer.perfectmat.entity.ResourceQuotas;
import org.enast.hummer.perfectmat.util.ErrorCodeUtil;
import org.enast.hummer.perfectmat.util.JsonNodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author zhujinming6
 * @create 2019-09-21 13:28
 * @update 2019-09-21 13:28
 **/
public abstract class AbstractConverter implements BasicConverter {

    public Logger log = LoggerFactory.getLogger(AbstractConverter.class);

    /**
     * 保留两位小数:格式
     */
    public String TWO_DECIMAL_ABBREVIATIONS = "0.00";
    public String SIX_DECIMAL_ABBREVIATIONS = "0.000000";

    protected Set<String> resetQuotas;

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    /**
     * 特殊报文解析入口
     * 统一处理错误码和其他扩展方法
     *
     * @param simpleVO     资源缓存信息
     * @param accessData   报文原始对象
     * @param quotas
     * @param dataObject   报文data的对象
     * @param businessType 能力集类型
     */
    @Override
    public void analysisSpecial(ResourceCache simpleVO, AccessData accessData, ResourceQuotas quotas, JsonNode dataObject, String businessType) {
        String code = accessData.getCode();
        // 处理错误码
        dealError(simpleVO, quotas, accessData, code, businessType, dataObject);
        analysis(simpleVO, accessData, quotas, dataObject, businessType, code);
    }

    /**
     * 转换器和解析必需实现的方法
     *
     * @param simpleVO
     * @param accessData
     * @param quota
     * @param dataObject
     * @param businessType
     * @param code
     */
    @Override
    public void analysis(ResourceCache simpleVO, AccessData accessData, ResourceQuotas quota, JsonNode dataObject, String businessType, String code) {

    }

    /**
     * 转换器和解析必需实现的方法
     *
     * @param simpleVO
     * @param jsonNode
     * @param status
     * @param dataObject
     * @param businessType
     * @param code
     */
    @Override
    public void analysis(ResourceCache simpleVO, JsonNode jsonNode, Map<String, Object> status, JsonNode dataObject, String businessType, String code) {

    }

    /**
     * 不重载改方法默认使用当前的
     *
     * @param quotas
     * @param accessData
     * @param code
     * @param businessType
     */
    @Override
    public void dealError(ResourceCache simpleVO, ResourceQuotas quotas, AccessData accessData, String code, String businessType, JsonNode dataObject) {
        if (PConstant.NORMAL.equals(code)) {
            ErrorCodeUtil.errorCodeNormal(quotas.getQuotas(), businessType);
        } else {
            ErrorCodeUtil.singleCustomResource(simpleVO.getSubTypeCode(), quotas.getQuotas(), code, accessData, BusinessType.code4(businessType), dataObject);
        }
    }


    /**
     * 对象key值转换
     *
     * @param object
     * @param status
     */
    protected void objectConvert(JsonNode object, Map<String, Object> status) {
        Iterator<String> iterator = object.fieldNames();
        while (iterator.hasNext()) {
            String key = iterator.next();
            // 过滤调无效属性
            if (PConstant.INDEX_CODE.equals(key)) {
                continue;
            }
            status.put(key, JsonNodeUtil.getObject(object.get(key)));
        }
    }

    /**
     * jsonArray 转 json字符串
     *
     * @param key
     * @param node
     * @param status
     */
    protected void array2JsonStr(String key, Object node, Map<String, Object> status) {
        try {
            status.put(key, objectMapper.writeValueAsString(node));
        } catch (JsonProcessingException e) {
            log.error("", e.getMessage());
        }
    }

    /**
     * 计算使用率
     *
     * @param used
     * @param total
     * @param key
     * @param status
     */
    protected void computeUsage(JsonNode used, JsonNode total, String key, Map<String, Object> status) {
        double usedD = doubleObject(used), tatalD = doubleObject(total);
        computeUsageDouble(usedD, tatalD, key, status);
    }

    protected void computeUsage(Object used, Object total, String key, Map<String, Object> status) {
        double usedD = doubleObject2(used), tatalD = doubleObject2(total);
        computeUsageDouble(usedD, tatalD, key, status);
    }

    /**
     * 计算使用率(百分比)(double)
     * 保留两位小数
     *
     * @param used
     * @param total
     * @param key
     * @param status
     */
    protected void computeUsageDouble(double used, double total, String key, Map<String, Object> status) {
        DecimalFormat df = new DecimalFormat(TWO_DECIMAL_ABBREVIATIONS);
        Float usage;
        String usageStr;
        if (total == 0) {
            usageStr = df.format((double) 0 * 100);
        } else {
            usageStr = df.format(used / total * 100);
        }
        usage = Float.parseFloat(usageStr);
        status.put(key, usage);
    }

    /**
     * 数字转double辅助类
     *
     * @param o
     * @return
     */
    private double doubleObject(JsonNode o) {
        double result = 0;
        if (o.isInt()) {
            Integer totalI = o.asInt();
            result = totalI.doubleValue();
        } else if (o.isLong()) {
            Long totalI = o.asLong();
            result = totalI.doubleValue();
        } else if (o.isFloat()) {
            Float totalI = o.floatValue();
            result = totalI.doubleValue();
        } else if (o.isDouble()) {
            Double totalI = o.asDouble();
            result = totalI.doubleValue();
        } else if (o.isBigDecimal()) {
            BigDecimal totalB = o.decimalValue();
            result = totalB.doubleValue();
        } else if (o.isTextual()) {
            Double totalI = Double.parseDouble(o.asText());
            result = totalI.doubleValue();
        }
        return result;
    }

    /**
     * 数字转double辅助类
     *
     * @param o
     * @return
     */
    private double doubleObject2(Object o) {
        double result = 0;
        if (o instanceof Integer) {
            Integer totalI = (Integer) o;
            result = totalI.doubleValue();
        } else if (o instanceof Long) {
            Long totalI = (Long) o;
            result = totalI.doubleValue();
        } else if (o instanceof Float) {
            Float totalI = (Float) o;
            result = totalI.doubleValue();
        } else if (o instanceof Double) {
            Double totalI = (Double) o;
            result = totalI.doubleValue();
        } else if (o instanceof BigDecimal) {
            BigDecimal totalI = (BigDecimal) o;
            result = totalI.doubleValue();
        } else if (o instanceof String) {
            Double totalI = Double.parseDouble((String) o);
            result = totalI.doubleValue();
        }
        return result;
    }

    /**
     * 遍历key求和，平均
     *
     * @param jsonArray
     * @param key
     * @param status
     * @return
     */
    protected void computeAvg(JsonNode jsonArray, String key, Map<String, Object> status) {
        DecimalFormat df = new DecimalFormat(TWO_DECIMAL_ABBREVIATIONS);
        double total = 0;
        int size = 0;
        for (JsonNode o : jsonArray) {
            if (o != null) {
                total += doubleObject(o);
                size += 1;
            }
        }
        double avg = size > 0 ? Double.parseDouble(df.format(total / size)) : 0;
        status.put(key + "Total", total);
        status.put(key + "Size", size);
        status.put(key + "Avg", avg);
    }

    /**
     * 遍历key求总状态（0：异常，1：正常）
     * 前置：0：异常，1：正常(map把不满足的映射过来)
     * [{"status":1}
     * {"status":2},
     * {"status":0}]
     *
     * @param jsonArray
     * @param key
     * @param status
     * @return
     */
    protected void computeStatus(JsonNode jsonArray, String key, Map<String, Object> status, Map<Object, Object> map) {
        status.put(key, 1);
        for (JsonNode o : jsonArray) {
            ObjectNode objectNode = (ObjectNode) o;
            JsonNode obj = objectNode.get(key);
            if (obj != null) {
                if (!CollectionUtils.isEmpty(map)) {
                    if (map.get(key) != null) {
                        objectNode.put(key, (Integer) map.get(JsonNodeUtil.getObject(obj)));
                    }
                }
                if ("0".equals(JsonNodeUtil.getObject(objectNode.get(key)))) {
                    status.put(key, 0);
                    break;
                }
            }
        }
    }

    /**
     * 计算平均值,计算值不同
     *
     * @param jsonArray
     * @param key
     * @param status
     */
    protected void computeSignleAvg(JsonNode jsonArray, String key, Map<String, Object> status) {
        int totalUsage = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonNode ob = jsonArray.get(i);
            if (ob == null) {
                continue;
            }
            Integer usage = JsonNodeUtil.getInteger(ob, key);
            if (usage != null) {
                totalUsage += usage.intValue();
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String num = df.format(((float) totalUsage / jsonArray.size()));
        Float usage = Float.parseFloat(num);
        status.put(key, usage);
        status.put("uNumber", jsonArray.size());
    }

    /**
     * 巡检正常的情况下,必检项,没有检测到设置为-1
     *
     * @param status
     */
    @Override
    public void resetStatus(Map<String, Object> status) {

    }
}
