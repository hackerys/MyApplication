package com.jansen.myapplication.inputfilter;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jansen.myapplication.R;
import com.jansen.myapplication.utils.WnLogsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/6/23.
 */
public class FilterTest extends Activity implements View.OnFocusChangeListener {
    @Bind(R.id.edit)
    EditText mEdit;
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.con)
    LinearLayout mCon;
    private InputFilter[] mFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        ButterKnife.bind(this);
        initFilter();
        mEdit.setFilters(mFilters);
        mEdit.setOnFocusChangeListener(this);
        mButton.setOnFocusChangeListener(this);
        mCon.setOnFocusChangeListener(this);
    }

    private void initFilter() {
        mFilters = new InputFilter[]{
                new InputFilter.LengthFilter(5)
                , new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (".".equals(source) && dstart == 0) {
                    source = "0.";
                }
                return source;
            }
        }
        };
    }

    @OnClick({R.id.edit, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit:
                break;
            case R.id.button:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.button) {
            WnLogsUtils.e("button:" + hasFocus);
        }
        if (v.getId() == R.id.edit) {
            WnLogsUtils.e("edit:" + hasFocus);
        }
        if (v.getId() == R.id.con) {
            WnLogsUtils.e("mCon:" + hasFocus);
        }
    }
}
