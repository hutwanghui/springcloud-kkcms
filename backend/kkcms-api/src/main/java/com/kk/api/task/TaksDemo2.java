package com.kk.api.task;

import com.kk.api.entity.Comment;
import com.kk.api.entity.Moment;
import com.kk.api.entity.Song;
import com.kk.api.entity.User;
import com.kk.api.service.ICommentService;
import com.kk.api.service.IMomentService;
import com.kk.api.service.ISongService;
import com.kk.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by msi- on 2018/2/16.
 */
@Component
public class TaksDemo2 {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IMomentService momentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISongService songService;

    /**
     * 创建CompletableFuture对象。
     * public static CompletableFuture<Void> 	runAsync(Runnable runnable)
     * 会使用ForkJoinPool.commonPool()作为它的线程池执行异步代码
     * public static CompletableFuture<Void> 	runAsync(Runnable runnable, Executor executor)
     * 以Runnable函数式接口类型为参数，所以CompletableFuture的计算结果为空
     * public static <U> CompletableFuture<U> 	supplyAsync(Supplier<U> supplier)
     * public static <U> CompletableFuture<U> 	supplyAsync(Supplier<U> supplier, Executor executor)
     * 计算结果完成时的处理
     * public CompletableFuture<T> 	whenComplete(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T> 	whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T> 	whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
     * public CompletableFuture<T>     exceptionally(Function<Throwable,? extends T> fn)
     * Action的类型是BiConsumer<? super T,? super Throwable>，它处理正常的计算结果，或者异常情况。
     * 方法不以Async结尾，意味着Action使用相同的线程执行，而Async可能会使用其它的线程去执行(如果使用相同的线程池，也可能会被同一个线程选中执行)。
     *
     * @return
     * @throws Exception
     */

    @Async
    public CompletableFuture<List<Comment>> dotaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
       /* Thread.sleep(Random.class.newInstance().nextInt(10000));*/
        CompletableFuture<List<Comment>> future = CompletableFuture.supplyAsync(() -> {
            return commentService.selectListAll();
        });
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return future;
    }

    @Async
    public CompletableFuture<List<Comment>> doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        CompletableFuture<List<Comment>> future = CompletableFuture.supplyAsync(() -> {
            return commentService.getMomentItem();
        });
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
        return future;
    }


    @Async
    public CompletableFuture<List<User>> doTaskThree() throws Exception {

        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        CompletableFuture<List<User>> future = CompletableFuture.supplyAsync(() -> {
            return userService.selectListAll();
        });
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
        return future;
    }

    @Async
    public CompletableFuture<List<Song>> doTaskFour() {
        System.out.println("开始做任务四");
        long start = System.currentTimeMillis();
        CompletableFuture<List<Song>> future = CompletableFuture.supplyAsync(() -> {
            return songService.selectListAll();
        });
        long end = System.currentTimeMillis();
        System.out.println("完成任务四，耗时：" + (end - start) + "毫秒");
        return future;
    }

    @Async
    public CompletableFuture<String> doTaskFive_combine() {
        System.out.println("开始做任务四");
        long start = System.currentTimeMillis();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return "world";
        }), (s1, s2) -> s1 + "::" + s2);
        long end = System.currentTimeMillis();
        System.out.println("完成任务四，耗时：" + (end - start) + "毫秒");
        return future;

    }
}
