package com.alex.service.impl;

import com.alex.bean.UserAddress;
import com.alex.service.OrderService;
import com.alex.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-01-22 10:13
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Reference
    UserService userService;

    @HystrixCommand(fallbackMethod = "hello")
    @Override
    public List<UserAddress> initOrder(String userId) {
        System.out.println("用户的id是：" + userId);
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        for (UserAddress address : addressList) {
            System.out.println(address);
        }

        return addressList;
    }


    /**
     * hystrix代理容错
     *  dubbo原有容错可以直接在client返回为null,也可以在调用后，报错时，返回为null
     *
     * @param userId
     * @return
     */
    public List<UserAddress> hello(String userId) {
        System.out.println("被容错的用户的id是：" + userId);
        return null;
    }
}
