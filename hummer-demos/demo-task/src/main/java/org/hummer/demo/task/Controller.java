package org.hummer.demo.task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class Controller {

    @GetMapping("/123")
    public String test(){
        return "1";
    }
}
