package com.jansen.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created Jansen on 2016/5/19.
 */
public class ImageLoaderTest extends Activity {
    private ImageView mView;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_load_layout);
        mView = (ImageView) findViewById(R.id.img);
        String usrl = "http://p0.so.qhimg.com/t01a2a9f6f224453560.jpg";
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
        ImageLoader.getInstance().displayImage(usrl, mView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.e("onLoadingComplete", "onLoadingComplete");
            }

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.e("onLoadingStarted", "onLoadingStarted");
            }
        });
    }
}
