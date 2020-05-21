# Hummer

#### 介绍
基于spring-boot的一组分布式套件，包括，主从选举、统一任务调度、统一配置变更等

#### 主从选举（hummer-cluster）
    目前：
        支持基于consul的分布式锁实现的主从选举。
        内置分布式锁，可以直接使用。
    新目标：
        支持zookeeper的选举方案。
        支持基于netty实现的raft选举方案。
[详情查看](./hummer-cluster/cluster-consul/readme.md). 
#### 统一任务调度（hummer-task）
    目前：
        支持定时任务周期3分钟以上的任务的统一管理和调度，解决避免重复执行，或者执行失败后无法重试执行的问题。
        支持具有“前置任务”的任务的按需调度
        内置分布式任务调度锁，解决基于Spring定时任务框架实现的定时任务，在多实例部署场景会重复执行的问题。
    新目标：
        支持后台管理系统：任务配置、重启、修改执行时间等功能。
[详情查看](./hummer-task/readme.md). 
#### 统一配置（hummer-config）
    目前（代码整理中）：
        支持application.properties和spring自动配置的配置的热加载和更新
    新目标：
        支持对接第三方配置框架
        支持多样化的配置数据入口。
[详情查看](./hummer-config/readme.md). 

#### 更多惊喜，期待您的加入。。。。

#### 参与贡献

注意：代码提交主库在github。初次拉取代码时，如果国内网络不好，请到：https://gitee.com/enast/hummer （码云）上拉取最新代码，然后再本地切换到github即可。

```
1.  Fork 本仓库
2.  新建 Feat_develop 分支
3.  提交代码
4.  新建 Pull Request
```
