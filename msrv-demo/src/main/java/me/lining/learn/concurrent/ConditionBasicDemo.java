package me.lining.learn.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lining
 * @date 2026/03/31 16:48
 */
public class ConditionBasicDemo {
    private final Lock lock = new ReentrantLock();
    // 两个独立条件
    private final Condition notFull = lock.newCondition();   // 不满：生产者等待
    private final Condition notEmpty = lock.newCondition(); // 不空：消费者等待

    private int count = 0;
    private final int MAX = 5;
    //
    public void produce() throws InterruptedException {
        lock.lock();
        try {
            while (count >= MAX) {
                notFull.wait();
            }
            count++;
            System.out.println("生产者生产1，剩余: " + count);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (count <= 0) {
                notEmpty.wait();
            }
            count--;
            System.out.println("消费者消费1，剩余: " + count);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionBasicDemo demo = new ConditionBasicDemo();
        new Thread(() -> {
            while (true) {
                try {
                    demo.consume();
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    demo.produce();
                    Thread.sleep(800);
                } catch (Exception e) {
                }
            }
        }).start();
    }
}