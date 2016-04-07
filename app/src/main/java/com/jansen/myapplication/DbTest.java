package com.jansen.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.jansen.myapplication.adapter.StringAdapter;
import com.jansen.myapplication.base.BaseActivity;
import com.jansen.myapplication.bean.Child;
import com.jansen.myapplication.bean.Parent;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DbTest extends BaseActivity {
    @Bind(R.id.save)
    Button mSave;
    @Bind(R.id.read)
    Button mRead;
    private ListView mListView;
    private StringAdapter mAdapter;
    private ArrayList<String> titles;
    private Button mhaha;
    private String toastTip = "toast in DbTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initeData() {

    }

    @Override
    public void initeView() {

    }

    @Override
    public void initeListener() {

    }


    @OnClick({R.id.save, R.id.read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                try {
                    Parent mParent = new Parent();
                    mParent.setEmail("904042749@qq.com");
                    mParent.setName("言胜");
                    mParent.setDate(new Date());
                    db.save(mParent);

                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.read:
                try {
                    Parent mFirst = db.selector(Parent.class).findFirst();
                    if (mFirst != null) {
                        Log.e("Parent", JSON.toJSONString(mFirst));
                        Child mChild = new Child();
                        mChild.setParentId(mFirst.getId());
                        mChild.setName("儿子");
                        mChild.setEmail("904042749@qq.com");
                        db.save(mChild);
                        Child mChild1 = db.selector(Child.class).findFirst();
                        if (mChild1 != null) {
                            Log.e("Child", JSON.toJSONString(mChild1));
                            Log.e("Child-Parent", JSON.toJSONString(mChild1.getParent(db)));
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
