package me.lining.learn.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * @author lining
 * @date 2026/03/31 16:29
 */

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "等待");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行");
            }, "线程" + i).start();
        }
    }
}
