package com.jansen.myapplication.clip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jansen.myapplication.R;
import com.jansen.myapplication.config.Constant;
import com.jansen.myapplication.utils.ImageUtils;

import java.io.File;

/**
 * Created Jansen on 2016/4/13.
 */
public class ClipActivity extends Activity implements View.OnClickListener {
    private ClipImageLayout mClipImageLayout;
    private TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clip_layout);
        init();
        setListener();
        initData();
    }

    private void initData() {
        Intent mIntent = getIntent();
        if (mIntent == null) {
            return;
        }
        Bundle mBundle = mIntent.getExtras();
        if (mBundle == null) {
            return;
        }
        //初始化图片存储目录
        File savePath = new File(Constant.PIC_SAVE_PATH);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        String path = mIntent.getStringExtra(Constant.PIC_TO_CLIP_PATH);
        Bitmap mBitmap = ImageUtils.getPixCompressedImage(path, 440, 440);
        ImageUtils.toturn(mBitmap, ImageUtils.readPictureDegree(path));
        mClipImageLayout.setImageClipBitmap(mBitmap);
    }


    private void setListener() {
        save.setOnClickListener(this);
    }

    private void init() {
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.clip_layout);
        save = (TextView) findViewById(R.id.save);
    }

    @Override
    public void onClick(View v) {
        /**
         * 保存
         */
        if (v.getId() == R.id.save) {
            transmitBitmap();
        }
    }

    public void transmitBitmap() {
        Bitmap bitmap = mClipImageLayout.clip();
        File mFile = new File(Constant.PIC_SAVE_PATH, System.currentTimeMillis() + "_upload.jpg");
        ImageUtils.qualityCompress(mFile, bitmap, Constant.MAX_PIC_SIZE);
        Intent intent = getIntent();
        intent.putExtra(Constant.CLIP_TO_SHOW_PATH, mFile.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();
    }
}
