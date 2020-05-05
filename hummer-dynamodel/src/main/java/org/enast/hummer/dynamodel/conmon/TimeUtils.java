package org.enast.hummer.dynamodel.conmon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.JSONScanner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhujinming6
 * @create 2017-12-20 9:18
 * @update 2017-12-20 9:18
 **/
public class TimeUtils {

    public static TimeZone defaultTimeZone = TimeZone.getDefault();
    public static Locale defaultLocale = Locale.getDefault();
    public static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final int sixty = 60;
    private static final int day = 24;
    private static final String dayStr = "天";
    private static final String secStr = "秒";
    private static final String minStr = "分钟";
    private static final String hourStr = "小时";
    private static final String dayEn = " Days ";
    private static final String secEn = " Seconds ";
    private static final String minEn = " Minutes ";
    private static final String hourEn = " Hours ";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    static Logger log = LoggerFactory.getLogger(TimeUtils.class);
    /**
     * //2013-10-24T11:39:00.000+0800
     * DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
     * //2013-10-24T11:39:00.000+08:00
     * DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
     */
    private static final String IOS8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String IOS8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String IOS8601_XXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String IOS8601_Z_NO_XXX = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
    private static Pattern pattern_number = Pattern.compile("\\d");

    public static String timeStr4Ms(Long ms) {
        if (ms == null) {
            return null;
        }
        return timeStr(ms / 1000);
    }

    /**
     * 秒转时间文本
     *
     * @param seconds
     * @return
     */
    public static String timeStr(Long seconds) {
        if (seconds == null) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        long min = TimeUnit.SECONDS.toMinutes(seconds);
        long sec = seconds;
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long days = TimeUnit.SECONDS.toDays(seconds);
        if (seconds <= 0) {
            res.append("小于1秒");
            return res.toString();
        }
        if (seconds < sixty) {
            return res.append(sec).append(secStr).toString();
        }
        if (seconds < sixty * sixty) {
            sec = seconds % sixty;
            if (sec == 0) {
                return res.append(min).append(minStr).toString();
            }
            return res.append(min).append(minStr).append(sec).append(secStr).toString();
        }
        if (seconds < sixty * sixty * day) {
            min = min % (sixty);
            sec = seconds - (hours * sixty * sixty + min * sixty);
            if (sec == 0 && min == 0) {
                return res.append(hours).append(hourStr).toString();
            }
            if (min != 0 && sec == 0) {
                return res.append(hours).append(hourStr).append(min).append(minStr).toString();
            }
            return res.append(hours).append(hourStr).append(min).append(minStr).append(sec).append(secStr).toString();
        }
        min = min % (sixty);
        hours = hours % day;
        sec = seconds - (sixty * sixty * day * days + hours * sixty * sixty + min * sixty);
        if (sec == 0 && min == 0 && hours == 0) {
            return res.append(days).append(dayStr).toString();
        }
        if (sec == 0 && min == 0 && hours != 0) {
            return res.append(days).append(dayStr).append(hours).append(hourStr).toString();
        }
        if (sec == 0 && min != 0 && hours != 0) {
            return res.append(days).append(dayStr).append(hours).append(hourStr).append(min).append(minStr).toString();
        }
        return res.append(days).append(dayStr).append(hours).append(hourStr).append(min).append(minStr).append(sec).append(secStr).toString();
    }


    public static String timeStrV2(Long seconds) {
        if (seconds == null) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        long min = TimeUnit.SECONDS.toMinutes(seconds);
        long sec = seconds;
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long days = TimeUnit.SECONDS.toDays(seconds);
        if (seconds <= 0) {
            res.append("小于1秒");
            return res.toString();
        }
        if (seconds < sixty) {
            return res.append(sec).append(secStr).toString();
        }
        if (seconds < sixty * sixty) {
            return res.append(min).append(minStr).toString();
        }
        if (seconds < sixty * sixty * day) {
            min = min % (sixty);
            sec = seconds - (hours * sixty * sixty + min * sixty);
            if (sec == 0 && min == 0) {
                return res.append(hours).append(hourStr).toString();
            }
            if (min != 0 && sec == 0) {
                return res.append(hours).append(hourStr).append(min).append(minStr).toString();
            }
            return res.append(hours).append(hourStr).append(min).append(minStr).toString();
        }
        min = min % (sixty);
        hours = hours % day;
        sec = seconds - (sixty * sixty * day * days + hours * sixty * sixty + min * sixty);
        if (sec == 0 && min == 0 && hours == 0) {
            return res.append(days).append(dayStr).toString();
        }
        if (sec == 0 && min == 0 && hours != 0) {
            return res.append(days).append(dayStr).append(hours).append(hourStr).toString();
        }
        if (sec == 0 && min != 0 && hours != 0) {
            return res.append(days).append(dayStr).append(hours).append(hourStr).append(min).append(minStr).toString();
        }
        return res.append(days).append(dayStr).append(hours).append(hourStr).append(min).append(minStr).toString();
    }

    public static String timeStrEn(Long seconds) {
        if (seconds == null) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        long min = TimeUnit.SECONDS.toMinutes(seconds);
        long sec = seconds;
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long days = TimeUnit.SECONDS.toDays(seconds);
        if (seconds <= 0) {
            res.append("Less 1 seconds");
            return res.toString();
        }
        if (seconds < sixty) {
            return res.append(sec).append(secEn).toString();
        }
        if (seconds < sixty * sixty) {
            return res.append(min).append(minEn).toString();
        }
        if (seconds < sixty * sixty * day) {
            min = min % (sixty);
            sec = seconds - (hours * sixty * sixty + min * sixty);
            if (sec == 0 && min == 0) {
                return res.append(hours).append(hourStr).toString();
            }
            if (min != 0 && sec == 0) {
                return res.append(hours).append(hourEn).append(min).append(minEn).toString();
            }
            return res.append(hours).append(hourEn).append(min).append(minEn).toString();
        }
        min = min % (sixty);
        hours = hours % day;
        sec = seconds - (sixty * sixty * day * days + hours * sixty * sixty + min * sixty);
        if (sec == 0 && min == 0 && hours == 0) {
            return res.append(days).append(dayEn).toString();
        }
        if (sec == 0 && min == 0 && hours != 0) {
            return res.append(days).append(dayEn).append(hours).append(hourEn).toString();
        }
        if (sec == 0 && min != 0 && hours != 0) {
            return res.append(days).append(dayEn).append(hours).append(hourEn).append(min).append(minEn).toString();
        }
        return res.append(days).append(dayEn).append(hours).append(hourEn).append(min).append(minEn).toString();
    }


    /**
     * 取某天零点的函数
     * 返回 Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp getStartTimeOfDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return new Timestamp(day.getTime().getTime());
    }

    /**
     * 取某天末点的函数
     * 返回 Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp getEndTimeOfDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return new Timestamp(day.getTime().getTime());
    }

    /**
     * 时间格式化
     *
     * @param str
     * @param formt
     * @return
     */
    public static Date str2Date(String str, String formt) {
        if (StringUtils.isBlank(formt)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formt);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 时间格式化 UTC
     *
     * @param str
     * @param formt
     * @return
     */
    public static Date str2DateUtc(String str, String formt) {
        if (StringUtils.isBlank(formt)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formt);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @param formt
     * @return
     */
    public static String date2Str(Date date, String formt) {
        if (StringUtils.isBlank(formt) || date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formt);
        return sdf.format(date);
    }


    public static String date2Str_yyyy_MM_dd_HH_mm_ss(Date date) {
        return date2Str(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static String date2Str_yyyy_MM_dd(Date date) {
        return date2Str(date, yyyy_MM_dd);
    }

    public static String date2StryyyyMMdd(Date date) {
        return date2Str(date, yyyyMMdd);
    }

    /**
     * 取当前前一天时间的函数
     * 返回 Timestamp
     *
     * @return
     */
    public static Date getLastDay() {
        return getDay(-1);
    }

    public static Date getLastDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.add(Calendar.DATE, -1);
        return new Timestamp(day.getTime().getTime());
    }

    public static Date getLastDay_yyyy_MM_dd() {
        Date date = getLastDay();
        if (date == null) {
            return null;
        }
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return new Date(day.getTime().getTime());
    }

    /**
     * 取距离当前时间num天时间的函数
     * 返回 Timestamp
     *
     * @return
     */
    public static Date getDay(int num) {
        Calendar day = Calendar.getInstance();
        Date date = new Date();
        day.setTime(date);
        day.add(Calendar.DATE, num);
        return new Timestamp(day.getTime().getTime());
    }

    public static Date str2DateIOS8601Z(String str) {
        return str2Date(str, IOS8601_Z);
    }

    public static Date str2DateIOS8601(String str) {
        return str2Date(str, IOS8601);
    }

    public static Date str2DateIOS8601XXX(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return str2Date(str, IOS8601_XXX);
    }

    public static Long recordStr2Ss(String startTime) {
        return dateParse2SsRegExp(startTime);
    }

    public static boolean isToday(String centerRecordTime) {
        Long date = Long.parseLong(centerRecordTime);
        Date todayDate = getStartTimeOfDay(new Date());
        if (date != null && todayDate != null) {
            long t = date - todayDate.getTime();
            return t >= 0 && t < 24 * 60 * 60 * 1000;
        }
        return false;
    }

    public static long dateParse2SsRegExp(String period) {
        Matcher matcher = pattern.matcher(period);
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1)) * 3600L + Long.parseLong(matcher.group(2)) * 60 + Long.parseLong(matcher.group(3));
        } else {
            throw new IllegalArgumentException("Invalid format " + period);
        }
    }

    public static Date castToDate(Object value) {
        return castToDate(value, null);
    }

    public static Date castToDate(Object value, String format) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    return calendar.getTime();
                }
            } finally {
                dateLexer.close();
            }

            if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                strVal = strVal.substring(6, strVal.length() - 2);
            }

            if (strVal.indexOf('-') > 0 || strVal.indexOf('+') > 0) {
                if (format == null) {
                    if (strVal.length() == DEFFAULT_DATE_FORMAT.length() || (strVal.length() == 22 && DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
                        format = DEFFAULT_DATE_FORMAT;
                    } else if (strVal.length() == 10) {
                        format = "yyyy-MM-dd";
                    } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                        format = "yyyy-MM-dd HH:mm:ss";
                    } else if (strVal.length() == 29 && strVal.charAt(26) == ':' && strVal.charAt(28) == '0') {
                        format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                    } else if (strVal.length() == 23 && strVal.charAt(19) == ',') {
                        format = "yyyy-MM-dd HH:mm:ss,SSS";
                    } else {
                        format = "yyyy-MM-dd HH:mm:ss.SSS";
                    }
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
                dateFormat.setTimeZone(JSON.defaultTimeZone);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    throw new JSONException("can not cast to Date, value : " + strVal);
                }
            }
            try {
                long timeMils = Long.parseLong((String) value);
                Date date = new Date(timeMils);
                return date;
            } finally {

            }
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(TimeUtils.castToDate("2018-05-14 11:06:58"));
//        System.out.println(TimeUtils.castToDate("2019-12-18T11:23:20.808+08:00"));
//        System.out.println(TimeUtils.castToDate("2019/12/18T11:23:20.808+08:00"));
//        System.out.println(TimeUtils.castToDate("12321"));
        System.out.println(TimeUtils.castToDate("12:32:01"));
    }

    public static Date getLastHoursStartTime(long lastTime) {
        Calendar day = Calendar.getInstance();
        day.setTime(new Date(lastTime));
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }
}
