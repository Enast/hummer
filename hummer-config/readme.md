运管统一配置发现与变更
=
## 目录

 * <a href="#1">介绍</a>
 * <a href="#2">使用指南</a>
 
 * * *
 
 ### <a name="1">介绍</a>
 
 hummer-config是为了解决通过运管统一自定义配置的配置项发生变更时程序服务能够及时作出响应的统一配置框架.最终以jar包的方式提供使用.
 

#### 主要功能

 * 支持properties类配置的配置变更响应处理
    
 > <a id="1-1">主要流程</a>
 ```
     节点实例启动的时框架会去主动发现当前实例的注解说要需要框架的管理的任务,并走批量restful接口调用任务注册节点,实现任务的主动注册
     如果任务之前已经注册过(即数据库中的任务表存在该任务),那么此任务会被忽略,因为该功能只负责任务的初始化,不会做任务的更新,确保了任务相关信息的可控性.
 ```
 
 * 支持@Value注解配置的配置变更响应处理
 
 > <a id="1-1">主要流程</a>
 
  ```
    主要流程是由主节点周期性去数据库中获取需要执行的任务,然后异步调用框架中封装好的统一接口,并且这个接口是每个依赖了当前框架的节点实现都会有实现；
    任务执行完之后会调用框架中封装好的接口上报任务执行状态给任务服务端，又服务端做任务的状态信息更新处理。
  ```
 

 ### <a name="2">使用指南</a>
 
 #### 依赖引用(参考hummer-service)
 
  ```
       <dependency>
           <groupId>org.enast.hummer</groupId>
           <artifactId>hummer-config</artifactId>
           <version>1.0.0-SNAPSHOT</version>
       </dependency>
  ```
 
 #### 注解式声明需要管理的配置项所在的bean类
  
  ```
    主要是在对应的配置项所在的spring bean 类中注解@EnableUnifyConfig即可。
    参考样例:
    场景一:
    1.hummer-stream 中HamPersonChanelPeoperties(这是properties类注解的例子)
        1.1
            application.properties文件中可以预先配置:"hummer.person.chanel.pageSize"这个配置项
        如果,运管的自定义配置项中不存在名称定义为:"hummer.person.chanel.pageSize"(和application.properties文件保持一致)的配置
        那么程序运行中使用的application.properties文件的配置,否则使用“配置变更服务”（第三方）配置的值
        1.2
            在“配置变更服务”修改服务的"配置参数"时,如果不是需要服务重启的参数,那么就会由hummer-config对进行配置修改之后的值热加载在程序内存中.实现配置项的变更
    场景二:
    2.hummer-cluster,ConsulElectServiceImpl(这是@Value注解是配置)
        处理逻辑和1.1,1.2一致,运管中自定义配置项的名称和application.properties文件(@Value注解配置的配置项名称一致)
  ```