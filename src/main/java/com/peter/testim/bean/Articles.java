package com.peter.testim.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 14-2-28
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "Articles")
public class Articles implements Serializable {

    private List<Item> items;

    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
