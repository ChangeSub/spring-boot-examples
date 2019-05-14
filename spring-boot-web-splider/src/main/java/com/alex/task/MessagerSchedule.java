package com.alex.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@EnableAsync
public class MessagerSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagerSchedule.class);


    @Value("${service-config.file-path}")
    private String filePath;

    @Value("${msa.login.username}")
    private String loginName;

    @Value("${msa.login.password}")
    private String loginPwd;

    @Value("${msa.certificate.hyz}")
    private String hyzProp;
    @Value("${msa.certificate.cyzc}")
    private String cyzcProp;
    @Value("${msa.certificate.hgz}")
    private String hgzProp;
    @Value("${msa.certificate.srz}")
    private String srzProp;

    /**
     * 这个项目里面要做两件事情
     * <p>
     * 1.定时任务去获取4个exel文件，将文件下载，内容解析，然后存入mysql，
     * 2.做一个船员数据获取的一个接口。
     */
    @Async
    @Scheduled(cron = "0 30 16 ? * *")
    public void downExl() {
        WebClient webClient = initClient();
        List<String> pathList = new ArrayList<String>();
        Map<Integer, String> pathTypeList = new HashMap<Integer, String>();
        try {
            HtmlPage page = webClient.getPage("http://cmp.msa.gov.cn");
            Thread.sleep(11L);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://sso.msa.gov.cn/oam/server/auth_cred_submit?username=");
            stringBuilder.append(loginName);
            stringBuilder.append("&password=");
            stringBuilder.append(loginPwd);
            webClient.getPage(stringBuilder.toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
            String nows = simpleDateFormat.format(new Date());
            System.out.println("hyz:s:" + hyzProp);
            try {
                UnexpectedPage hyz = webClient.getPage("http://cmp.msa.gov.cn/crew_mgt/cert/exportMailMessage.action?busi_code=crew:hyz");
                InputStream hyzStream = hyz.getInputStream();
                String hyzNmae = filePath + hyzProp + nows + ".xls";
//                FileUtil.getFile(hyzStream, hyzNmae);
                pathList.add(hyzNmae);
                pathTypeList.put(4, hyzNmae);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }catch (FailingHttpStatusCodeException e){
                e.printStackTrace();
            }

            try {
                UnexpectedPage hgz = webClient.getPage("http://cmp.msa.gov.cn/crew_mgt/cert/exportMailMessage.action?busi_code=crew:hgz");
                InputStream hgzStream = hgz.getInputStream();
                String hgzNmae = filePath + hgzProp + nows + ".xls";
//                FileUtil.getFile(hgzStream, hgzNmae);
                pathList.add(hgzNmae);
                pathTypeList.put(2, hgzNmae);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }

            try {
                UnexpectedPage srz = webClient.getPage("http://cmp.msa.gov.cn/crew_mgt/cert/exportMailMessage.action?busi_code=crew:srz");
                InputStream srzStream = srz.getInputStream();
//                String srzNmae = filePath + "srz" + nows + ".xls";
                String srzNmae = filePath + srzProp + nows + ".xls";
//                FileUtil.getFile(srzStream, srzNmae);
                pathList.add(srzNmae);
                pathTypeList.put(3, srzNmae);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }
    }


    @Async
    @Scheduled(cron = "0 10 17 ? * *")
    public void test() {
        WebClient webClient = initClient();
        try {
            HtmlPage page = webClient.getPage("http://cmp.msa.gov.cn");
            Thread.sleep(11);

            webClient.getCookieManager().setCookiesEnabled(true);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://sso.msa.gov.cn/oam/server/auth_cred_submit?username=");
            stringBuilder.append(loginName);
            stringBuilder.append("&password=");
            stringBuilder.append(loginPwd);
            webClient.getPage(stringBuilder.toString()).getWebResponse().getContentAsString();


            Map<Integer, String> urlsTypeList = new HashMap<Integer, String>();
            urlsTypeList.put(2, "http://cmp.msa.gov.cn/crew_mgt/ShowReport.wx?PAGEID=list_seaqualcert_issue");
            urlsTypeList.put(3, "http://cmp.msa.gov.cn/crew_mgt/ShowReport.wx?PAGEID=list_compcert_issue");
            urlsTypeList.put(4, "http://cmp.msa.gov.cn/crew_mgt/ShowReport.wx?PAGEID=list_seafarercert_issue");

            for (int mapItem = 1; mapItem <= 4; mapItem++) {
                String url = urlsTypeList.get(mapItem);
                if (null == (url)) {
                    continue;
                }


                HtmlPage pageShowReport = webClient.getPage(url);
                System.out.println("-=-=-=-=-=-=-=-=-ees=");
                System.out.println("-=-=-=-=-=-=-=-=-edssss=");

                //获取html的页数分页
                List<Object> htmlTableCellList = pageShowReport.getByXPath("//td[@class='cls-navigate-info']");

                if (htmlTableCellList.size() > 0) {


                    HtmlTableCell htmlTableCell = (HtmlTableCell) htmlTableCellList.get(0);

                    System.out.println("htmlss:: " + htmlTableCell.asXml());

                    HtmlSpan htmlSpan = (HtmlSpan) htmlTableCell.getFirstChild();

                    DomNodeList<DomNode> nodeDomNodeList = htmlSpan.getChildNodes();

                    DomNode node = nodeDomNodeList.get(0);
                    String local_page_num = node.asText().replace(" ", "");
                    System.out.println("local_page_num:: " + local_page_num);


                    if (local_page_num.startsWith("当前")) {
                        //获取出总记录条数
                        Integer total_count = Integer.valueOf(local_page_num.substring(local_page_num.indexOf('共') + 1, local_page_num.indexOf('条')));
                        int pageNum = total_count / 100 + (total_count % 100 > 0 ? 1 : 0);
                        //获取select
                        HtmlSelect pageNubSelect = (HtmlSelect) htmlTableCell.getElementsByAttribute("select", "data-from", "selectbox_6").get(0);
                        HtmlOption htmlOption_100 = pageNubSelect.getOptionByValue("100");
                        HtmlPage option_click_back = htmlOption_100.click();
                        webClient.waitForBackgroundJavaScript(20000);   //等待
                        System.out.println("htmlBack::" + option_click_back.asXml() + "-=-=-=-=-=-=-=-=-option_click_back-end=");
                        System.out.println("htmlBack:pageNum:" + pageNum);

                        HtmlSpan input_out_span = (HtmlSpan) htmlTableCell.getLastElementChild();
                        HtmlInput local_page = (HtmlInput) input_out_span.getLastElementChild();

                        System.out.println("local_page::" + local_page.asXml());

                        for (int pg = 1; pg <= pageNum; pg++) {
                            /*
                             *  判断当前页的cell数量
                             *  1.当前条数小于等于这个已加载的100
                             *  （如果总页数等于1，那么就直接是当前的 total count）
                             *  2.当前条数大于等于这个100
                             *  （应该这样来判断，如果计算出来的总页数，大于1，则之前的所有页数据，为total count，最后一页数据为[total count%100]）
                             */
                            int local_page_cell_num = total_count % 100 == 0 ? 100 : pg == pageNum ? total_count % 100 : pg <= pageNum ? 100 : total_count;

                            //点击切换为100之后，首次无需点击
                            if (pg > 1 && pg < pageNum) {
                                //聚焦
                                local_page.mouseDown();
                                local_page.setAttribute("value", String.valueOf(pg));
                                //失焦
                                local_page.mouseOut();
                                //等待后端渲染
                                webClient.waitForBackgroundJavaScript(20000);

                            }

                            //通过100来操作
                            //定制规则
                            String rule_url = url.substring(url.indexOf('=') + 1, url.length());

                            //等待10s 、、//*[@id="list_compcert_issue_guid_report1_data"] ---需动态获取
                            System.out.println("地址：：" + rule_url + "_guid_report1_data");
                            HtmlTable table = (HtmlTable) option_click_back.getElementById(rule_url + "_guid_report1_data");
                            System.out.println("-=-=-=-=-=-=-=-=-table=" + table.asXml() + "-=-=-=-=-=-=-=-=-table-end=");
                            System.out.println("当前网址：：" + url);
                            //循环操作这个数据
                            for (int i = 0; i < local_page_cell_num; i++) {
                                HtmlTableRow item_tr = table.getRowById(rule_url + "_guid_report1_tr_" + i);
                                System.out.println("item_tr::" + item_tr.asXml());
                                String td_name_rule = rule_url + "_guid_report1_wxcol_name__td" + i;
                                //获取人员姓名  list_compcert_issue_guid_report1_wxcol_name__td14
                                HtmlTableDataCell td_name = item_tr.getOneHtmlElementByAttribute("td", "id", td_name_rule);
                                String local_name = td_name.getAttribute("value");
                                System.out.println("local_name::" + local_name);
//                                if (local_name.equals("翟建军") || local_name.equals("杨志伟")) {

                                    //获取取证方式        --list_compcert_issue_guid_report1_wxcol_take_cert_mode__td14
                                    String recive_type_rule = rule_url + "_guid_report1_wxcol_take_cert_mode__td" + i;
                                    HtmlTableDataCell recive_type_cell = item_tr.getOneHtmlElementByAttribute("td", "id", recive_type_rule);
                                    String recive_type = recive_type_cell.getAttribute("value");
                                    System.out.println("recive_type::" + recive_type);
                                    if ("邮寄".equals(recive_type)) {
                                        //勾选该checkbox
                                        String checkbox_rule = rule_url + "_guid_report1_rowselectbox_col";
                                        HtmlCheckBoxInput checkBoxInput = item_tr.getOneHtmlElementByAttribute("input", "name", checkbox_rule);
                                        checkBoxInput.click();
                                    }

                            }
                            //list_compcert_issue_issue_guid_report1_button1_id
                            String send_btn_rule = "btn_" + rule_url + "_guid_report1_button1_id";
                            HtmlButton send_cert_button = (HtmlButton) pageShowReport.getElementById(send_btn_rule);
                            send_cert_button.click();
                            System.gc();
                            webClient.waitForBackgroundJavaScript(20000);   //等待
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }
    }


    public static WebClient initClient() {
        // 创建一个webclient
//        WebClient webClient = new WebClient();
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        //3 启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 4 运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5 设置超时
        webClient.getOptions().setTimeout(50000);
        webClient.getCache().setMaxSize(0);
        //6 设置忽略证书
        //webClient.getOptions().setUseInsecureSSL(true);
        //7 设置Ajax
        //webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //8设置cookie
        webClient.getCookieManager().setCookiesEnabled(true);
        return webClient;
       /* WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);

        webClient.getOptions().setCssEnabled(false);

        webClient.getOptions().setRedirectEnabled(true);

        webClient.getOptions().setThrowExceptionOnScriptError(false);

        webClient.getOptions().setTimeout(60000);
        webClient.getCache().setMaxSize(0);

        webClient.getCookieManager().setCookiesEnabled(true);
        return webClient;*/
    }


    /**
     * Jsoup解析示例
     */




}
