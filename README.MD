
这个项目主要用来探索 app 模块化的设计以及通用组件的下沉

* 业务模块化的最佳实践，便于业务开发、扩展和维护
* 通用组件下沉
    * 统一管理第三方依赖
    * 借用 springboot 中的扩展性设计，二开第三方组件，方便业务快速开发


### java 能力层的构建

#### RPC 框架设计

![](/doc/pic/Rpc.png)

#### rabbitmq 二次封装、可靠性投递

![](/doc/pic/MQ_Best_Practices.jpg)

* 自定义注解 @RabbitMqBinding ，实现 Queue、RouteKey、Exchange、DelayQueue 自动注册与绑定
* 通过 lifeCycle + hook 实现可靠性投递
* 自动配置

#### Zookeeper 分布式锁的实现

![](/doc/pic/zk-lock.png)

* 自动配置

#### 日志

* [ ] 统一日志配置与格式
* [ ] 集成 MDC 和 TTL
* [ ] k8s 环境下的最佳配置
* [ ] 自动装配

#### 配置中心

* [ ] 实现启动时，从远程加载配置
* [ ] 监听远程配置，并同步本地信息
* [ ] 自动配置

#### DBProxy

* [ ] 实现多数据源
* [ ] 支持主库查询
* [ ] 分库、分区业务层支撑

#### 报表

* [ ] 压测
* [ ] 调优

    