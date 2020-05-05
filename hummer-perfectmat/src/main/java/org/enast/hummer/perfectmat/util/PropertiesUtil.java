package org.enast.hummer.perfectmat.util;

import org.apache.commons.lang3.StringUtils;
import org.enast.hummer.common.TimeUtils;
import org.enast.hummer.perfectmat.entity.BusinessType;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.enast.hummer.perfectmat.entity.ResourceCache;
import org.enast.hummer.perfectmat.entity.ResourceQuotas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2018-07-06 12:57
 * @update 2018-07-06 12:57
 **/
public class PropertiesUtil {

    private static final String STATUS_LOCAL_KEY_SUFFIX = "CollectTimeLocal";
    private static final String STATUS_DIFF_KEY_SUFFIX = "CollectTimeDiff";
    private static final String STATUS_UTC_KEY_SUFFIX = "CollectTimeUTC";

    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    public static void fillDstTime(Date date, ResourceQuotas quotas) {
        try {
            if (date == null) {
                return;
            }
            String collectTimeISO = TimeUtils.date2Str(date, TimeUtils.IOS8601_XXX);
            String businessType = quotas.getBusinessType().getCode();
            String localTime =getLocalTime(collectTimeISO);
            String timeDiff = getTimeDiff(collectTimeISO);
            Map<String, Object> map = quotas.getQuotas();
            quotas.setCollectTimeLocal(localTime);
            quotas.setCollectTimeDiff(timeDiff);
            Date localDate = TimeUtils.str2DateIOS8601(localTime);
            if (localDate != null) {
                map.put(businessType + STATUS_LOCAL_KEY_SUFFIX, localDate.getTime());
            }
            map.put(businessType + STATUS_DIFF_KEY_SUFFIX, timeDiff);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static String getTimeDiff (String isoTimeStr) {
        if (StringUtils.isEmpty(isoTimeStr)) {
            return StringUtils.EMPTY;
        }

        //2018-10-30T17:03:44.964+08:00
        if (isLegalDate(isoTimeStr, DateFormat.yyyy_MM_dd_T_HH_mm_ss_SX)) {
            return isoTimeStr.substring(isoTimeStr.length()-6);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据ISO时间字符串获取本地时间
     */
    public static String getLocalTime(String isoTimeStr) {
        if (StringUtils.isEmpty(isoTimeStr)) {
            return StringUtils.EMPTY;
        }
        //2018-10-30T17:03:44.964+08:00
        if (isLegalDate(isoTimeStr, DateFormat.yyyy_MM_dd_T_HH_mm_ss_SX)) {
            return isoTimeStr.substring(0, isoTimeStr.length()-6);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 判断字符串是否符合指定格式
     *
     * @param timeStr
     * @param df
     */
    public static boolean isLegalDate(String timeStr, DateFormat df) {
        SimpleDateFormat formatter = new SimpleDateFormat(df.getValue());
        try {
            Date date = formatter.parse(timeStr);
            formatter.format(date);
        } catch (Exception e) {
            // ignore
            return false;
        }
        return true;
    }

    /**
     * @return
     * @author zhujinming6
     * @create_date 2019/11/23 15:02
     * @modify_date 2019/11/23 15:02
     */
    public static ResourceQuotas resourceQuota(AccessData accessData, ResourceCache simpleVO) {
        // 1.解析报文头
        String businessType = accessData.getBusinessType();
        // 2.报文采集时间
        Date collectTime = accessData.getCollectTime();
        if (collectTime == null) {
            log.info("collectTime is null :{},{}", businessType, simpleVO.getResId());
            return null;
        }
        // 用来存储 解析的状态(静态)数据
        Map<String, Object> status = new HashMap<>();
        BusinessType pafBusinessType = BusinessType.code4(businessType);
        if (pafBusinessType == null) {
            log.info("unknown pafBusinessType :{},{}", businessType, simpleVO.getResId());
            return null;
        }
        ResourceQuotas quotas = new ResourceQuotas(simpleVO, pafBusinessType, collectTime, status);
        return quotas;
    }

    /**
     * 日期格式定义
     *
     */
    public enum DateFormat {

        /**
         * yyyy-MM-dd
         */
        yyyy_MM_dd("yyyy-MM-dd"),

        /**
         * yyyy-MM-dd HH:mm
         */
        yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),

        /**
         * yyyy-MM-dd HH:mm:ss
         */
        yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),

        /**
         * 本地ISO时间格式
         * <br>例如： 2018-10-30T17:03:44
         */
        yyyy_MM_dd_T_HH_mm_ss("yyyy-MM-dd'T'HH:mm:ss"),

        /**
         * 本地ISO时间格式
         * <br>例如： 2018-10-30T17:03:44.964
         */
        yyyy_MM_dd_T_HH_mm_ss_S("yyyy-MM-dd'T'HH:mm:ss.SSS"),

        /**
         * 带时区ISO时间格式
         * <br>例如： 2018-10-30T17:03:44.964+08:00
         */
        yyyy_MM_dd_T_HH_mm_ss_SX("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),

        /**
         * 带时区UTC时间格式
         * <br>例如： 2018-10-30 17:03:44+08:00
         */
        yyyy_MM_dd_HH_mm_ss_SX("yyyy-MM-dd HH:mm:ssXXX"),

        /**
         * 带时区ISO时间格式
         * <br>例如： 2018-10-30T17:03:44+08:00
         */
        yyyy_MM_dd_T_HH_mm_ss_SX2("yyyy-MM-dd'T'HH:mm:ssXXX"),

        yyyy_MM_dd_00_00_00("yyyy-MM-dd 00:00:00"),

        /**
         * yyyyMMdd
         */
        yyyyMMdd("yyyyMMdd"),

        /**
         * yyyyMM
         */
        yyyyMM("yyyyMM"),

        /**
         * yyyy-MM
         */
        yyyy_MM("yyyy-MM"),

        /**
         * MM-dd
         */
        MM_dd("MM-dd"),

        /**
         * MMdd
         */
        MMdd("MMdd"),

        HH("HH"),

        /**
         * yyyyMMddHHmmss
         */
        yyyyMMddHHmmss("yyyyMMddHHmmss"),

        /**
         * 对外接口中使用
         */
        yyyy_MM_dd_HH_mm_ss_api("yyyy/MM/dd HH:mm:ss");

        private final String value;

        DateFormat(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
