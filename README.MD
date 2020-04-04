### 单体应用的模块化与服务化

这个项目主要用来探索 app 模块化的设计以及通用组件的下沉

* 最佳实践
    * 单体仓库
    * 分层、模块化、六边形架构
        * CQRS
            * 增删改分离
            * 下沉单一业务
            * 值模型、领域事件
        * inbound
            * controller
            * rpc
            * mqListener
        * outbound
            * cache
            * sql
            * mq
            * rpc
    * 通用业务处理
        * 消息、第三方服务的可靠性
        * 排序并下沉 update 语句，避免死锁并减少锁事件
        * 主从延迟处理
            * 延迟队列
            * 走主库
            * cas + retry
        * 接口性能优化思路
            * sql、索引
            * 缓存
            * CQRS、分区
            * 垂直分库、水平分库
    * 通用组件下沉
        * 分布式锁
        * mq、event
        * cache
        * 分布式id生成器
        * JWT + 上下文透传
        * log + mdc + TraceFilter
        * common-utils
    

### java 能力层的构建

#### RPC 框架设计

![](/doc/pic/Rpc.png)

[介绍](https://mubu.com/doc/6US5AgtAuT_)

* oss 
    * oss-api
    * oss-client
    * oss-svc
* rpc-spring-boot-starter

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

    