# dandelion-parent

## 模块结构

```
    .../dandelion-scheduling-quartz                 quartz 定时任务模块
        ../dandelion-scheduling-quartz-a            使用 quartz.properties 默认配置文件创建数据源
        ../dandelion-scheduling-quartz-b            使用 application.properties 配置数据源
```

## 模块服务端口

```
    .../dandelion-scheduling-quartz                     
        ../dandelion-scheduling-quartz-a            30000
        ../dandelion-scheduling-quartz-b            30001
```

### dandelion-scheduling-quartz-a

* 使用原生 quartz.properties 进行配置

### dandelion-scheduling-quartz-b

* 顶替原生配置

### 参考配置

* 官方git地址：https://github.com/quartz-scheduler/quartz
* 数据表：https://github.com/quartz-scheduler/quartz/tree/9f9e400733f51f7cb658e3319fc2c140ab8af938/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore

## 版本

```
 1.0.0-SNAPSHOT      初始化,开发快照
```
