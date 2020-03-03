package day09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 连接池
 */
public class JDBCJava {
    public static void main(String[] args) {
//        创建线程池（单线程）
        ExecutorService service = Executors.newSingleThreadExecutor();
//创建连接池（固定线程）
        ExecutorService pool = Executors.newFixedThreadPool(5);
//        创建连接池(多线程)
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
//                    打印当前线程名称
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}



