# dynamic-bean
# 特性
* 扩展性：支持单维度（策略模式）、双维度（桥接模式）和多维度的扩展，增强系统结构稳定性，降低耦合度。
* 隔离性：每个维度（controller层、service层、dao层、各业务层等）内增加处理类满足各种定制需求，运行期间根据输入tag标识自动路由到相应的处理类，减少测试成本和更新风险。
* 动态性：运行期自动的业务路由（基于请求的tag标识）和热部署降低更新和发布成本。
## 环境
jdk1.8、maven3.8.4、IntelliJ IDEA(Community Edition)
## 代码结构
* dynamic-bean：基于spring的FactoryBean机制和JDK的Proxy机制，实现对bean的动态分派和动态加载的组件，核心逻辑在DynamicFactoryBean.java。
* dynamic-bean-demo：使用dynamic-bean组件的demo工程。基于spring boot实现的WEB工程，用于测试动态分派实现的策略模式（SampleController#color(String)）和桥接模式（SampleController#colorAndShape(String, String)）。
## 运行测试
1. 启动dynamic-bean-demo工程（DynamicBeanApplicationDemo#main(String[])）。
2. 请求：`curl --location --request GET 'http://localhost:8080/color/red'`，页面返回`I am red`  
请求：`curl --location --request GET 'http://localhost:8080/color/red/shape/circle'`，页面返回`I am a circle, I am red`  
请求：`curl --location --request GET 'http://localhost:8080/color/blue/shape/rectangle'`，页面返回`I am a rectangle, I am blue`  
## 联系方式
javafine@163.com
