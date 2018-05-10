package com.kk.api;

import com.kk.api.entity.*;
import com.kk.api.entity.useraction.echarts;
import com.kk.api.mapper.UserActionMapper;
import com.kk.api.service.ICommentService;
import com.kk.api.service.IMomentService;
import com.kk.api.service.ISongService;
import com.kk.api.service.IUserService;
import com.kk.api.task.TaksDemo;
import com.kk.api.task.TaksDemo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.method.P;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsApiApplicationTests {

    @Autowired
    private ISongService songService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IMomentService momentService;

    @Autowired
    private IUserService userService;
    @Autowired
    private TaksDemo taskDemo;
    @Autowired
    private TaksDemo2 taksDemo2;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSelectOne() {
        Song song = songService.selectById(1);
        System.out.print("******" + song.toString());
    }

    @Test
    public void deleteCTQTest() {
        List<String> iditems = Arrays.asList("999", "998", "997");
        songService.deleteCTQ(iditems);
    }

    /**
     * 完成任务，耗时：1134毫秒
     * 批量跟心数据库的方式可以在一次数据库连接中更新所有数据，避免了频繁数据库建立和断开连接的开销
     * 但是一旦出错，所有的更新将自动回滚。而且通常这种方式也更容易出错。
     */
    @Test
    public void updateCTQTest() {
        List<Song> list = new ArrayList<>();
        Song song = new Song(1000, "二框", "歌手二框");
        Song song1 = new Song(1001, "二框", "歌手二框");
        Song song2 = new Song(1002, "二框", "歌手二框");
        list.add(song);
        list.add(song1);
        list.add(song2);
        System.out.println("开始做任务");
        long start = System.currentTimeMillis();
        songService.updateCTQ(list);
        long end = System.currentTimeMillis();
        System.out.println("完成任务，耗时：" + (end - start) + "毫秒");

    }

    @Test
    public void updateListTest() {
        List<Song> list = new ArrayList<>();
        Song song = new Song(1000, "二框", "歌手二框");
        Song song1 = new Song(1001, "二框", "歌手二框");
        Song song2 = new Song(1002, "二框", "歌手二框");
        list.add(song);
        list.add(song1);
        list.add(song2);
        System.out.println("开始做任务");
        long start = System.currentTimeMillis();
        songService.updateById(song);
        songService.updateById(song1);
        songService.updateById(song2);
        long end = System.currentTimeMillis();
        System.out.println("完成任务，耗时：" + (end - start) + "毫秒");
    }

    @Test
    public void getMessage() {
        List<Comment> commentList = commentService.selectListAll();
        List<Moment> momentList = momentService.selectListAll();
    }

    @Test
    public void testAsync() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1 = taskDemo.doTaskOne();
        Future<String> task2 = taskDemo.doTaskTwo();
        Future<String> task3 = taskDemo.doTaskThree();

        while (true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            // Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }

    /**
     * 实测
     * 完成任务一、二、三，耗时：948毫秒
     * 当数据超过1000
     * 完成任务一、二、三，耗时：1052毫秒
     * 当数据超过10000
     *
     * @throws Exception
     */
    @Test
    public void testSync() throws Exception {
        System.out.println("开始做任务一、二、三的同步");
        long start = System.currentTimeMillis();
        List<Comment> commentList = commentService.selectListAll();
        List<Comment> momentList = commentService.getMomentItem();
        List<User> userList = userService.selectListAll();
        //List<Song> songList = songService.selectListAll();
        long end = System.currentTimeMillis();
        System.out.println("完成任务一、二、三，耗时：" + (end - start) + "毫秒");
    }

    /**
     * 完成任务一，耗时：2毫秒
     * 完成任务二，耗时：2毫秒
     * 完成任务三，耗时：2毫秒
     * 实测16毫秒
     * <p>
     * 当查询数据超过1000
     * 完成任务三，耗时：3毫秒
     * 完成任务一，耗时：4毫秒
     * 完成任务四，耗时：3毫秒
     * 完成任务二，耗时：4毫秒
     * 任务全部完成，总耗时：20毫秒
     * 当查询数据超过10000
     * 完成任务四，耗时：7毫秒
     * 完成任务二，耗时：7毫秒
     * 完成任务三，耗时：8毫秒
     * 完成任务一，耗时：8毫秒
     * 任务全部完成，总耗时：10毫秒
     *
     * @throws Exception
     */
    @Test
    public void testAsync2() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<List<Comment>> task1 = taksDemo2.dotaskOne();
        CompletableFuture<List<Comment>> task2 = taksDemo2.doTaskTwo();
        CompletableFuture<List<User>> task3 = taksDemo2.doTaskThree();
        CompletableFuture<List<Song>> task4 = taksDemo2.doTaskFour();
        long end = System.currentTimeMillis();
        System.out.println(task1.get() + "::" + task2.get() + "::" + task3.get());
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

    @Test
    public void testComplableFutureApi() throws ExecutionException, InterruptedException {
        CompletableFuture<String> taskFive = taksDemo2.doTaskFive_combine();
        System.out.println(taskFive.get());
    }

    @Test
    public void insertSong() {
        for (int i = 0; i < 10000; i++) {
            Song song = new Song("歌手框框", "框框王");
            songService.insert(song);
        }

    }

    @Test
    public void testThreadLamda() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("=============Before java 8============\n");
            }
        }).start();

        new Thread(() -> System.out.print("=============After java 8 lamda===========\n")).start();
    }

    /**
     * 使用lamda表达式对列表进行迭代
     */
    @Test
    public void testListThreadLamda() {
        // Java 8之前：
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.println(feature);
        }

        // Java 8之后
        List<String> features_lamda = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features_lamda.forEach(n -> System.out.print("java 8" + n + "\n"));
        features_lamda.forEach(System.out::println);
    }

    /**
     * 使用lamda表达式进行函数式接口断言
     * 使用 java.util.function.Predicate 函数式接口以及lambda表达式，可以向API方法添加逻辑，用更少的代码支持更多的动态行为。
     */
    @Test
    public void testPredicate() {
        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));
        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> str.endsWith("a"));
        System.out.println("Print all languages :");
        filter(languages, (str) -> true);
        System.out.println("Print no language : ");
        filter(languages, (str) -> false);
        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> str.length() > 4);

        // 甚至可以用and()、or()和xor()逻辑函数来合并Predicate，
        // 例如要找到所有以J开始，长度为四个字母的名字，你可以合并两个Predicate并传入
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        languages.stream()
                .filter(startsWithJ.and(fourLetterLong))
                .forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
    }

    public static void filter(List<String> list, Predicate<String> predicate) {
        for (String name : list) {
            if (predicate.test(name)) {
                System.out.println(name + "  ");
            }
        }
    }

    /**
     * lambda表达式传到 map() 方法，后者将其应用到流中的每一个元素。
     * map将集合类（例如列表）元素进行转换
     */
    @Test
    public void testLamdaMap() {
        // 不使用lambda表达式为每个订单加上12%的税
        List<Integer> list = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : list) {
            double price = cost + .12 * cost;
            System.out.println(price);
        }
        System.out.println("============");
        // 使用lambda表达式
        List<Comment> commentList = commentService.selectListAll();
        Map<Integer, List<Comment>> map = commentList.stream().collect(Collectors.groupingBy(Comment::getId));
        System.out.println(map.toString());
        System.out.println("============");
        Map<Integer, List<String>> map1 = commentList.stream().collect(Collectors.groupingBy(Comment::getId, Collectors.mapping(Comment::getContent, Collectors.toList())));
        System.out.println(map1.toString());
        list.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);
    }

    /**
     * 与map()相反，将所有值合并成一个。reduce 又被称为折叠操作
     */
    @Test
    public void testReduce() {
        // 为每个订单加上12%的税
        // 老方法：
        List<Integer> list = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : list) {
            double price = cost + .12 * cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        // 新方法：
        double bill = list.stream().map((cost) -> cost + .12 * cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }

    /**
     * 列表转字符串的lamda简化
     */
    @Test
    public void testStringToList() {
        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");
        String G7Countries = G7.stream()/*.map(x -> x.toUpperCase())*/.collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    /**
     * 利用流的去重 distinct()
     */
    @Test
    public void testListDistinct() {
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    }

    /**
     * 利用流的计算集合元素的最大值、最小值、总和以及平均值
     * 返回 IntSummaryStatistics、LongSummaryStatistics 或者 DoubleSummaryStatistic s，描述流中元素的各种摘要数据
     */
    @Test
    public void testListMathFunction() {
        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }

    @Test
    public void getMometnItem() throws Exception {
        List<Comment> momentItemList = commentService.getMomentItem();
        Map<Moment, List<CommentItem>> map = momentItemList.stream().collect(Collectors.groupingBy(comment -> comment.getMoment(), Collectors.mapping(comment -> comment.getBase(), Collectors.toList())));
        map.forEach((key, value) -> {
                    List<User> pariseList = new ArrayList<>();

                    List<String> features_lamda = Arrays.asList(key.getPraiseuseridlist().substring(1, key.getPraiseuseridlist().length() - 1).split(", "));
                    features_lamda.stream().forEach(userid -> pariseList.add(userService.selectById(Integer.valueOf(userid))));
                    System.out.println(pariseList.toString());
                }
        );
        System.out.println("========" + map.size());


        System.out.println(map.toString());
    }

    @Autowired
    private UserActionMapper actionMapper;

    @Test
    public void selectTop10Test() {
        List<echarts> echarts = actionMapper.selectTop10();
        System.out.println("top结果" + echarts.toString());
    }

    @Test
    public void selectPurchseMouth() {
        List<Integer> echarts = actionMapper.selectPurchasMouth();
        System.out.println(echarts.toString());
    }

    @Test
    public void selectBehaviorTest() {
        List<echarts> echart = actionMapper.selectBehavior();
        echart.stream().forEach(ec -> {
            if (ec.getName().equals("1")) {
                ec.setName("浏览");
            } else if (ec.getName().equals("2")) {
                ec.setName("收藏");
            } else if (ec.getName().equals("3")) {
                ec.setName("添加购物车");
            } else {
                ec.setName("购买");
            }
        });
        System.out.println(echart.toString());
    }
}
