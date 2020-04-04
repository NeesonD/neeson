### 能力层

* 分布式锁
* 幂等处理
    
    
#### 幂等设计

常用的有两种方法

* 下游提供相应的查询系统
* 下游系统做幂等

**设计**

* 首先需要一个全局 ID
* 处理流程
    * add 操作
        * 每次操作，先查一下。要注意原子处理
        * 通过唯一索引
    * update 操作
    
#### Template 的通用设计

* Operations 定义行为
* Accessor 管理 ConnectionFactory
* Interceptors 对消息进行过滤处理
* Template 具体实现
    * MessageConverter 用于编解码
    * HeadConverter 用于头部编解码
    * ErrorHandler 错误处理