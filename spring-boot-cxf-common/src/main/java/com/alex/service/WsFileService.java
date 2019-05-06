package com.alex.service;

import com.alex.entity.CxfFileWrapper;

import javax.jws.WebService;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-05 11:06
 */

/**
 *这个地方写包名
*/
@WebService(targetNamespace="http://service.alex.com/")
public interface WsFileService {
    CxfFileWrapper download(String fileName);
}
