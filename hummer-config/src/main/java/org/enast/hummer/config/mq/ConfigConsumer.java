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
//public class ConfigConsumer extends AbstractSpringNotifyConsumer {
//
//    private static final Logger log = LoggerFactory.getLogger(ConfigConsumer.class);
//
//    private UnifyConfigService configService;
//
//    public ConfigConsumer(SpringNotifyConnectionFactory factory, String destionName, boolean isPersistent, UnifyConfigService configService) {
//        super.setSpringNotifyConnectionFactory(factory);
//        super.setDestionName(destionName);
//        super.setIsTopic(true);
//        super.setIsPersistent(isPersistent);
//        this.configService = configService;
//    }
//
//    /**
//     * {"data":"{\"data\":{\"ids\":[{\"componentId\":\"nms\",\"serviceNodeCode\":\"4cc19616-40cf-4107-a7ba-53ad924bbd4a\",\"serviceType\":\"nms-cmdb\"}]},\"operate\":\"update\",\"type\":\"service\"}"}
//     *
//     * @param message
//     * @throws NotifyException
//     */
//    @Override
//    public void onMessage(TextNotifyMessage message) throws NotifyException {
//        log.info("ConfigConsumer", "message")), message);
//    }
//}
