package jansen.com.calclibrary;

import java.util.Objects;

/**
 * Created Jansen on 2016/7/19.
 */
public class SynDemo {
    private static final Object mObject = new Object();

    private static class TaskA implements Runnable {

        @Override
        public void run() {
            synchronized (mObject) {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread() + ":" + i);
                }
            }
        }
    }

    private static class TaskB implements Runnable {

        @Override
        public void run() {
            synchronized (mObject) {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread() + ":" + i);
                }
            }
        }
    }

    public static void main(String[] args) {
        TaskA mTaskA = new TaskA();
        TaskB mTaskB = new TaskB();
        new Thread(mTaskA).start();
        new Thread(mTaskB).start();
    }
}
