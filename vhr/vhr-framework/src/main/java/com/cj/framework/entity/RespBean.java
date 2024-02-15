package com.cj.framework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {

    private Integer status;
    private String message;
    private Object data;


    public static RespBean ok(String message,Object data){
        return new RespBean(200, message, data);
    }

    public static RespBean ok(String message){
        return new RespBean(200, message, null);
    }

    public static RespBean error(String message){
        return new RespBean(500, message, null);
    }

}
