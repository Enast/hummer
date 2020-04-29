package org.hummer.task.common;

import com..hik.crypt.Authentication;
import com..hik.crypt.CryptErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;

/*
 * 核心服务相关类
 *@auther shixiafeng
 *@create 2017-11-13
 */
@Service
@EnableConfigurationProperties(TaskBicProperties.class)
public class TaskBicUtils {
    private static Logger log = LoggerFactory.getLogger(TaskBicUtils.class);

    @Autowired
    private TaskBicProperties taskBicProperties;

    /**
     * 是否需要bic验证
     */
    public static boolean need = false;
    /**
     * 校验token
     */
    public static String token = "";

    @PostConstruct
    public void init() {
        need = taskBicProperties.isNeed();
        token = taskBicProperties.getToken();
    }

    /**
     * 生成身份标记，Base64编码
     *
     * @param content 需要标记的内容
     * @return 身份标记
     */
    public static String generateToken(String content, boolean need) {
        String token = "";
        if (need) {
            try {
                token = new String(Base64.getEncoder().encode(Authentication.identifyApply(content)), "UTF-8");
            } catch (Exception e) {
                log.error("[ generateToken ] error", e);
                return null;
            }
        } else {
            token = TaskBicUtils.token;
        }
        return token;
    }

    public static String generateToken(String content) {
        return generateToken(content, need);
    }

    private final static String CHECK_SUCCESS = "0"; // 校验成功结果码


    /**
     * 校验身份标记，标记为{@code Base64}编码字符串
     *
     * @param token   身份标记
     * @param content 标记的内容
     * @return true：身份校验合法，false：身份校验不合法
     */
    public static boolean validateToken(String token, String content) {
        if (need) {
            if (StringUtils.isBlank(token)) {
                log.warn("Rest Authentication validate failed, input token is blank.");
                return false;
            }
            try {
                String result = Authentication.identifyCheck(Base64.getDecoder().decode(token), content);
                return StringUtils.equals(CHECK_SUCCESS, result);
            } catch (IllegalArgumentException e) {
                log.error("Rest Authentication end with invalid token ", e);
                return false;
            } catch (CryptErrorException e) {
                log.error("Rest Authentication validate error ", e);
                return false;
            }
        } else {
            return true;
        }
    }
}
