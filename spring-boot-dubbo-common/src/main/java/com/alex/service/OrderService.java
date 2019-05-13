package com.alex.service;



import com.alex.bean.UserAddress;

import java.util.List;

/**
 * 描述:
 *  将接口提出来，方便多模块调用
 *
 * @author hany
 * @create 2019-01-22 9:58
 */
public interface OrderService {
    public List<UserAddress> initOrder(String userId);
}
