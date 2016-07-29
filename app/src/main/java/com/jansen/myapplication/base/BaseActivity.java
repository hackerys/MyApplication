package com.jansen.myapplication.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created Jansen on 2016/3/28.
 */
public abstract class BaseActivity extends FragmentActivity {
    private MyApplication2 mApplication;
    public DbManager.DaoConfig daoConfig;
    public DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mApplication = (MyApplication2) getApplication();
        daoConfig = mApplication.getDaoConfig();
        db = x.getDb(daoConfig);
        initeData();
        initeView();
        initeListener();
    }

  //  public abstract void setContentView();

    public abstract void initeData();

    public abstract void initeView();

    public abstract void initeListener();

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
