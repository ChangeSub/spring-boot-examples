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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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


    /**
     * 在这里呢，记录一个问题
     * 在java调用wfs的webservice时，
     * 我的一种简单的解决方案：
     *  1.首先用soap-ui,在线生成请求报文，携带action to,调用webservice服务，
     *  2.如果能有正常返回结果的，查看http log，取出所有请求报文
     *  3.利用java的URL对象去发起http请求
     *  4.最后获取完成之后，用dom4j去解析返回的xml
     *  5.数据入库
     *
     */
    public void clientWtihSoap() throws IOException {
        //创建连接对象
        URL wsUrl = null;
        try {
            wsUrl = new URL("http://xxxxxxxxx:8901/PublishCJVTS");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        try {
            //获取连接
            conn = (HttpURLConnection) wsUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.setDoInput(true);
        conn.setDoOutput(true);
        try {
            //设置请求方式
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        //请求头需要携带的参数，用来指明为soap协议（具体需要看报错返回，要求你请求的格式）
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");

        OutputStream os = null;
        try {
            os = conn.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //soap-ui汇聚的请求体
        String soap = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
                " <soap:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://tempuri.org/IServiceGetCJVTS/GetCJVTSData</wsa:Action><wsa:To>http://198.17.1.225:8901/PublishCJVTS</wsa:To></soap:Header>\n" +
                " <soap:Body>\n" +
                " <tem:GetCJVTSData>\n" +
                "<tem:value/>\n" +
                "</tem:GetCJVTSData>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        try {
            os.write(soap.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = null;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] b = new byte[1024];
        int len = 0;
        StringBuffer s = new StringBuffer();
        while((len = is.read(b)) != -1){
            String ss = null;
            try {
                ss = new String(b,0,len,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            s .append( ss);
        }
        //打印结果，如果这个数据量大的话，开发工具可能打印不全，但是数据本身不会缺失
        System.out.println(s);
        //解析数据
//        dom4jUtil.readResult(s.toString());
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
        conn.disconnect();
    }

}
