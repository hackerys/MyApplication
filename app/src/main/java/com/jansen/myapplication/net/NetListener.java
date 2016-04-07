package com.jansen.myapplication.net;


/**
 * Created Jansen on 2016/3/30.
 */
public interface NetListener {
    public void onSuccess(Object result, int taskflag);

    public void onFailure(String msg);

    public void onTaskStart();

    public void onLoading(long total, long current);
}
