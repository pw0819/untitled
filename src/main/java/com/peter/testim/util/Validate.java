package com.peter.testim.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: LIUTENG
 * Date: 13-5-23
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class Validate {
    private static String token = "111111";

    public static boolean checkSignature(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        return checkSignature(signature, timestamp, nonce);
    }

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] tmpArr = {token, timestamp, nonce};
        //将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(tmpArr);
        String tmpArrToStr = "";
        for (String string : tmpArr) {
            tmpArrToStr += string;
        }
        //对拼接后的字符串进行sha1加密
        tmpArrToStr = sha1(tmpArrToStr);
        System.out.println("计算生成的加密字符串:" + tmpArrToStr + "\n来自微信服务器的加密字符串:" + signature);//TODO 测试代码发布时移除
        if (signature.equals(tmpArrToStr)) {
            return true;
        } else {
            return false;
        }
    }

    public static String sha1(String tmpArrToStr) {
        MessageDigest md;
        String password = "";
        try {
            md = MessageDigest.getInstance("sha1");
            md.update(tmpArrToStr.getBytes());
            password = new BigInteger(1, md.digest()).toString(16);
            System.err.println(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

}
