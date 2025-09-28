package com.example.scsrabbitmqdemo.test;

/**
 * @desc:
 * @time: 2025-07-18 11:05:48
 * @author: Alina
 */

/**
 * @author Alina
 */

public class Worker implements Runnable {
    private boolean running = true;

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            // 模拟工作
            System.out.println("Worker is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, Failed to complete operation");
            }
        }
        System.out.println("Worker has stopped.");
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        Thread workerThread = new Thread(worker);
        workerThread.start();

        Thread.sleep(2000); // 让 worker 线程运行一会儿

        System.out.println("Main thread will stop the worker thread.");
        worker.stop();
    }
}