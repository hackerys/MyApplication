package com.jansen.myapplication;

import android.util.Log;

/**
 * Created Jansen on 2016/4/6.
 */
public class A {
    public A() {
    }

    public static void mark() {
        B mB = new B();
        Log.e("A",mB.bug());
    }
}
