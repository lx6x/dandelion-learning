# dandelion-scheduling-rabbitmq -- rabbitmq 使用用例

## rpc 远程调用

客户端发送请求消息，服务端回复响应的消息。为了接收响应的消息，我们需要在请求消息中发送一个回调队列，可以使用默认的队列。 replyTo:通常用来设置一个回调队列，用于客户端获取响应信息。 correlationId:用来关联请求(
request)和其调用RPC之后的回复(response)，保证请求与响应是对应的。

用例视图

![img.png](images/mq-rpc.png)