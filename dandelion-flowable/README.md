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

## 流程从模型创建开始到执行任务的一般过程
```text
1. 模型创建：使用Flowable提供的流程建模工具（如Flowable Modeler）或BPMN 2.0兼容的建模工具创建业务流程模型。在模型中定义流程的各个节点、连线、任务、网关、事件等。
2. 部署流程定义：将流程模型部署到Flowable引擎中。通过将BPMN 2.0 XML文件上传到Flowable引擎并进行部署，将流程模型转换为可执行的流程定义。
3. 启动流程实例：使用Flowable API启动流程实例。通过Flowable的 RuntimeService 对象，使用流程定义的key或ID来启动流程实例。启动流程实例后，将会创建一个流程实例，并从流程的开始节点开始执行。
4. 执行任务：在流程实例执行过程中，当到达任务节点时，流程会停下来等待相关用户或系统操作员来执行任务。任务可以是用户任务（需要人工干预）或服务任务（由系统自动执行）。
5. 完成任务：执行任务的操作员（用户或系统）根据任务的要求完成任务，并使用Flowable API中的 TaskService 对象将任务标记为已完成。这将触发流程继续向下执行。
6. 流程继续执行：一旦任务被标记为已完成，流程将继续执行下一个节点。根据流程模型中定义的条件、连线和网关，流程将决定下一个要执行的节点。
7. 循环执行任务：流程可能会多次经过任务节点的循环，直到满足流程终止的条件。循环可以是基于条件、计数器或事件触发的。
8. 流程结束：当流程达到终止条件时，流程将结束。此时，可以根据需要进行相关的记录、报告或其他后续操作。
请注意，上述过程是一个典型的流程执行过程，实际情况可能因业务需求、流程模型的复杂性以及使用的Flowable版本而有所不同。您可以根据具体的业务流程和要求来调整和扩展上述过程。
```
 
## 集成Api

## 常用Api接口及其描述

RepositoryService：用于管理流程定义和流程部署的服务接口。

* createDeployment()：创建一个流程部署对象。
* deploy()：部署流程定义。
* deleteDeployment()：删除流程部署。
* createProcessDefinitionQuery()：创建流程定义查询对象。

RuntimeService：用于管理运行时流程实例和执行流的服务接口。
* startProcessInstanceByKey()：根据流程定义的key启动流程实例。
* createExecutionQuery()：创建执行流查询对象。
* signalEventReceived()：触发流程实例的信号事件。

TaskService：用于管理任务的服务接口。

* createTaskQuery()：创建任务查询对象。
* complete()：完成任务。
* claim()：领取任务。
* delegateTask()：委派任务给其他用户。

IdentityService：用于管理用户、组和身份验证的服务接口。

* createUser()：创建用户。
* createGroup()：创建组。
* getUser()：根据用户ID获取用户信息。
* createMembership()：将用户添加到组中。

HistoryService：用于管理流程实例和任务历史数据的服务接口。

* createHistoricProcessInstanceQuery()：创建历史流程实例查询对象。
* createHistoricTaskInstanceQuery()：创建历史任务查询对象。
* createHistoricActivityInstanceQuery()：创建历史活动查询对象。

FormService：用于管理表单数据的服务接口。

* getRenderedStartForm()：获取启动表单的HTML渲染结果。
* submitStartFormData()：提交启动表单数据。

