package com.jansen.myapplication.thread;

/**
 * Created Jansen on 2016/7/19.
 */
public class Task1 implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread()+":"+i);
            }
        }
    }

    public static void main(String[] args) {
        Task1 mTask1 = new Task1();
        new Thread(mTask1).start();
        new Thread(mTask1).start();
    }
}
