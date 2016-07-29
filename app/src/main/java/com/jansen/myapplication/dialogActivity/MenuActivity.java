package com.jansen.myapplication.dialogActivity;

import android.app.Activity;
import android.os.Bundle;

import com.jansen.myapplication.R;

import java.io.FileDescriptor;
import java.io.FileOutputStream;

/**
 * Created Jansen on 2016/7/4.
 */
public class MenuActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bototm_view_layout);
        FileOutputStream fos=new FileOutputStream(new FileDescriptor());
        
    }
}
