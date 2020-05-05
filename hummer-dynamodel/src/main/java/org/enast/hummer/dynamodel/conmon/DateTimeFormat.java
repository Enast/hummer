package org.enast.hummer.dynamodel.conmon;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public enum DateTimeFormat {
	
	/** 12:12:12 */
	TIME("1", "", "HH:mm:ss", 1000),
	/** 12:12 */
	TIME_HHMM("2", "", "HH:mm", 60 * 1000),
	/** 2017-1-31*/
	DATE("3", "date", "yyyy-MM-dd", 1000),
	/** 2017/1/31*/
	DATE_EN("4", "date", "yyyy/MM/dd", 1000),
	/** 2017年1月31日 */
	DATE_CHS("5", "date", "yyyy年MM月dd日", 1000),
	/** 2017年1月 */
	DATE_CHS_YYYYMM("6", "month", "yyyy年MM月", 1000),
	/** 1月31日 */
	DATE_CHS_MMDD("7", "month", "MM月dd日", 1000),
	/** 2017-1-31 12:12:12 */
	DATETIME("8", "datetime", "yyyy-MM-dd HH:mm:ss", 1000),
	/** 2017-1-31 12:12:12 */
	DATETIME_EN("9", "datetime", "yyyy/MM/dd HH:mm:ss", 1000),
	/** 2017年1月31日 12日12分12秒 */
	DATETIME_CHS("10", "datetime", "yyyy年MM月dd日 HH时mm分ss秒", 1000);
	
	/** 日期格式列表 */
	public static List<DateTimeFormat> DateFormats = Arrays.asList(DATE, DATE_EN, DATE_CHS, DATE_CHS_YYYYMM, DATE_CHS_MMDD, DATE_CHS_MMDD,
			DATETIME, DATETIME_EN, DATETIME_CHS);
	/** 时间格式列表 */
	public static List<DateTimeFormat> TimeFormats = Arrays.asList(TIME, TIME_HHMM);
	
	private DateTimeFormat(String code, String type, String template, long threshold) {
		this.code = code;
		this.type = type;
		this.template = template;
		this.format = new SimpleDateFormat(template);
		this.threshold = threshold;
	}
	
	private String code;
	/** 前端日期插件type */
	private String type;
	private String template;
	private SimpleDateFormat format;
	/** 传入的毫秒数阈值 */
	private long threshold;
	
	public String getCode() {
		return code;
	}
	
	public String getType() {
		return type;
	}

	public String getTemplate() {
		return template;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}
	
	public long getThreshold() {
		return threshold;
	}
	
	public static DateTimeFormat getByCode(String code) {
		for (DateTimeFormat d : values()) {
			if (d.code.equals(code)) {
				return d;
			}
		}
		//默认DATE枚举
		return DATE;
	}

	public static DateTimeFormat getByTemplate(String template) {
		for (DateTimeFormat d : values()) {
			if (d.template.equals(template)) {
				return d;
			}
		}
		//默认DATE枚举
		return DATE;
	}

}
