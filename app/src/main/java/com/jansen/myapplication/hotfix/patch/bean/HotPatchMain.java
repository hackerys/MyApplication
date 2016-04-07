package com.jansen.myapplication.hotfix.patch.bean;

import java.io.Serializable;

/**
 * Coolspan on 2016/3/14 18:01
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class HotPatchMain implements Serializable {

    private HotPatchBean hotpatch;//热修复补丁
    private HotPatchBean config;//配置文件补丁
    private HotPatchBean react;//React Native补丁

    public HotPatchBean getHotpatch() {
        return hotpatch;
    }

    public void setHotpatch(HotPatchBean hotpatch) {
        this.hotpatch = hotpatch;
    }

    public HotPatchBean getConfig() {
        return config;
    }

    public void setConfig(HotPatchBean config) {
        this.config = config;
    }

    public HotPatchBean getReact() {
        return react;
    }

    public void setReact(HotPatchBean react) {
        this.react = react;
    }
}
