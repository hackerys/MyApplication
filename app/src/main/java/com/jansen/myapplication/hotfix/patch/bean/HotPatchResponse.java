package com.jansen.myapplication.hotfix.patch.bean;

import java.io.Serializable;

/**
 * Coolspan on 2016/3/14 16:01
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class HotPatchResponse implements Serializable {

    private int code;//状态
    private String message;//成功描述信息
    private String error;//错误描述信息
    private HotPatchMain body;//补丁主要内容

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HotPatchMain getBody() {
        return body;
    }

    public void setBody(HotPatchMain body) {
        this.body = body;
    }
}
