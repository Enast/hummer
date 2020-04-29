package org.hummer.config.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hummer.config.config.UnifyConfigProperties;
import org.hummer.config.event.ConfigChangeEvent;
import org.hummer.config.service.UnifyConfigService;
import com..doraemon.common.nbf.conf.NbConfig;
import com..doraemon.common.util.StringUtils;
import com..notify.connection.NotifyConnection;
import com..notify.connection.NotifyConnectionFactory;
import com..notify.consumer.NotifyConsumer;
import com..notify.data.NotifyMessage;
import com..notify.destination.MessageDestination;
import com..notify.exception.NotifyException;
import com..notify.listener.NotifyMessageListener;
import com..notify.spring.factory.SpringNotifyConnectionFactory;
import log.HikLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 配置初始化类
 *
 * @author zhujinming6
 * @create 2019-10-18 17:11
 * @update 2019-10-18 17:11
 **/
@Component
@EnableConfigurationProperties(UnifyConfigProperties.class)
public class UnifyConfigInitService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UnifyConfigInitService.class);

    @Autowired(required = false)
    @Qualifier("mqFactory")
    SpringNotifyConnectionFactory factory;

    @Resource
    UnifyConfigService unifyConfigService;
    @Resource
    NbConfig nbConfig;
    @Resource
    UnifyConfigProperties unifyConfigProperties;
    @Autowired
    ApplicationContext context;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {unifyConfigService.resetAllProperties();}).start();
        if (unifyConfigProperties == null || StringUtils.isBlank(unifyConfigProperties.getComponentId()) || StringUtils.isBlank(unifyConfigProperties.getSegmentId())) {
            log.info("unifyConfigProperties is not config"));
            return;
        }
        new Thread(() -> {
            NotifyConnectionFactory connectionFactory = new NotifyConnectionFactory();
            NotifyConnection connection = null;
            String agentNo = nbConfig.get("@agent.agentNo", null);
            if (StringUtils.isBlank(agentNo)) {
                log.info("@agent.agentNo is null"));
                return;
            }
            try {
                connection = connectionFactory.createNotifyConnection();
                connection.start();
                MessageDestination destination = new MessageDestination(agentNo + "." + unifyConfigProperties.getComponentId()
                        + "." + unifyConfigProperties.getSegmentId() + ".topic." + unifyConfigProperties.getIndex(), true);
                log.info(JSON.toJSONString(destination)));
                NotifyConsumer configConsumer = connection.createConsumer(destination);
                configConsumer.receive(new NotifyMessageListener() {
                    @Override
                    public void onMessage(NotifyMessage message) throws NotifyException {
                        String msgStr = JSON.toJSONString(message);
                        log.info("configConsumer:{}", "msgStr")), msgStr);
                        //获取消息基本信息
                        JSONObject jsonObj = JSON.parseObject(msgStr, JSONObject.class);
                        if (jsonObj == null) {
                            return;
                        }
                        String type = jsonObj.getString("type");
                        if ("local_config".equals(type)) {
                            // 生成一个配置变更事件
                            context.publishEvent(new ConfigChangeEvent(type));
                            unifyConfigService.resetAllProperties();
                        }
                    }
                });
                log.info("start configConsumer "));
            } catch (NotifyException e) {
                log.error("", e);
            }
        }).start();
    }
}
