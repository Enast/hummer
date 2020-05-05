package org.hummer.task.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sf.codegen.EntityEnhancerJavassist;

/**
 * @author zhujinming6
 * @create 2020-04-29 16:57
 * @update 2020-04-29 16:57
 **/
@SpringBootApplication
public class TaskServerApplication {

    public static void main(String[] args) {
        //jpa实体类所在的包
        new EntityEnhancerJavassist().enhance("org.hummer.task.server.model");
        SpringApplication.run(TaskServerApplication.class, args);
    }

}
