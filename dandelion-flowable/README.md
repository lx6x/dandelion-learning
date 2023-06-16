Flowable 流程引擎使用
===

官方示例文档：[Flowable](https://www.flowable.com/open-source/docs/bpmn/ch05a-Spring-Boot)

中文文档：[V6.3.0](https://tkjohn.github.io/flowable-userguide/#_introduction)

Github: [flowable-engin](https://github.com/flowable/flowable-engine)

## SpringBoot 集成 Flowable-ui

下载 [flowable-6.8.0](https://github.com/flowable/flowable-engine/releases/tag/flowable-6.8.0)

源码解压后 flowable-ui 下执行 ***maven clear install***

新建 springboot 项目

pom.xml 配置

```
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter-ui-modeler</artifactId>
    <version>6.8.0</version>
</dependency>

<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter-ui-idm</artifactId>
    <version>6.8.0</version>
</dependency>
```

application.yml 配置

```yaml
server:
  port: 30025
spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/flowable?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
flowable:
  #  关闭定时任务JOB
  async-executor-activate: false
  #  自动更新数据库
  database-schema-update: false
  #  校验流程文件，默认校验resources下的processes文件夹里的流程文件
  process-definition-location-prefix: classpath*:/processes/
  #  指定流程定义文件的后缀名。
  process-definition-location-suffixes: "**.bpmn20.xml, **.bpmn"
  database-schema: flowable
  common:
    app:
      idm-url: http://localhost:30025/flowable-idm
      idm-admin:
        user: admin
        password: test
```

> 启动访问 <http://localhost:30025/modeler/#/processes>
