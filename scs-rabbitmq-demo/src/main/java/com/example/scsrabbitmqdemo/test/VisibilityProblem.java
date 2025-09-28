package com.example.scsrabbitmqdemo.test;

/**
 * @desc:
 * @time: 2025-07-18 11:10:31
 * @author: Alina
 */

public class VisibilityProblem implements Runnable {
    // 没有 volatile 关键字
    private boolean running = true;
    private long count = 0;

    public void stop() {
        System.out.println("Main thread is setting running to false.");
        running = false;
    }

    public long getCount() {
        return count;
    }

    @Override
    public void run() {
        System.out.println("Worker thread has started.");
        // 这是一个“高速空转”循环，没有任何可能触发内存刷新的操作
        while (running) {
            // 我们在这里做一个简单的计算，防止JIT将整个循环优化掉
            count++;
        }
        System.out.println("Worker thread has stopped. Final count: " + count);
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityProblem worker = new VisibilityProblem();
        Thread workerThread = new Thread(worker);
        workerThread.start();

        // 让 worker 线程运行1秒，足以让JIT编译器将循环编译为本地代码
//        Thread.sleep(1000);

        worker.stop(); // 主线程修改 running 的值

        // 等待2秒，看看 worker 线程是否会停止
        workerThread.join(2000);

        // 检查线程是否还活着
        if (workerThread.isAlive()) {
            System.out.println("-------------------------------------------");
            System.out.println("FAILURE: Worker thread is still alive!");
            System.out.println("The change to 'running' was not visible.");
            System.out.println("Current count: " + worker.getCount());
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("-------------------------------------------");
            System.out.println("SUCCESS: Worker thread has stopped.");
            System.out.println("-------------------------------------------");
        }
    }
}