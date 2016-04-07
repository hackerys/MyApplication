package com.jansen.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.jansen.myapplication.base.BaseActivity;
import com.jansen.myapplication.bean.Parent;
import com.jansen.myapplication.net.CommonParams;
import com.jansen.myapplication.config.Constant;
import com.jansen.myapplication.config.Urls;
import com.jansen.myapplication.net.HttpManager;
import com.jansen.myapplication.net.NetListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/3/30.
 */
public class httpUpload extends BaseActivity implements NetListener {

    @Bind(R.id.upload)
    Button mUpload;
    @Bind(R.id.sendJson)
    Button mSendJson;
    @Bind(R.id.download)
    Button mDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_layout);
        ButterKnife.bind(this);
    }

    @Override
    public void initeData() {

    }

    @Override
    public void initeView() {

    }

    @Override
    public void initeListener() {
        //设置监听器
        HttpManager.getInstance().setNetListener(httpUpload.this);
    }

    @OnClick({R.id.upload, R.id.sendJson, R.id.download})
    public void onClick(View view) {
        /**
         * 上传文件
         */
        if (view.getId() == R.id.upload) {
            //上传的参数
            CommonParams mParams = new CommonParams(Urls.HOST + Urls.UPLOAD_FILE_ACTION, httpUpload.this);
            // 使用multipart表单上传文件,重要
            mParams.setMultipart(true);
            try {
                mParams.addBodyParameter("file1", new FileInputStream(Constant.FILE_PATH+"/a.jpg"), "image/jpeg", System.currentTimeMillis()+"我靠.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            HttpManager.getInstance().LoadFile(mParams, Urls.UPLOAD_FILE_FLAG);
        }
        /**
         * 传递json对象
         */
        if (view.getId() == R.id.sendJson) {
            Parent mParent = new Parent();
            mParent.setEmail("904042749@qq.com");
            mParent.setName("言胜");
            mParent.setDate(new Date());
            CommonParams mParams = new CommonParams(Urls.HOST + Urls.SEND_JSON_ACTION, httpUpload.this);
            mParams.addBodyParameter("json", JSON.toJSONString(mParent));
            HttpManager.getInstance().post(mParams, Urls.SEND_JSON_FLAG);
        }
        /**
         * 下载文件
         */
        if (view.getId() == R.id.download) {
            CommonParams mParams = new CommonParams(Urls.HOST + Urls.DOWN_LOAD_ACTION, httpUpload.this);
            mParams.addBodyParameter("picname", "ys.jpg");
            //path必须带上后缀名
            mParams.setSaveFilePath(Constant.FILE_PATH+"/xUtils/ys.jpg");
            HttpManager.getInstance().downLoad(mParams, Urls.DOWN_LOAD_FLAG);
        }
    }

    @Override
    public void onSuccess(Object result, int taskflag) {
        /**
         * 上传文件回调
         */
        if (taskflag == Urls.UPLOAD_FILE_FLAG) {
            toast(result.toString());
        }
        /**
         * 传递json参数
         */
        if (taskflag == Urls.SEND_JSON_FLAG) {
            toast(result.toString());
        }
        /**
         * 文件下载的回调
         */
        if (taskflag == Urls.DOWN_LOAD_FLAG) {
            File mFile = (File) result;
            Log.e("path:", mFile.getAbsolutePath());
            toast("下载成功");
        }
    }

    @Override
    public void onFailure(String msg) {
        toast(msg);
    }

    @Override
    public void onTaskStart() {
        //开始没有进度的进度条
    }

    @Override
    public void onLoading(long total, long current) {
        Log.e("大小:" + total, "当前:" + current);
    }

}
