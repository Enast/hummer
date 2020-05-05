package org.enast.hummer.perfectmat.converter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.enast.hummer.perfectmat.entity.HdListAccess;
import org.enast.hummer.perfectmat.entity.HdStatus;
import org.enast.hummer.perfectmat.util.JsonNodeUtil;
import org.enast.hummer.perfectmat.entity.ResourceCache;
import org.enast.hummer.perfectmat.aop.DataConverter;
import org.enast.hummer.perfectmat.converter.AbstractConverter;
import org.enast.hummer.perfectmat.converter.service.HdListConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhujinming6
 * @create 2019-09-21 11:49
 * @update 2019-09-21 11:49
 **/
@Component
@DataConverter(keys = "hdList")
public class HdListConverterImpl extends AbstractConverter implements HdListConverter {


    /**
     * @param status
     */
    @Override
    public void resetStatus(Map<String, Object> status) {

    }

    @Resource
    ObjectMapper objectMapper;

    @Override
    public void analysis(ResourceCache simpleVO, JsonNode object, Map<String, Object> status, JsonNode jObject, String businessType, String code) {
        JsonNode jsonArray = jObject.get(HdListAccess.hdList.getOriginCode());
        if (JsonNodeUtil.isEmptyArray(jsonArray)) {
            return;
        }
        Long totalHD = null;
        Long totalFreeHD = null;
        Long totalUsedHD = null;
        int hdStatus = -1;
        int unCheck = 0;
        Set<Integer> statusSet = new HashSet<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonNode ob = jsonArray.get(i);
            if (ob == null) {
                continue;
            }
            Long total = JsonNodeUtil.getLong(ob, HdListAccess.hdTotalSpace.getOriginCode());
            if (total != null && total != -1) {
                if (totalHD == null) {
                    totalHD = total;
                } else {
                    totalHD = totalHD + total;
                }
            }
            Long free = JsonNodeUtil.getLong(ob, HdListAccess.hdFreeSpace.getOriginCode());
            if (free != null && free != -1) {
                if (totalFreeHD == null) {
                    totalFreeHD = free;
                } else {
                    totalFreeHD = totalFreeHD + free;
                }
            }
            Long used = JsonNodeUtil.getLong(ob, HdListAccess.hdUsedSpace.getOriginCode());
            if (used != null && used != -1) {
                if (totalUsedHD == null) {
                    totalUsedHD = used;
                } else {
                    totalUsedHD = totalUsedHD + used;
                }
            }
            // 0是正常,-1是未检测,有一个异常则异常,所有未见检测才未检测,所有正常才正常
            // 正常、smart状态、休眠 都算做正常，其他算作异常--12-26
            Integer stat = JsonNodeUtil.getInteger(ob, HdListAccess.hdStatus.getOriginCode());
            if (stat != null) {
                if (stat != 0 && stat != -1 && stat != 3 && stat != 5) {
                    hdStatus = 0;
                    statusSet.add(stat);
                } else if (stat == -1) {
                    unCheck++;
                } else if (stat == 0 || stat == 3 || stat == 5) {
                    // 有一个异常则异常
                    if (hdStatus == 0) {
                        continue;
                    }
                    hdStatus = 1;
                }
            }
        }
        dealStatus(status, hdStatus, unCheck, jsonArray.size(), totalHD, totalUsedHD, totalFreeHD, jsonArray, statusSet);
    }


    @Override
    public void analysis(List<HdStatus> hdStatusList, Map<String, Object> status) {
        if (CollectionUtils.isEmpty(hdStatusList)) {
            return;
        }
        Long totalHD = null;
        Long totalFreeHD = null;
        Long totalUsedHD = null;
        int hdStatus = -1;
        int unCheck = 0;
        Set<Integer> statusSet = new HashSet<>();
        for (HdStatus hd : hdStatusList) {
            if (hd == null) {
                continue;
            }
            Long total = hd.getHdTotalSpace();
            if (total != null && total != -1) {
                if (totalHD == null) {
                    totalHD = total;
                } else {
                    totalHD = totalHD + total;
                }
            }
            Long free = hd.getHdFreeSpace();
            if (free != null && free != -1) {
                if (totalFreeHD == null) {
                    totalFreeHD = free;
                } else {
                    totalFreeHD = totalFreeHD + free;
                }
            }
            Long used = hd.getHdUsedSpace();
            if (used != null && used != -1) {
                if (totalUsedHD == null) {
                    totalUsedHD = used;
                } else {
                    totalUsedHD = totalUsedHD + used;
                }
            }
            // 0是正常,-1是未检测,有一个异常则异常,所有未见检测才未检测,所有正常才正常
            Integer stat = hd.getHdStatus();
            if (stat != null) {
                if (stat != 0 && stat != -1 && stat != 3 && stat != 5) {
                    hdStatus = 0;
                    statusSet.add(stat);
                } else if (stat == -1) {
                    unCheck++;
                } else if (stat == 0 || stat == 3 || stat == 5) {
                    // 有一个异常则异常
                    if (hdStatus == 0) {
                        continue;
                    }
                    hdStatus = 1;
                }
            }
        }
        dealStatus(status, hdStatus, unCheck, hdStatusList.size(), totalHD, totalUsedHD, totalFreeHD, hdStatusList, statusSet);
    }

    private void dealStatus(Map<String, Object> status, int hdStatus, int unCheck, int size, Long totalHD, Long totalUsedHD, Long totalFreeHD, Object hdStatusList, Set<Integer> statusSet) {
        // 1.磁盘异常
        if (hdStatus == 0) {
            status.put(HdListAccess.hdStatus.getHospCode(), 0);
        }
        // 2.未检测
        else if (hdStatus == -1 || unCheck == size) {
            status.put(HdListAccess.hdStatus.getHospCode(), -1);
        }
        // 3.正常
        else {
            status.put(HdListAccess.hdStatus.getHospCode(), 1);
        }
        DecimalFormat df = new DecimalFormat(SIX_DECIMAL_ABBREVIATIONS);
        if (totalUsedHD == null && totalHD != null && totalFreeHD != null && totalHD.intValue() >= 0 && totalFreeHD.intValue() >= 0) {
            totalUsedHD = totalHD - totalFreeHD;
        }
        if (totalFreeHD == null && totalHD != null && totalUsedHD != null && totalHD.intValue() >= 0 && totalUsedHD.intValue() >= 0) {
            totalFreeHD = totalHD - totalUsedHD;
        }
        if (totalHD != null && totalHD.intValue() >= 0) {
            status.put(HdListAccess.hdTotalSpace.getHospCode(), Float.parseFloat(df.format(totalHD.doubleValue())));
        }
        if (totalFreeHD != null && totalFreeHD.intValue() >= 0) {
            status.put(HdListAccess.hdFreeSpace.getHospCode(), Float.parseFloat(df.format(totalFreeHD.doubleValue())));
        }
        if (totalUsedHD != null && totalUsedHD.intValue() >= 0) {
            status.put(HdListAccess.hdUsedSpace.getHospCode(), Float.parseFloat(df.format(totalUsedHD.doubleValue())));
        }
        if (totalHD != null && totalUsedHD != null && totalHD.intValue() >= 0 && totalUsedHD.intValue() >= 0) {
            computeUsageDouble(totalUsedHD.doubleValue(), totalHD.doubleValue(), HdListAccess.hdUsage.getHospCode(), status);
        } else if (totalHD != null && totalFreeHD != null && totalHD.intValue() >= 0 && totalFreeHD.intValue() >= 0) {
            computeUsageDouble((totalHD.doubleValue() - totalFreeHD.doubleValue()), totalHD.doubleValue(), HdListAccess.hdUsage.getHospCode(), status);
        } else {
            log.info("hdListConverter totalHD:{}", totalHD);
        }
        try {
            status.put("hdStatusCauses", objectMapper.writeValueAsString(statusSet));
        } catch (JsonProcessingException e) {
            log.error("", e);
        }
        array2JsonStr(HdListAccess.hdList.getHospCode(), hdStatusList, status);
    }


}
