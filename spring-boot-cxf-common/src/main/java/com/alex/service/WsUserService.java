package com.alex.service;

import com.alex.entity.Crew;
import com.alex.entity.User;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace="http://service.alex.com/")
public interface WsUserService {

//    @WebMethod
    String getName(@WebParam(name = "userId") String userId);

//    @WebMethod
    User getUser(String userId);

    Crew getCrew(String idCard);
}
