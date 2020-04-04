#### Rpc 框架

**參考**

* https://gitee.com/huangyong/rpc/tree/master
* https://github.com/luxiaoxun/NettyRpc

![](/doc/pic/Rpc.png)

[设计](https://mubu.com/doc/6US5AgtAuT_)

* oss 
    * oss-api
    * oss-client (客户端)
    * oss-svc (服务端)
* rpc-spring-boot-starter

#### 启动

本地安装一个 zookeeper 

* run oss-svc (这个可以开多个)
* run oss-client
* 测试 oss-svc 中的接口即可