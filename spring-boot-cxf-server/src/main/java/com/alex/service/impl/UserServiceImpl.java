package com.alex.service.impl;


import com.alex.entity.Crew;
import com.alex.service.UserService;

import javax.jws.WebService;


@WebService(targetNamespace = "http://service.sever.ws.com/", endpointInterface = "com.alex.service.UserService")
public class UserServiceImpl implements UserService {

    /**
     * 获取船员数据消息
     *
     * @param idCard
     * @return
     */
    public Crew getCrew(String idCard) {
        System.out.println("请求的数据：："+idCard);
        Crew crew = new Crew();
        crew.setIdCard("1121");
        crew.setName("测试");
        return crew;
    }
}
