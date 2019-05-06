package com.alex.service;

import com.alex.entity.CxfFileWrapper;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 描述:
 * 根据文件名一个一个去获取
 *
 * @author hany
 * @create 2018-12-10 9:20
 */

@WebService
public interface FileService {

    @WebMethod
    CxfFileWrapper download(String fileName);
}
