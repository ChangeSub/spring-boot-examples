package com.alex.config;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-05 11:07
 */
import com.alex.service.WsFileService;
import com.alex.service.WsUserService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {

    // 创建客户端代理类
    private JaxWsProxyFactoryBean clientFactory = new JaxWsProxyFactoryBean();

    @Value("${service-config.webservice}")
    private String webservicePath;

    @SuppressWarnings("unchecked")
    private final <T> T buildClient(Class<T> clazz, String address) {
        clientFactory.setServiceClass(clazz);
        clientFactory.setAddress(address);
        return (T) clientFactory.create();
    }


    @Bean // 创建船员soap接口服务
    public WsUserService userService(){
        return buildClient(WsUserService.class, webservicePath+"user?wsdl");
    }

   /* @Bean // 创建Ems信息soap接口服务
    public WsEmsService emsService(){
//        return buildClient(WsEmsService.class, "http://http://47.98.108.145:8098/ws/ems?wsdl");
        return buildClient(WsEmsService.class, webservicePath+"ems?wsdl");
    }*/

    @Bean // 创建文件下载的soap接口服务
    public WsFileService fileService(){
//        return buildClient(WsEmsService.class, "http://http://47.98.108.145:8098/ws/ems?wsdl");
        return buildClient(WsFileService.class, webservicePath+"file?wsdl");
    }


}
