package com.jansen.myapplication.hotfix.patch.bean;

import java.io.Serializable;

/**
 * Coolspan on 2016/3/14 14:45
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class HotPatchRequest implements Serializable {

    public Integer family_id;//app应用名称，这是里"yunshang"
    public Integer os_type;//系统类型，1:Android     2:IOS
    public String ver_num;//app版本名称
    public String device_name;//设备名称
    public String react_md5;//React Native补丁文件的MD5
    public String config_md5;//配置文件的MD5
    public String hotpatch_md5;//热修复补丁文件的MD5

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

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getReact_md5() {
        return react_md5;
    }

    public void setReact_md5(String react_md5) {
        this.react_md5 = react_md5;
    }

    public String getConfig_md5() {
        return config_md5;
    }

    public void setConfig_md5(String config_md5) {
        this.config_md5 = config_md5;
    }

    public String getHotpatch_md5() {
        return hotpatch_md5;
    }

    public void setHotpatch_md5(String hotpatch_md5) {
        this.hotpatch_md5 = hotpatch_md5;
    }
}
