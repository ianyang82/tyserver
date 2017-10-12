package tv.huan.master.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/3/31
 * Time: 18:07
 * To change this template use File | Settings | File Templates
 */
public class EasyUiMenuModel implements Serializable{
    private String id;
    private String text;
    private String url;
    private List<EasyUiMenuModel> child = new ArrayList<EasyUiMenuModel>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<EasyUiMenuModel> getChild() {
        return child;
    }

    public void setChild(List<EasyUiMenuModel> child) {
        this.child = child;
    }
}
