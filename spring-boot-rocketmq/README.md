springboot与这个rocketmq结合的小例子
========================================

安装过程
------------------------------
安装rocketmq
----------------------------
1.解压下面的文件
2.配置环境变量  （ROCKETMQ_HOME	具体解压的根目录）

启动mq
----------------------------
1.打开mq的bin目录
2.启动nameserver	
>start mqnameserver.cmd
3.启动broker
>start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true

rocketmq插件
--------------------------------
1.创建具体文件夹
2.git clone https://github.com/apache/rocketmq-externals.git
3.下载完成之后，进入‘rocketmq-externals\rocketmq-console\src\main\resources’文件夹，打开‘application.properties’进行配置。
4.指定mq的链接消息(ip:端口号)或修改端口号
server.port=8082
rocketmq.config.namesrvAddr=127.0.0.1:9876

5.编译项目
进入‘\rocketmq-externals\rocketmq-console’文件夹，执行‘mvn clean package -Dmaven.test.skip=true’，编译生成。

6.编译成功之后，Cmd进入‘target’文件夹，执行‘java -jar rocketmq-console-ng-1.0.0.jar’，启动‘rocketmq-console-ng-1.0.0.jar’。

7.测试
浏览器中输入‘127.0.0.1：配置端口’，成功后即可查看。


与springboot整合的过程，参考博客如下
-----------------------------------
https://blog.csdn.net/qq_18603599/article/details/81172866
