# dandelion-parent -- 蒲公英

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
|   ├── dandelion-doc-smart                       smart
|   ├── dandelion-doc-swagger                     swagger
├── dandelion-excel                               excel 工具包使用
├── dandelion-feignclient                         feignclient
├── dandelion-flowable                            工作流实现（flowable-引擎）
├── dandelion-limiter                             限流（aop+redis）
├── dandelion-mybatis-generator                   mybatis 代码生成器
├── dandelion-netty                               netty 
├── dandelion-oshi                                oshi 获取主机cpu/内存/系统 ... 资源信心
├── dandelion-oss                                 oss 服务 （阿里）
├── dandelion-repeat-submit                       重复提交验证（redis实现）
├── dandelion-scheduling                          任务调度
|   ├── dandelion-kafka                           kafka 消息
|   ├── dandelion-scheduling-quartz               quartz 定时任务
|   ├── dandelion-scheduling-rabbitmq             rabbitmq 消息
|   ├── dandelion-scheduling-xxl-job              xxl-job 定时任务
|   ├── dandelion-scheduling-rocketmq             rocketmq 消息
├── dandelion-search                              搜索引擎 Lucene/elasticsearch 使用
├── dandelion-security                            SpringBoot-Security安全框架（2.3.2）
|   ├── dandelion-security-init                   初始
|   ├── dandelion-security-jwt                    jwt使用
|   ├── dandelion-security-jwt-auth               jwt+auth使用
├── dandelion-thread                              线程
├── dandelion-ureport2                            图表工具
```

## 版本

```
    1.0      初始化,开发快照
```



