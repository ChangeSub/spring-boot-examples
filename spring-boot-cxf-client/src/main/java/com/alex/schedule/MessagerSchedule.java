package com.alex.schedule;

import com.alex.entity.Crew;
import com.alex.entity.CxfFileWrapper;
import com.alex.service.WsFileService;
import com.alex.service.WsUserService;
import com.alex.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-05 14:58
 */

//https://github.com/ChangeSub/spring-boot-examples.git/
@Component
@EnableAsync
public class MessagerSchedule {
    @Autowired
    private WsFileService wsFileService;

    @Autowired
    private WsUserService wsUserService;

    @Value("${service-config.file-path}")
    private String filePath;

    @Async
    @Scheduled(fixedDelay = 10*60*1000)
    public void testFileDownLoad() {

        System.out.println("working....");

        String fileName = "srz1219.xls";
        CxfFileWrapper cxfFileWrapper = wsFileService.download(fileName);

        DataHandler dataHandler = cxfFileWrapper.getFile();

        try {

            InputStream is = dataHandler.getInputStream();
            FileUtil.getFile(is, filePath + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Async
    @Scheduled(fixedDelay = 10*60*1000)
    public void testReqUser() {
        Crew crew = wsUserService.getCrew("111");

        System.out.println("crew::"+crew);
    }

}
