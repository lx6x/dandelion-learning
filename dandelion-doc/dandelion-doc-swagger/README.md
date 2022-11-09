# dandelion-commons-swagger

## 模块结构

```
    ../dandelion-commons-swagger
        .../config              swagger配置文件
            ..../plugin         swagger插件配置
            ..../valid          注解配置
        .../controller          接口验证测试
        .../parameters          实体测试  
```

## 模块服务端口

```
    dandelion-commons-swagger      30004 
```

## pom 引入
```
    <!--集成Swagger start-->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.9.2</version>
        <exclusions>
            <exclusion>
                <groupId>io.swagger</groupId>
                <artifactId>swagger-annotations</artifactId>
            </exclusion>
            <exclusion>
                <groupId>io.swagger</groupId>
                <artifactId>swagger-models</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.9.2</version>
    </dependency>
    <!--整合Knife4j ui美化 knife4j中自带 swagger-annotations 和 swagger-models -->
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-spring-boot-starter</artifactId>
        <version>2.0.4</version>
    </dependency>
    <!--集成Swagger end-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
```

## 版本

```
 1.0      初始版本
```

## 参考地址
文档地址：https://mp.weixin.qq.com/s/w5-wJbq38mmt0312fqlJJA  
GitHub地址：https://github.com/jianzh5/cloud-blog/
