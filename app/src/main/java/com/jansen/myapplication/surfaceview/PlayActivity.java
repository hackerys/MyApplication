package com.jansen.myapplication.surfaceview;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.jansen.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/5/31.
 */
public class PlayActivity extends Activity {
    @Bind(R.id.play)
    Button mPlay;
    @Bind(R.id.pause)
    Button mPause;
    @Bind(R.id.stop)
    Button mStop;
    @Bind(R.id.reset)
    Button mReset;
    @Bind(R.id.sur)
    SurfaceView mSur;
    @Bind(R.id.progress)
    SeekBar mProgress;
    @Bind(R.id.container)
    LinearLayout mContainer;
    //视屏播放器
    private MediaPlayer mPlayer;
    //记录播放位置
    private int position;
    private SurfaceHolder mHolder;
    //文件名
    public static final String FILE_NAME = "movie1.mp4";
    //进度定时
    private Timer mTimer;
    private TimerTask mTimerTask;
    //互斥变量，防止定时器与SeekBar拖动时进度冲突
    private boolean isChanging = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        ButterKnife.bind(this);
        mHolder = mSur.getHolder();
        //添加surface监听器
        mHolder.addCallback(new SurceCallBack());
        //设置分辨率
        mHolder.setFixedSize(176, 144);
        //设置surfaceview不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到用户面前
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mPlayer = new MediaPlayer();
        mProgress.setOnSeekBarChangeListener(new MySeekbar());
    }

    @OnClick({R.id.play, R.id.pause, R.id.stop, R.id.reset, R.id.sur, R.id.progress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                play();
                break;
            case R.id.pause:
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                } else {
                    mPlayer.start();
                }
                break;
            case R.id.stop:
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                break;
            case R.id.reset:
                if (mPlayer.isPlaying()) {
                    mPlayer.seekTo(position);
                } else {
                    mPlayer.start();
                }
                break;
            case R.id.progress:
                break;
        }
    }

    private void play() {
        //播放本地视屏
        File mFile = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
        //重置为初始状态
        mPlayer.reset();
        //设置音乐流的类型
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //使用surfaceview播放
        mPlayer.setDisplay(mHolder);
        //播放文件的路径
        try {
            mPlayer.setDataSource(mFile.getAbsolutePath());
            mPlayer.prepare();
            //先设置设置进度，在播放
            mProgress.setMax(mPlayer.getDuration());
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (isChanging) {
                        return;
                    }
                    mProgress.setProgress(mPlayer.getCurrentPosition());
                }
            };
            //每隔10毫秒执行一次task
            mTimer.schedule(mTimerTask, 0, 10);
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //进度条处理

    /**
     * 对进度条的监听
     */
    class MySeekbar implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            isChanging = true;
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            mPlayer.seekTo(mProgress.getProgress());
            isChanging = false;
        }
    }

    /**
     * 监听surface的创建和销毁
     */
    private class SurceCallBack implements SurfaceHolder.Callback {
        /**
         * 画面修改
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            // TODO Auto-generated method stub

        }

        /**
         * 画面创建
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (position > 0) {
                play();
                mPlayer.seekTo(position);
                position = 0;
            }

        }

        /**
         * 画面销毁
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mPlayer.isPlaying()) {
                position = mPlayer.getCurrentPosition();
                mPlayer.stop();
            }
        }
    }

    //来电处理
    protected void onDestroy() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.release();
        }
        super.onDestroy();
    }

    protected void onPause() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        super.onPause();
    }

    protected void onResume() {
        if (mPlayer != null) {
            if (!mPlayer.isPlaying()) {
                mPlayer.start();
            }
        }
        super.onResume();
    }
}
