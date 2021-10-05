package com.home.closematch.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Msg {
    private int code;
    private String msg;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> data;

    public Msg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Msg success(){
        return new Msg(200, "success");
    }

    public static Msg success(String msg){
        return new Msg(200, msg);
    }

    public Msg add(String key, Object value){
        if (data == null){
            data = new HashMap<>();
        }
        data.put(key, value);
        return this;
    }

    public static Msg fail(int code, String msg){
        return new Msg(code, msg);
    }

}
