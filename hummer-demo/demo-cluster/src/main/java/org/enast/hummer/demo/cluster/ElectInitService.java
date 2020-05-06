package org.enast.hummer.demo.cluster;

import org.enast.hummer.cluster.consul.service.ConsulElectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-04-29 19:43
 * @update 2020-04-29 19:43
 **/
@Component
public class ElectInitService implements CommandLineRunner {

    @Resource
    ConsulElectService consulElectService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        consulElectService.work("hummer");
    }
}
