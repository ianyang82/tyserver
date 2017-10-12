package tv.huan.master.common.model;

import tv.huan.master.common.enums.StatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/12/10
 * Time: 15:26
 * To change this template use File | Settings | File Templates
 */
public class MyResponse<M> {
    public MyResponse() {
    }

    public MyResponse(StatusEnum statusEnum) {
        setError(statusEnum.getError());
        setMsg(statusEnum.getMsg());
    }

    private int error = StatusEnum.SUCCESS.getError();
    private String msg = StatusEnum.SUCCESS.getMsg();
    private List<M> data = new ArrayList<M>();

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setEnum(StatusEnum statusEnum) {
        setError(statusEnum.getError());
        setMsg(statusEnum.getMsg());
    }

    public List<M> getData() {
        return data;
    }

    public void setData(List<M> data) {
        this.data = data;
    }
}
