package me.lining.learn.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author lining
 * @date 2026/03/31 16:41
 */
public class CounDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        // 设定计数器 = 3个任务
        CountDownLatch latch = new CountDownLatch(3);

        // 开启3个子线程执行任务
        for (int i = 1; i <= 3; i++) {
            int taskNo = i;
            new Thread(() -> {
                try {
                    System.out.println("任务" + taskNo + " 正在执行...");
                    Thread.sleep(1000); //模拟耗时业务
                    System.out.println("任务" + taskNo + " 执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 必须finally里调用，防止异常不计数
                    latch.countDown();
                }
            }).start();
        }

        System.out.println("主线程等待所有子任务完成...");
        // 阻塞：直到计数器变为0才往下走
        latch.await();
        System.out.println("✅ 所有任务全部结束，主线继续收尾工作");
    }
}
