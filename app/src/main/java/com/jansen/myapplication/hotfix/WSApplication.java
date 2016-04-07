package com.jansen.myapplication.hotfix;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;

import com.jansen.myapplication.hotfix.patch.FixBugManage;
import com.jansen.myapplication.hotfix.patch.WNPatchFileManage;
import com.jansen.myapplication.utils.AppInfo;

import java.io.File;

/**
 * Created by hmz on 2015/9/1.
 */
public class WSApplication extends Application {
    public Vibrator mVibrator;
    public static String lastUrlForLottory;
    private static FixBugManage fixBugManage;
    private WNPatchFileManage patchFileManage;
    public WSApplication() {
        super();
    }

  //  public static String htmlResPath = Environment.getExternalStorageDirectory().toString() + "/Android/data/" + "com.wn518.wnshangcheng" + File.separator + "H5Cache/";
 //   public static String htmlResPathTemp = Environment.getExternalStorageDirectory().toString() + "/Android/data/" + "com.wn518.wnshangcheng" + File.separator + "H5Cache/";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppInfo mInfo=new AppInfo(base);
        try {
            this.fixBugManage = new FixBugManage(base);
            patchFileManage = WNPatchFileManage.getInstance();
            patchFileManage.init(base, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int crashCount = this.patchFileManage.getKeyValueInt("hotpatch_crash");
            if (crashCount == 5) {
                this.patchFileManage.addMobileToServerBlackList();
                Log.e("attachBaseContext","把此设备加入服务器黑名单.");
                this.fixBugManage.removeAllPatch();
                this.patchFileManage.deleteHotFixPatch();
            } else if (crashCount < 5) {
                this.fixBugManage.init(mInfo.getVersionName() + "_" + mInfo.getVersionCode());
                Log.e("attachBaseContext", "初始化热修复成功.");
            } else if (crashCount > 5) {
                //不加载补丁，不做处理,如果有补丁文件就删除
                this.fixBugManage.removeAllPatch();
                this.patchFileManage.deleteHotFixPatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.fixBugManage.removeAllPatch();//出现异常立即清除已添加的补丁文件
            this.patchFileManage.deleteHotFixPatch();//仅删除热修复相关联的文件
            int crashCount = this.patchFileManage.getKeyValueInt("hotpatch_crash");
            if (crashCount < 5) {
                if (crashCount == -1) {
                    crashCount += 2;
                } else {
                    crashCount++;
                }
                this.patchFileManage.saveKeyValueInt("hotpatch_crash", crashCount);
                Log.e("attachBaseContext", "你妹，居然挂了：" + crashCount);
            }
//            this.patchFileManage.removeAllPatchFile();//删除所有热更新文件接口
        }
    }

    public static FixBugManage getFixBugManage() {
        return fixBugManage;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void initShare() {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Android/data/" + getPackageName() + File.separator + "WnCache");
        File file1 = new File(Environment.getExternalStorageDirectory().toString() + "/Android/data/" + getPackageName() + File.separator + "H5Cache");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file1.exists()) {
            file1.mkdirs();
        }
    }





    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
