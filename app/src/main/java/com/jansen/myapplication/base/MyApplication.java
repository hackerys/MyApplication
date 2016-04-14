package com.jansen.myapplication.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jansen.myapplication.utils.FixBugManage;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created Jansen on 2016/3/30.
 */
public class MyApplication extends Application implements DbManager.DbOpenListener, DbManager.DbUpgradeListener {
    private DbManager.DaoConfig mDaoConfig;
    public static final String DB_NAME = "ys.db";
    public static final String DB_PATH = "/sdcard/mydb/";
    public static final int DB_VERSION = 1;
    private FixBugManage mFixBugManage;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
        mDaoConfig = new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                        // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File(DB_PATH))
                .setDbVersion(DB_VERSION)
                .setDbOpenListener(this)
                .setDbUpgradeListener(this);
        //热修复初始化
        mFixBugManage = new FixBugManage(this);
        mFixBugManage.init("1");
        //加载hack.dex
        File patch = new File(
                Environment.getExternalStorageDirectory(), "hack.jar");
        Log.e("file:", "" + patch.exists());
        mFixBugManage.addPatch(patch.getAbsolutePath());
        //crashhandler
        CrashHandler.getInstance().init(this);
    }

    @Override
    public void onDbOpened(DbManager db) {
        // 开启WAL, 对写入加速提升巨大
        db.getDatabase().enableWriteAheadLogging();
    }

    /**
     * 数据库升级所要进行的操作
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
        // TODO: ...
        // db.addColumn(...);
        // db.dropTable(...);
        // ...
        // or
        // db.dropDb();
    }

    public DbManager.DaoConfig getDaoConfig() {
        return mDaoConfig;
    }

    public void setDaoConfig(DbManager.DaoConfig mDaoConfig) {
        this.mDaoConfig = mDaoConfig;
    }

    public FixBugManage getFixBugManage() {
        return mFixBugManage;
    }
}
