package com.jansen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jansen.myapplication.clip.ClipActivity;
import com.jansen.myapplication.config.Constant;
import com.jansen.myapplication.utils.ImageUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/4/13.
 */
public class ChooseImage extends Activity {
    @Bind(R.id.gellary)
    Button mGellary;
    @Bind(R.id.camera)
    Button mCamera;
    @Bind(R.id.show)
    ImageView mShow;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_image);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 从相册选择返回的结果
         */
        if (resultCode == RESULT_OK && requestCode == Constant.REQUES_GELLARY) {
            Intent i = new Intent(this, ClipActivity.class);
            i.putExtra(Constant.PIC_TO_CLIP_PATH, getPickPhotoPath(this, data));
            startActivityForResult(i, Constant.REQUES_CUT);
        }
        /**
         * 拍照返回的结果
         */
        if (resultCode == RESULT_OK && requestCode == Constant.REQUE_CAMARA) {
            Intent i = new Intent(this, ClipActivity.class);
            Log.e("tempFile", tempFile.getPath());
            i.putExtra(Constant.PIC_TO_CLIP_PATH, tempFile.getPath());
            startActivityForResult(i, Constant.REQUES_CUT);
        }
        /**
         * 图片剪切后返回的结果
         */
        if (resultCode == RESULT_OK && requestCode == Constant.REQUES_CUT) {
            String tempUrl = data.getStringExtra(Constant.CLIP_TO_SHOW_PATH);
            mShow.setImageBitmap(ImageUtils.getPixCompressedImage(tempUrl, 320, 320));
        }
    }

    @OnClick({R.id.gellary, R.id.camera, R.id.show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gellary:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, Constant.REQUES_GELLARY);
                break;
            case R.id.camera:
                getImageFromCamera();
                break;
            case R.id.show:
                break;
        }
    }

    /**
     * 相册返回的路径
     *
     * @param activity
     * @param data
     * @return
     */
    public static String getPickPhotoPath(Activity activity, Intent data) {
        String path = "";
        try {
            Uri imageuri = data.getData();
            if (null != imageuri && imageuri.getScheme().compareTo("file") == 0) {
                path = imageuri.toString().replace("file://", "");
            } else {
                if (imageuri != null) {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = activity.managedQuery(imageuri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(column_index);
                    }
                }
            }
        } catch (SecurityException e) {

        }
        return path;
    }

    /**
     * 拍照
     */
    private void getImageFromCamera() {
        //初始化图片存储目录
        File savePath = new File(Constant.PIC_SAVE_PATH);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        tempFile = new File(Constant.PIC_SAVE_PATH,
                System.currentTimeMillis() + "_original.jpg");
        Log.e("tempFile", tempFile.getPath());
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(cameraintent, Constant.REQUE_CAMARA);
    }
}
