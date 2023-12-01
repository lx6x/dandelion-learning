package org.dandelion.start.example;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * @author lx6x
 * @date 2023/11/28
 */
@Component
public class ProducerPostConstruct {

    @PostConstruct
    public void init(){
        System.out.println("PostConstruct init");
    }
}
