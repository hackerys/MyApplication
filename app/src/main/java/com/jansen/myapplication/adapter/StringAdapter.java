package com.jansen.myapplication.adapter;

import android.content.Context;

import com.jansen.myapplication.R;
import com.jansen.myapplication.base.CommonAdapter;
import com.jansen.myapplication.base.ViewHolder;

import java.io.Serializable;
import java.util.List;

/**
 * Created Jansen on 2016/3/28.
 */
public class StringAdapter extends CommonAdapter<String> implements Serializable{
    public StringAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String t, int position) {
        holder.setText(R.id.mTitle, t);
    }
}
