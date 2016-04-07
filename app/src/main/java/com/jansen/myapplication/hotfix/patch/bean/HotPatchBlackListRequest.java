package com.jansen.myapplication.hotfix.patch.bean;

import java.io.Serializable;

/**
 * 补丁热修复拉入黑名单Bean
 * <p/>
 * Coolspan on 2016/3/30 16:13
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class HotPatchBlackListRequest implements Serializable {

    //    {"device_name":"HUAWEI:SCL-TL00H","family_id":1,"os_type":1,"ver_num":"2.1.1.开发",type:3}

    private String device_name;
    private Integer family_id;
    private Integer os_type;
    private String ver_num;
    private Integer type;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public Integer getFamily_id() {
        return family_id;
    }

    public void setFamily_id(Integer family_id) {
        this.family_id = family_id;
    }

    public Integer getOs_type() {
        return os_type;
    }

    public void setOs_type(Integer os_type) {
        this.os_type = os_type;
    }

    public String getVer_num() {
        return ver_num;
    }

    public void setVer_num(String ver_num) {
        this.ver_num = ver_num;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
