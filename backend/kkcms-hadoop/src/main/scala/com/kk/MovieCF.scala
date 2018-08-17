package com.kk

import java.net.InetSocketAddress

import org.apache.spark.mllib.recommendation.{MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import org.apache.log4j.{Level, Logger}
import scala.io.Source

/**
  * Created by hutwanghui on 2018/7/14 13:39.
  * email:zjjhwanhui@163.com
  * qq:472860892
  * desc:
  */

class MovieCF extends Serializable {

}


object MovieCF {
  //批次累加
  //第一个参数：当前批次
  //第二个参数：以前的批次（因为有可能没有，比如第一次，使用Option）
  val func = (itemOld: Iterator[(String, Seq[Int], Option[Int])]) => {
    itemOld.flatMap {
      case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(m => (x, m))
    }
  }

  def main(args: Array[String]): Unit = {
    //创建SparkConf并设置为本地模式运行
    //注意local[2]代表开两个线程,因为是使用了Socket的链接方式，所以必须要双核消息的生产者和消费者两个线程
    val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
    //设置DStream批次时间间隔为2秒
    val ssc = new StreamingContext(conf, Seconds(2))
    //SQLContext要依赖SparkContext
    val sc = new SparkContext(conf)
    //创建SQLContext
    val sqlContext = new SQLContext(sc)
    //做checkpoint 写入共享存储中
    ssc.checkpoint("/home/spark/streaming")
    // Initial RDD input to updateStateByKey
    val initialRDD = ssc.sparkContext.parallelize(List(Rating(0, 7, 1.1), Rating(0, 8, 3.1)))
    //配置拉取的flume的绑定地址
    val address = Seq(new InetSocketAddress("192.168.0.100", 8190))
    val lines = FlumeUtils.createPollingStream(ssc, address, StorageLevel.MEMORY_AND_DISK)

    //updateStateByKey结果可以累加但是需要传入一个自定义的累加函数：updateFunc
    val wordCounts = lines.flatMap(x =>
      new String(x.event.getBody().array()).split("::"))
      .map(line => Rating(line(0).toInt, line(1).toInt, line(2).toDouble))


    val wordCount = lines.flatMap(x =>
      new String(x.event.getBody().array()).split(" "))
      .map(line => (line, 1))

    //开始计算
    ssc.start()
    //等待停止
    ssc.awaitTermination()
  }

  def getRe(personUrl: String, HomeDirPath: String, UserId: String): Array[Int] = {
    //屏蔽不必要的日志显示在终端上

    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)


    //设置运行环境
    val sparkConf = new SparkConf().setAppName("com.kk.MovieCF" + UserId)
      .setMaster("local[*]")
      .set("spark.executor.memory", "1g")
      .set("spark.testing.memory", "2147480000")
      .set("spark.driver.allowMultipleContexts", "true")

    val sc = new SparkContext(sparkConf)
    sc.addJar("/data/wwwroot/default/recommendsite.jar")
    sc.setCheckpointDir("/home/spark/checkpoint")
    val myRatings = loadRatings(personUrl)
    //样本数据目录
    val myRatingsRDD = sc.parallelize(myRatings, 1)
    val movielensHomeDir = HomeDirPath
    //装载电影目录对照表(电影ID->电影标题)

    val ModelPath = "file:////home/spark/ALS_Model" + UserId
    val bestModel = MatrixFactorizationModel.load(sc, ModelPath)

    //装载电影目录对照表(电影ID->电影标题)

    val movies = sc.textFile(movielensHomeDir + "/movies.dat").map {

      line =>

        val fields = line.split("::")

        // format: (movieId, movieName)

        (fields(0).toInt, fields(1))

    }.collect().toMap


    //推荐前十部最感兴趣的电影，注意要剔除用户已经评分的电影

    val myRatedMovieIds = myRatings.map(_.product).toSet

    val candidates = sc.parallelize(movies.keys.filter(!myRatedMovieIds.contains(_)).toSeq)

    val recommendations = bestModel

      .predict(candidates.map((0, _)))

      .collect

      .sortBy(-_.rating)

      .take(10)

    var i = 1

    println("Movies recommended for you:")

    //    recommendations.foreach { r =>
    //
    //      println("%2d".format(i) + ": " + movies(r.product))
    //
    //      i += 1
    //
    //    }
    var array: Array[Int] = recommendations.map(r => r.product)
    sc.stop()
    array
  }

  /** 校验集预测数据和实际数据之间的均方根误差 **/

  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], n: Long): Double = {


    val predictions: RDD[Rating] = model.predict((data.map(x => (x.user, x.product))))

    val predictionsAndRatings = predictions.map { x => ((x.user, x.product), x.rating) }

      .join(data.map(x => ((x.user, x.product), x.rating))).values

    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).reduce(_ + _) / n)

  }

  def loadRatings(path: String): Seq[Rating] = {

    println(s"path = ${path}")
    val lines = Source.fromFile(path).getLines()

    val ratings = lines.map {

      line =>

        val fields = line.split("::")

        Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble)

    }.filter(_.rating > 0.0)

    if (ratings.isEmpty) {

      sys.error("No ratings provided.")

    } else {

      ratings.toSeq

    }

  }
}
