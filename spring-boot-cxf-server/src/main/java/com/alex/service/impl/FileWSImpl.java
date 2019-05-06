package com.alex.service.impl;

import com.alex.entity.CxfFileWrapper;
import com.alex.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;
import java.io.File;

/**
 * 描述:
 *
 * @author hany
 * @create 2018-12-11 13:35
 */
@WebService(targetNamespace = "http://service.sever.ws.com/", endpointInterface = "com.alex.service.FileService")
public class FileWSImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWSImpl.class);

    @Value("${service-config.file-path}")
    private String filePathProp;

    public CxfFileWrapper download(String fileName) {
        //下载文件的路径
        String filePath = filePathProp + fileName;
        CxfFileWrapper fileWrapper = new CxfFileWrapper();
        File file = new File(filePath);
        if(file.exists()){
            LOGGER.info("本次外网到内网的文件下载请求的文件名为：{}",fileName);
            fileWrapper.setFileName(fileName);
            fileWrapper.setFileExtension("xls");
            DataSource source = new FileDataSource(file);
            fileWrapper.setFile(new DataHandler(source));
        }
        return fileWrapper;
    }
}
