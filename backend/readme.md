# springcloud-kkcms
微服务框架

**项目介绍技术** 
核心技术：Spring Cloud、OAuth2、jwt、Spring Cloud Security、Eureka、Zuul、Hystrix、Feign、Ribbon、Redis、Mybatis、Mysql、Oracle、GraphQL

- Eureka实现了服务注册中心以及服务注册与发现
- zuul实现api网关服务，是各模块的统一接口，并在zuul上做了认证鉴权和监控。
- 认证鉴权方面采用了spring security+spring oauth2，实现了自定义的授权服务器和资源服务器，使用jwt做token令牌发放（内还含有session+cookie的传统授权方式）。
- 服务间通过Ribbon或Feign实现服务的消费以及均衡负载
- 为了使得服务集群更为健壮，使用Hystrix的融断机制来避免在微服务架构中个别服务出现异常时引起的故障蔓延。
- redis做缓存，用来存储令牌、验证码、session等热点数据
- 数据库方面使用mybatis+druid数据源。可以使用oracle数据库和mysql数据库。
- api服务可以使用RestFul也可以使用GraphQL（所有实体类的增删改查只需要两个接口，且可以通过前端json请求自定义需要的返回结果）。
- 使用Swagger做了API接口文档生成


- **基于spark的电影推荐系统前端页面演示**
1

- **项目测试截图**
服务注册中心
![服务注册中心](https://gitee.com/uploads/images/2018/0212/160802_19da651b_1689775.png "chrome_2018-02-12_16-07-41.png")
使用GraphQL的查询
![使用GraphQL的查询](https://gitee.com/uploads/images/2018/0212/155450_e3b9f657_1689775.png "chrome_2018-02-12_15-49-15.png")
未登录或令牌无效时的拦截
![未登录或令牌无效时的拦截](https://gitee.com/uploads/images/2018/0212/155633_6c9dd22c_1689775.png "chrome_2018-02-12_15-51-09.png")
错误返回信息自定义信息和错误码
![错误返回信息自定义信息和错误码](https://gitee.com/uploads/images/2018/0212/160021_80592735_1689775.png "chrome_2018-02-12_15-58-27.png")
![错误返回信息自定义信息和错误码](https://gitee.com/uploads/images/2018/0212/160032_bcf5836a_1689775.png "chrome_2018-02-12_15-59-03.png")
![错误返回信息自定义信息和错误码](https://gitee.com/uploads/images/2018/0212/155956_59ed581a_1689775.png "chrome_2018-02-12_15-59-25.png")
登陆验证通过返回jwt令牌
![登陆验证通过返回jwt令牌](https://gitee.com/uploads/images/2018/0212/155532_89de2aba_1689775.png "chrome_2018-02-12_15-50-36.png")
jwt令牌的解析，后台已整合
![jwt令牌的解析](https://gitee.com/uploads/images/2018/0212/161035_18aa76e0_1689775.png "chrome_2018-02-12_16-09-55.png")


