package com.xyw.result;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:15:08
 */
public class Result<T> {

    private int code;

    private String msg;

    private T data;


    /**
     * 成功时调用
     * @param data
     * @param <T>
     * @return
     *
     * 第一个<T>声明此方法持有一个类型T,也可以理解为声明此方法为范型方法
     */
    public <T> Result<T> success(T data){
        return new Result<T>(data);
    }


    /**
     * 失败时调用
     * @param codeMsg
     * @param <T>
     * @return
     */
    public <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    private Result(T data){
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg){
        if(codeMsg != null){
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
