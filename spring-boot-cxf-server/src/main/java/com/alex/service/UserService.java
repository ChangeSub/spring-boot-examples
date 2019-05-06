package com.alex.service;

import com.alex.entity.Crew;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UserService {

    /**
     * 获取船员信息
     *
     * @param idCard
     * @return
     */
    @WebMethod
    Crew getCrew(String idCard);
}
