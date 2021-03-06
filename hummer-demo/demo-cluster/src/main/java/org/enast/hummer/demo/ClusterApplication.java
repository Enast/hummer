package org.enast.hummer.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhujinming6
 * @create 2020-04-29 16:57
 * @update 2020-04-29 16:57
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClusterApplication.class, args);
    }

}
