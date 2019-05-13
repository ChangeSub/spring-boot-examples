package com.alex.service;



import com.alex.bean.UserAddress;

import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-01-22 9:58
 */
public interface UserService {

    /**
     * 按照用户id返回
     * @param userId
     * @return
     */
    public List<UserAddress> getUserAddressList(String userId);

}
