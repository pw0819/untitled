package com.peter.testim.server;

import com.peter.testim.bean.ImDto;
import com.peter.testim.util.JaxbUtil;
import com.peter.testim.util.Validate;
import com.peter.testim.util.XmlUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 14-2-25
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public class MainServlet extends HttpServlet {

    private XmlUtil xmlUtil;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append("\r\n");
            sb.append(line);
        }
//        String wxMsgXml = IOUtils.toString(request.getInputStream(), "utf-8");
        ImDto dto = JaxbUtil.converyToJavaBean(sb.toString(), ImDto.class);
//        ImDto dto = (ImDto) this.xmlUtil.convertFromXMLToObject(sb.toString());
        String from = dto.getFromUserName();
        dto.setFromUserName(dto.getToUserName());
        dto.setToUserName(from);

        pw.println(JaxbUtil.convertToXml(dto));
//        pw.println(this.xmlUtil.convertFromObjectToXML(dto));
        pw.flush();
        pw.close();
        super.doPost(request, response);


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("get start...");//TODO 测试代码发布时移除
        response.setContentType("text/html;charset=UTF-8");
        //参考代码，不做校验原样返回
//        String echo = request.getParameter("echostr");
//        pw.println(echo);
        String echoStr = request.getParameter("echostr");
        try {
            if (Validate.checkSignature(request)) {
                PrintWriter wt = response.getWriter();
                wt.write(echoStr);
                wt.close();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("get end...");//TODO 测试代码发布时移除

    }

    @Autowired
    public void setXmlUtil(XmlUtil xmlUtil) {
        this.xmlUtil = xmlUtil;
    }
}
