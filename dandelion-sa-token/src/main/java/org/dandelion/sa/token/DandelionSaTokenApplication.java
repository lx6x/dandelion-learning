package org.dandelion.sa.token;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dandelion.sa.token.domain.mapper")
public class DandelionSaTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DandelionSaTokenApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

}
