package org.hummer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;

/*
* 工具类
 *@auther shixiafeng
 *@create 2017-11-18
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static <T> HttpEntity<T> getHttpEntity() {
        return getHttpEntity(null);
    }

    public static <T> HttpEntity<T> getHttpEntity(T t) {
        HttpHeaders hh = getHttpHeaders();
        hh.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<T> httpEntity = new HttpEntity<>(t, hh);
        return httpEntity;
    }

    public static HttpEntity<MultiValueMap<String, String>> getFormUrlHttpEntity() {
        return getFormUrlHttpEntity(null);
    }

    public static HttpEntity<MultiValueMap<String, String>> getFormUrlHttpEntity(MultiValueMap<String, String> map) {
        HttpHeaders hh = getHttpHeaders();
        hh.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, hh);
        return httpEntity;
    }


    public static HttpEntity<MultiValueMap<String, ?>> getFormDataHttpEntity() {
        return getFormDataHttpEntity(null);
    }

    public static HttpEntity<MultiValueMap<String, ?>> getFormDataHttpEntity(MultiValueMap<String, ?> map) {
        HttpHeaders hh = getHttpHeaders();
        hh.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, ?>> httpEntity = new HttpEntity<>(map, hh);
        return httpEntity;
    }

    public static HttpHeaders getHttpHeaders() {
        HttpHeaders hh = new HttpHeaders();
        hh.setAcceptLanguageAsLocales(Arrays.asList(Locale.CHINESE, Locale.CHINA));
        //加入bic校验头
//        hh.set("Token", TaskBicUtils.generateToken(null));
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request != null) {
                //获取header信息
                String cookie = request.getHeader(HttpHeaders.COOKIE);
                if (cookie != null) {
                    hh.set(HttpHeaders.COOKIE, cookie);
                    log.info("Set cookie:" + cookie);
                }
            }
        }
        return hh;
    }
}
