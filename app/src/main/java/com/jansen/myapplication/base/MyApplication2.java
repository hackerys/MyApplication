package com.jansen.myapplication.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created Jansen on 2016/4/25.
 */
public class MyApplication2 extends Application implements DbManager.DbOpenListener{
    private DbManager.DaoConfig mDaoConfig;
    public static final String DB_NAME = "ys.db";
    public static final String DB_PATH = "/sdcard/mydb/";
    public static final int DB_VERSION = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        //        CrashHandler.getInstance().init(this);
        initImageLoader(getApplicationContext());
        Stetho.initializeWithDefaults(this);
        //xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
        mDaoConfig = new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File(DB_PATH))
                .setDbVersion(DB_VERSION)
                .setDbOpenListener(this);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public void onDbOpened(DbManager db) {
        // 开启WAL, 对写入加速提升巨大
        db.getDatabase().enableWriteAheadLogging();
    }

    public DbManager.DaoConfig getDaoConfig() {
        return mDaoConfig;
    }

    public void setDaoConfig(DbManager.DaoConfig mDaoConfig) {
        this.mDaoConfig = mDaoConfig;
    }
}
