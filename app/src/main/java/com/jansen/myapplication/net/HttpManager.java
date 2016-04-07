package com.jansen.myapplication.net;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created Jansen on 2016/3/30.
 */
public class HttpManager {
    public static HttpManager mManager;
    private NetListener mNetListener;

    public static HttpManager getInstance() {
        if (mManager == null) {
            mManager = new HttpManager();
        }
        return mManager;
    }

    /**
     * post请求
     *
     * @param mParams
     * @param taskFlag
     */
    public void post(final RequestParams mParams, final int taskFlag) {
        if (mNetListener != null) {
            mNetListener.onTaskStart();
        }
        x.http().post(mParams, new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                if (mNetListener != null) {
                    mNetListener.onSuccess(result, taskFlag);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (mNetListener != null) {
                    mNetListener.onFailure(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 上传文件
     *
     * @param mParams
     * @param taskFlag
     */
    public void LoadFile(final RequestParams mParams, final int taskFlag) {
        if (mNetListener != null) {
            mNetListener.onTaskStart();
        }
        x.http().post(mParams, new Callback.ProgressCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (mNetListener != null) {
                    mNetListener.onSuccess(result, taskFlag);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (mNetListener != null) {
                    mNetListener.onFailure(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (mNetListener != null) {
                    mNetListener.onLoading(total, current);
                }
            }
        });
    }

    public void downLoad(final RequestParams mParams, final int taskFlag) {
        if (mNetListener != null) {
            mNetListener.onTaskStart();
        }
        x.http().post(mParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                if (mNetListener != null) {
                    mNetListener.onSuccess(result, taskFlag);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (mNetListener != null) {
                    mNetListener.onFailure(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (mNetListener != null) {
                    mNetListener.onLoading(total, current);
                }
            }
        });
    }

    public NetListener getNetListener() {
        return mNetListener;
    }

    public void setNetListener(NetListener mNetListener) {
        this.mNetListener = mNetListener;
    }
}
