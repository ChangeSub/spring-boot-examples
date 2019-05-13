package com.alex.service.impl;

import com.alex.bean.UserAddress;
import com.alex.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-01-22 9:51
 */
@Service    //暴露服务
@Component
public class UserServiceImpl implements UserService {

    @HystrixCommand
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        System.out.println("远程调用的userid是：" + userId);
        UserAddress address1 = new UserAddress(1, "地址1", "1", "han", "110", "1");
        UserAddress address2 = new UserAddress(2, "地址2", "2", "yu", "119", "2");
//        if(new Random().nextDouble() > 0.5){
//            throw new RuntimeException();
//        }
        return Arrays.asList(address1, address2);
    }
}
