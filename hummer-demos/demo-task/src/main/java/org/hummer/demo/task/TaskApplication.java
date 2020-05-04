package org.hummer.demo.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import sf.codegen.EntityEnhancerJavassist;

/**
 * @author zhujinming6
 * @create 2020-04-29 16:57
 * @update 2020-04-29 16:57
 **/
@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        //jpa实体类所在的包
        new EntityEnhancerJavassist().enhance("org.hummer");
        SpringApplication.run(TaskApplication.class, args);
    }

}
