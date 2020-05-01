package org.hummer.demo;

import org.hummer.demo.cluster.ElectInitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-04-29 16:57
 * @update 2020-04-29 16:57
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
