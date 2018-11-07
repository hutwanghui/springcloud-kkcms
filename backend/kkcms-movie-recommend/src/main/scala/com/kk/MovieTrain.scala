package com.kk

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by hutwanghui on 2018/7/14 13:40.
  * email:zjjhwanhui@163.com
  * qq:472860892
  * desc:
  */

class MovieTrain {

}

object MovieTrain {
  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上

    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)


    //设置运行环境args(2)
    //设置运行环境
    val sparkConf = new SparkConf().setAppName("com.kk.MovieTrain" + args(2))
      .setMaster("local[*]")
      .set("spark.executor.memory", "1g")
      .set("spark.testing.memory", "2147480000")
      .set("spark.driver.allowMultipleContexts", "true")
    val sc = new SparkContext(sparkConf)
    sc.addJar("/data/wwwroot/default/recommendsite.jar")
    sc.setCheckpointDir("/home/spark/checkpoint" + args(2))
    //装载用户评分，该评分由评分器生成(即生成文件personalRatings.txt)

    val myRatings = loadRatings(args(1))

    val myRatingsRDD = sc.parallelize(myRatings, 1)


    //样本数据目录

    val movielensHomeDir = args(0)


    //装载样本评分数据，其中最后一列Timestamp取除10的余数作为key，Rating为值，即(Int，Rating)

    val ratings = sc.textFile(movielensHomeDir + "/ratings.dat").map {

      line =>

        val fields = line.split("::")

        // format: (timestamp % 10, Rating(userId, movieId, rating))

        (fields(3).toLong % 10, Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble))

    }


    //装载电影目录对照表(电影ID->电影标题)

    val movies = sc.textFile(movielensHomeDir + "/movies.dat").map {

      line =>

        val fields = line.split("::")

        // format: (movieId, movieName)

        (fields(0).toInt, fields(1))

    }.collect().toMap


    //统计有用户数量和电影数量以及用户对电影的评分数目

    val numRatings = ratings.count()

    val numUsers = ratings.map(_._2.user).distinct().count()

    val numMovies = ratings.map(_._2.product).distinct().count()

    println("Got " + numRatings + " ratings from " + numUsers + " users " + numMovies + " movies")


    //将样本评分表以key值切分成3个部分，分别用于训练 (60%，并加入用户评分), 校验 (20%), and 测试 (20%)

    //该数据在计算过程中要多次应用到，所以cache到内存

    val numPartitions = 4

    val training = ratings.filter(x => x._1 < 6).values.union(myRatingsRDD).repartition(numPartitions).persist()

    val validation = ratings.filter(x => x._1 >= 6 && x._1 < 8).values.repartition(numPartitions).persist()

    val test = ratings.filter(x => x._1 >= 8).values.persist()


    val numTraining = training.count()

    val numValidation = validation.count()

    val numTest = test.count()

    println("Training: " + numTraining + " validation: " + numValidation + " com.kk.test: " + numTest)


    //训练不同参数下的模型，并在校验集中验证，获取最佳参数下的模型
    //numBlocks 是用于并行化计算的分块个数 (设置为-1为自动配置)。
    //rank 是模型中隐语义因子的个数。即特征数量
    //iterations 是迭代的次数。
    //lambda 是ALS的正则因子（spark的推荐值是0.01）
    //implicitPrefs 决定了是用显性反馈ALS的版本还是用适用隐性反馈数据集的版本。
    //alpha 是一个针对于隐性反馈 ALS 版本的参数，这个参数决定了偏好行为强度的基准。

    val ranks = List(8, 12)

    val lambdas = List(0.1, 10.0)

    val numIters = List(10, 20)

    var bestModel: Option[MatrixFactorizationModel] = None

    var bestValidationRmse = Double.MaxValue

    var bestRank = 0

    var bestLambda = -1.0

    var bestNumIter = -1


    for (rank <- ranks; lambda <- lambdas; numIter <- numIters) {

      val model = ALS.train(training, rank, numIter, lambda)

      val validationRmse = computeRmse(model, validation, numValidation)

      println("RMSE(validation) = " + validationRmse + " for the model trained with rank = "

        + rank + ",lambda = " + lambda + ",and numIter = " + numIter + ".")


      if (validationRmse < bestValidationRmse) {

        bestModel = Some(model)

        bestValidationRmse = validationRmse

        bestRank = rank

        bestLambda = lambda

        bestNumIter = numIter
      }

    }
    val ModelPath = "file:////home/spark/ALS_Model" + args(2)


    //用最佳模型预测测试集的评分，并计算和实际评分之间的均方根误差（RMSE）

    val testRmse = computeRmse(bestModel.get, test, numTest)

    println("The best model was trained with rank = " + bestRank + " and lambda = " + bestLambda

      + ", and numIter = " + bestNumIter + ", and its RMSE on the com.kk.test set is " + testRmse + ".")


    //create a naive baseline and compare it with the best model

    val meanRating = training.union(validation).map(_.rating).mean

    val baselineRmse = math.sqrt(test.map(x => (meanRating - x.rating) * (meanRating - x.rating)).reduce(_ + _) / numTest)

    val improvement = (baselineRmse - testRmse) / baselineRmse * 100

    println("The best model improves the baseline by " + "%1.2f".format(improvement) + "%.")

    bestModel.get.save(sc, ModelPath)

    println(s"ModelPath 矩阵保存= ${ModelPath}")

    sc.stop()

  }

  /** 装载用户评分文件 personalRatings.txt **/

  def loadRatings(path: String): Seq[Rating] = {

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

  /** 校验集预测数据和实际数据之间的均方根误差 **/

  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], n: Long): Double = {


    val predictions: RDD[Rating] = model.predict((data.map(x => (x.user, x.product))))

    val predictionsAndRatings = predictions.map { x => ((x.user, x.product), x.rating) }

      .join(data.map(x => ((x.user, x.product), x.rating))).values

    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).reduce(_ + _) / n)

  }

}
