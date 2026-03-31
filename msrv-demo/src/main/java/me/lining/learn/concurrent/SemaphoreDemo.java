package me.lining.learn.concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author lining
 * @date 2026/03/31 16:33
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    //阻塞
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 获取信号量");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " 释放信号量");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "线程" + i).start();
        }
    }
}
