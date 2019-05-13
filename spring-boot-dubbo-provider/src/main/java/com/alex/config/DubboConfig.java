package com.alex.config;

import com.alex.service.UserService;
import com.alibaba.dubbo.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 使用javaconfig的方式进行配置，这样可进行细节配置，如duboo方法级的精确配置
 *
 *  配置有几种级别：
 *      一级为虚拟机的参数，以-D的形式
 *      二级为xml,properties,yml,javaconfig，同一级中不能出现两个相同的appname
 *      三级为dubbo.properties文件，一般用于共性配置
 *      以上高级覆盖低级
 * @author hany
 * @create 2019-01-23 8:52
 */
@Configuration
public class DubboConfig {

    /**
     * 提供方的应用信息，用于依赖计算
     *
     * @return
     */
    @Bean
    public ApplicationConfig setApplicationConfig() {
        ApplicationConfig app = new ApplicationConfig();
        app.setName("boot-u-service-provider");
        return app;
    }

    /**
     * 使用zk广播注册中心暴露服务
     *
     * @return
     */
    @Bean
    public RegistryConfig setRegistryConfig() {
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        return registry;
    }

    /**
     * 使用dubbo协议在20880端口暴露服务
     *
     * @return
     */
    @Bean
    public ProtocolConfig setProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

    @Bean
    public ServiceConfig<UserService> userServiceServiceConfig(UserService userService){
        ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(userService);
        serviceConfig.setVersion("1.0.0");

        //配置一个method的消息
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("getUserAddressList");
        methodConfig.setTimeout(1000);

        //将method设置配置到service配置中
        List<MethodConfig> methodConfigs = new ArrayList<>();
        methodConfigs.add(methodConfig);

        serviceConfig.setMethods(methodConfigs);


        return  serviceConfig;
    }

}
