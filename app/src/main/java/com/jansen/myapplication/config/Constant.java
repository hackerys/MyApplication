package com.jansen.myapplication.config;

import android.os.Environment;

/**
 * Created Jansen on 2016/3/30.
 */
public class Constant {
    //SD卡根目录
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath();
    //传到裁剪页面的图片路径
    public static final String PIC_TO_CLIP_PATH="clipPath";
    //传回到显示页面的图片路径
    public static final String CLIP_TO_SHOW_PATH="showPath";
    //图片存储目录
    public static final String PIC_SAVE_PATH=FILE_PATH+"/jansen/pictures";
    //从相册选择图片
    public static final int REQUES_GELLARY=1;
    //从相机选择图片
    public static final int REQUE_CAMARA=2;
    //从裁剪返回的图片
    public static final int REQUES_CUT=3;
    //图片上传的最大值
    public static final int MAX_PIC_SIZE=200;
}
