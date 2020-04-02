#### Rpc 框架

* https://gitee.com/huangyong/rpc/tree/master
* https://github.com/luxiaoxun/NettyRpc


#### Template 的通用设计

* Operations 定义行为
* Accessor 管理 ConnectionFactory
* Interceptors 对消息进行过滤处理
* Template 具体实现
    * MessageConverter 用于编解码
    * HeadConverter 用于头部编解码
    * ErrorHandler 错误处理