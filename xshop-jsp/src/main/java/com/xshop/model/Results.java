package com.xshop.model;

import java.util.List;

public class Results<T> {
    private Integer code;
    private String msg;
    private List<T> datas;
    private T data;

    public Results() {}

    public Results(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Results(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Results(Integer code, String msg, List<T> datas) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
    }

    public Results(Integer code, String msg, List<T> datas, T data) {
        this.code = code;
        this.msg = msg;
        this.datas = datas;
        this.data = data;
    }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public List<T> getDatas() { return datas; }
    public void setDatas(List<T> datas) { this.datas = datas; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
