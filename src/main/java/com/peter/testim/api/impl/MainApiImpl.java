package com.peter.testim.api.impl;

import com.peter.testim.api.MainApi;
import com.peter.testim.bean.Articles;
import com.peter.testim.bean.ImDto;
import com.peter.testim.bean.Image;
import com.peter.testim.bean.Item;
import com.peter.testim.util.HttpUtil;
import com.peter.testim.util.Validate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 14-2-25
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public class MainApiImpl implements MainApi {
    private static final String MEDIA_ID_A = "e0wFC32rVOUh1vrN0FuGrCggtjM9ozkOgEnZSCjWf8H_A5Dgw7HB-ERuFLpyhCea";
    private static final String MEDIA_ID_B = "DXFAbksCYYP1_KbvVpd23_XsyH-nWOLAhOGfiZBSfK7DsyOmf3epnUPUOwobDc9g";
    @Override
    public String doGet(String signature, String timestamp, String nonce, String echoStr) {
        if (Validate.checkSignature(signature, timestamp, nonce)) {
            return echoStr;
        }
        return null;
    }

    @Override
    public ImDto doPost(ImDto dto) {
        String from = dto.getFromUserName();
        dto.setFromUserName(dto.getToUserName());
        dto.setToUserName(from);
        this.returnTextPic(dto);
//        this.returnText(dto);
//        this.returnPic(dto);
        return dto;
    }

    private void returnText(ImDto dto) {
        dto.setMsgType("text");
        dto.setContent("<a href=\"http://club.pchome.net/thread_1_15_7973071__.html\">test</a>");
    }

    private void returnTextPic(ImDto dto) {
        dto.setMsgType("news");
        dto.setArticleCount(2);
        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setTitle("测试1");
        item.setDescription("description1");
        item.setPicUrl("http://img.club.pchome.net/upload/club/other/2014/2/28/pics_Teo_Ace_1393575423.jpg");
        item.setUrl("http://club.pchome.net/thread_1_15_7973071__.html");
        items.add(item);
        item = new Item();
        item.setTitle("测试2");
        item.setDescription("description2");
        item.setPicUrl("http://img.club.pchome.net/upload/club/other/2014/2/28/pics_Teo_Ace_1393575886.jpg");
        item.setUrl("http://club.pchome.net/thread_1_15_7958463_3__TRUE.html");
        items.add(item);
        Articles articles = new Articles();
        articles.setItems(items);
        dto.setArticles(articles);
    }

    private void returnPic(ImDto dto) {
        dto.setMsgType("image");
        Image image = new Image();
        //先要上传个图片然后拿到MediaId
        image.setMediaId("e0wFC32rVOUh1vrN0FuGrCggtjM9ozkOgEnZSCjWf8H_A5Dgw7HB-ERuFLpyhCea");
        dto.setImage(image);
    }

    public static void main(String[] orgs) {
        //上传一个图片,得到MediaId
        HttpUtil httpUtil = new HttpUtil();
        File file = new File("/Users/test/Downloads/b.jpg");
        try {
            String s = httpUtil.callMethod("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=z_lUbwQ6m5VrWGv8HTpKBHEA2HJhtrSg57x9tIDl0I2qNNm_2BOtGiEOzuaAlcEN7wqQ2TVwcoud7QGkg2QHrjh9mDzJr4haTseatbaJEhZoboAAQFwLgl7-kRtTZCB6JHooYkL-Y6a0tJJyTx6jpQ&type=image", null, file);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
