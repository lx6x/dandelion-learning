# dandelion-parent

日常学习使用，某些模块可能会不全，很杂乱，想到就补到，用到补到

## 模块结构

```
dandelion-parent
├── dandelion-arithmetic                          算法实现
├── dandelion-bigdata                             大数据（hive hdfs 操作等）
├── dandelion-commons                             公共模块
├── dandelion-data                                数据模块
|   ├── dandelion-data-jdbc                       原生jdbc
|   ├── dandelion-data-jpa                        jpa
|   ├── dandelion-data-redis                      redis
|   ├── dandelion-data-routing-source             数据源动态切换
|   ├── dandelion-data-sharding-jdbc              sharding-jdbc
├── dandelion-design-patterns                     23种设计模式
├── dandelion-doc                                 api 文档
|   ├── dandelion-doc-knife4j                     Knife4j
|   ├── dandelion-doc-smart                       Smart
|   ├── dandelion-doc-swagger                     Swagger
├── dandelion-easyexcel                           easyexecl 工具包使用
├── dandelion-feignclient                         feignclient
├── dandelion-flowable                            工作流实现（flowable-引擎）
├── dandelion-limiter                             限流（aop+redis）
├── dandelion-mybatis-generator                   mybatis 代码生成器
├── dandelion-netty                               netty 
|   ├── dandelion-netty-beat                      使用netty发送心跳
|   |   ├── dandelion-netty-beat-server           使用netty发送心跳 测试服务端     
|   |   ├── dandelion-netty-beat-client           使用netty发送心跳 测试客户端
├── dandelion-onjava                              ON JAVA 中文版 代码验证
├── dandelion-oshi                                oshi 获取主机cpu/内存/系统 ... 资源信心
├── dandelion-oss                                 oss 服务 （阿里）
├── dandelion-repeat-submit                       重复提交验证（redis实现）
├── dandelion-scheduling                          任务调度
|   ├── dandelion-kafka                           kafka 消息
|   ├── dandelion-scheduling-quartz               quartz 定时任务
|   ├── dandelion-scheduling-rabbitmq             rabbitmq 消息
|   ├── dandelion-scheduling-xxl-job              xxl-job 定时任务
|   ├── dandelion-scheduling-rocketmq             rocketmq 消息
|   ├── dandelion-scheduling-spring               
├── dandelion-search                              搜索引擎 Lucene/elasticsearch 使用
|   ├── dandelion-search-lucene                   lucene 查询
|   ├── dandelion-search-elasticsearch            elasticsearch 使用
├── dandelion-security                            SpringBoot-Security安全框架（2.3.2）
|   ├── dandelion-security-init                   初始
|   ├── dandelion-security-jwt                    jwt使用
|   ├── dandelion-security-jwt-auth               jwt+auth使用
├── dandelion-thread                              线程
├── dandelion-ureport2                            图表工具
```




## 所有模块服务端口

```
dandelion-scheduling-quartz-a                               30000
dandelion-scheduling-quartz-b                               30001
dandelion-scheduling-xxl-job                                30002
dandelion-kafka                                             30003
dandelion-commons-swagger                                   30004
dandelion-domino-ui-server                                  30005
dandelion-domino-ui-client                                  8888 [注：默认端口运行可修改]
dandelion-doc-smart-01                                      30006
smart-multi-01                                              30007
dandelion-test                                              30008
dandelion-oss                                               30009
dandelion-thread                                            30010
dandelion-dandelion-feignclient-consumer                    30011
dandelion-feignclient-producer                              30012
dandelion-data-redis                                        30013
dandelion-security-init                                     30014
dandelion-data-jpa                                          30015
dandelion-security-jwt                                      30016
dandelion-security-jwt-auth                                 30017
dandelion-data-shardingsphere-jdbc                          30018
dandelion-netty-im-server                                   30019-30020 （多个 server 测试使用）
dandelion-netty-im-routing                                  30021
dandelion-netty-im-client                                   30022
dandelion-limiter                                           30023
dandelion-data-routing-source                               30024
dandelion-flowable                                          30025
dandelion-repeat-submit                                     30026
dandelion-oshi                                              30027
dandelion-search-lucene                                     30028
dandelion-netty-beat-server                                 30029
dandelion-netty-beat-client                                 30030
dandelion-scheduling-rocketmq                               30032
dandelion-search-elasticsearch                              30033
dandelion-doc-knife4j                                       30034
dandelion-easyexcel                                         30035
dandelion-scheduling-spring                                 30036
dandelion-scheduling-rabbitmq                               30037
```



