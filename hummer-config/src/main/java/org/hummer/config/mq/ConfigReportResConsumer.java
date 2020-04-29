//package org.hummer.config.mq;
//
//import org.hummer.config.service.UnifyConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 运管配置变更消费者
// *
// * @author zhujinming6
// * @create 2019-10-18 17:08
// * @update 2019-10-18 17:08
// **/
//public class ConfigReportResConsumer extends AbstractSpringNotifyConsumer {
//
//    private static final Logger log = LoggerFactory.getLogger(ConfigReportResConsumer.class);
//
//    private UnifyConfigService configService;
//
//    public ConfigReportResConsumer(SpringNotifyConnectionFactory factory, String destionName, boolean isPersistent, UnifyConfigService configService) {
//        super.setSpringNotifyConnectionFactory(factory);
//        super.setDestionName(destionName);
//        super.setIsTopic(true);
//        super.setIsPersistent(isPersistent);
//        this.configService = configService;
//    }
//
//    @Override
//    public void onMessage(TextNotifyMessage message) throws NotifyException {
//        log.info("ConfigReportResConsumer", "message")), message);
//    }
//}
