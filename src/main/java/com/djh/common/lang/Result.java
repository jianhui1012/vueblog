package com.djh.common.lang;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result result(int code, String msg, Object data){
        return new Result(code,msg,data);
    }

    public static Result result(Object data){
        return result(200,"操作成功",data);
    }

    public static Result fail(String msg){
        return result(400,msg,null);
    }


    public static Result fail(int code, String msg, Object data){
        return result(code,msg,data);
    }
}
