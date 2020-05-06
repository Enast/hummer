package org.enast.hummer.cluster.consul.config;

import org.enast.hummer.cluster.core.aop.impl.ClusterMasterAspect;
import org.enast.hummer.cluster.core.aop.impl.DistributeLockAspect;
import org.enast.hummer.cluster.consul.service.ConsulElectService;
import org.enast.hummer.cluster.core.service.MasterService;
import org.enast.hummer.cluster.consul.service.impl.ConsulElectServiceImpl;
import org.enast.hummer.cluster.core.service.impl.MasterServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * @author zhujinming6
 * @create 2020-04-30 12:55
 * @update 2020-04-30 12:55
 **/
public class ClusterAutoConfiguration {

    @Bean
    ConsulElectService consulElectService() {
        return new ConsulElectServiceImpl();
    }

    @Bean
    MasterService masterService() {
        return new MasterServiceImpl();
    }

    @Bean
    DistributeLockAspect distributeLockAspect() {
        return new DistributeLockAspect();
    }

    @Bean
    ClusterMasterAspect clusterMasterAspect() {
        return new ClusterMasterAspect();
    }

}
