package com.hmdp.utils;

public class Test {

    public void doWork() throws InterruptedException {
        System.out.println("开始工作");
        // 线程休眠5s
        Thread.sleep(5000);
        System.out.println("工作完成！");
    }

    public static void main(String[] args) {

        Test example = new Test();
        try {
            example.doWork();
        } catch (InterruptedException e) {
            // 线程休眠就会执行
            System.out.println("工作被中断！");
            // 恢复中断状态
            Thread.currentThread().interrupt();
        }

    }

}
