package com.example.appu1_snl.model;


import com.example.appu1_snl.PersonaEntry;

import java.util.List;

public class RptaGeneral {
    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
