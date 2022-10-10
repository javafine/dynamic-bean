# dynamic-bean
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
