package com.jansen.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import org.xutils.common.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created Jansen on 2016/4/13.
 */
public class ImageUtils {
    /**
     * 分辨率压缩
     *
     * @param path      图片路径
     * @param reqWidth  需要压缩的宽度
     * @param reqHeight 需要压缩的高度
     * @return 压缩后的bitmap
     */
    public static Bitmap getPixCompressedImage(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,mOptions);
        mOptions.inSampleSize = calcInsampleSize(mOptions, reqWidth, reqHeight);
        mOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path,mOptions);
    }

    /**
     * 计算采样率 1不变，2为原来分辨率的1/2;
     *
     * @param mOptions
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calcInsampleSize(BitmapFactory.Options mOptions, int reqWidth, int reqHeight) {
        int radio = 1;
        int width = mOptions.outWidth;
        int height = mOptions.outHeight;

		/*
         * heightRatio是图片原始高度与压缩后高度的倍数，widthRatio是图片原始宽度与压缩后宽度的倍数。
		 * inSampleSize为heightRatio与widthRatio中最小的那个，inSampleSize就是缩放值。
		 * inSampleSize为1表示宽度和高度不缩放，为2表示压缩后的宽度与高度为原来的1/2
		 */
        if (reqWidth < width || reqHeight < height) {
            int widthRadio = (int) ((float) width / (float) reqWidth);
            int heightRadio = (int) ((float) height / (float) reqHeight);
            radio = widthRadio > heightRadio ? heightRadio : widthRadio;
        }
        LogUtil.e(""+radio);
        return radio;
    }

    /**
     * 旋转图片
     *
     * @param img
     * @param degrees
     * @return
     */
    public static Bitmap toturn(Bitmap img, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(+degrees); /*翻转90度*/
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path
     * @return 照片路径
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * @param mFile
     * @param mBitmap
     * @param reqQuality 压缩图片大小的上限
     */
    public static void qualityCompress(File mFile, Bitmap mBitmap, int reqQuality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while ((baos.toByteArray().length / 1024) > reqQuality) {
            baos.reset();
            quality -= 10;
            mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            LogUtil.e((baos.toByteArray().length / 1024 + "kb"));
        }

        try {
            FileOutputStream fos = new FileOutputStream(mFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
