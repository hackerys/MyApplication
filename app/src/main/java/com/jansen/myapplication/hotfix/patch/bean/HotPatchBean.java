package com.jansen.myapplication.hotfix.patch.bean;

import java.io.Serializable;

/**
 * Coolspan on 2016/3/14 18:01
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class HotPatchBean implements Serializable {

    public int status;//1:有新补丁文件下载;其他状态都是异常或者是无补丁文件下载
    public String url;//补丁文件下载的url
    public String md5;//补丁文件加密后的MD5
    public int file_size;//补丁文件未加密之前的文件长度
    public String message;//补丁描述信息

    public String md6;//本地保存数据使用

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getMd6() {
        return md6;
    }

    public void setMd6(String md6) {
        this.md6 = md6;
    }
}
