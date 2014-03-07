package com.peter.testim.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 14-2-26
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "Image")
public class Image implements Serializable {

    private String mediaId;

    @XmlElement(name = "MediaId")
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
