# dandelion-netty

## dandelion-netty-init

学习了解，基础使用

### 项目结构

```
|_ bio      Bio 使用测试案例
|_ biopool  伪异步 IO 使用案例
|_ netty    Netty使用简单案例
   |_ netty   使用
   |_ line    LineBasedFrameDecoder 编码器解析策略使用（解决粘包拆包问题）
|_ nio      Nio使用案例
```

## dandelion-netty-im

聊天工具，算是个小案例

### 模块结构

#### dandelion-netty-im-server

**im 服务端**

* 端口：
    * server：30019/30020
    * netty：8081

----

### dandelion-netty-im-routing

**im 路由**

client 请求 server 从该服务获取请求连接地址

* 端口：
    * server：30021

----

### dandelion-netty-im-commons

**im 公共模块**

```text
|_protobuf  message proto 解析
|_src...
  |_config  配置
  |_utils   工具
```

----

### dandelion-netty-im-client

**im 客户端**

----

**protobuf使用**

* github下载 / 安装 / 环境变量配置 / 验证是否安装成功 -- 【protoc --version】
* 编写 .proto 文件

```protobuf
syntax = "proto2";

package protocol;

option java_package = "org.dandelion.netty.im.protocol";
option java_outer_classname = "MessageProto";

message MessageProtocol {

  required string command = 1;
  required int64 time = 2;
  required string userId = 3;
  required string content = 4;

}
```

* windows下打开cmd窗口，执行命令

```cmd
protoc -I=proto文件所在目录 --java_out=java文件生成的根目录(通常src/main/java) proto文件的绝对路径
eg:
protoc -I=src/main/resources/proto --java_out=src/main/java src/main/resources/proto/ProtoBufHeader.proto

```

####        



