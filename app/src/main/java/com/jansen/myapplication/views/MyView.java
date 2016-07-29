package com.jansen.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.jansen.myapplication.R;
import com.jansen.myapplication.utils.WnLogsUtils;

/**
 * Created Jansen on 2016/7/21.
 */
public class MyView extends View {
    private Paint textPaint;
    private Paint circlePaint;
    private GestureDetector mDetector;
    private Scroller mScroller;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyView, 0, 0);
        try {
            boolean showTest = mArray.getBoolean(R.styleable.MyView_showTest, true);
            int labelPosition = mArray.getInteger(R.styleable.MyView_labelPosition, 1);
            WnLogsUtils.e("showTest" + showTest);
            WnLogsUtils.e("labelPosition" + labelPosition);

        } catch (Exception mE) {
            mE.printStackTrace();
        } finally {
            mArray.recycle();
        }
        mScroller=new Scroller(context);
        setLayerType(View.LAYER_TYPE_HARDWARE,null);
        init();
    }

    private void init(){
        mDetector = new GestureDetector(MyView.this.getContext(), new mListener());

        textPaint=new Paint();
        textPaint.setColor(Color.RED);

        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.YELLOW);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            WnLogsUtils.e("onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //fasle表示mDetector没有辨认出来是什么手势
        boolean result=mDetector.onTouchEvent(event);
        //没有辨认出来停止先停止手势
        if (!result){
          //  stopscrolling();
            result=true;
        }
        return result;
    }
}
