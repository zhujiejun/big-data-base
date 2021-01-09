package com.zhujiejun.spark

import com.zhujiejun.const.spark.Constant._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId}

object App006 {
    def main(args: Array[String]): Unit = {
        val sparkConfig: SparkConf = new SparkConf()
            .setMaster(COMMON_PARAM("spark.cores"))
            .setAppName(SERVICE_006_NAME)
            .setAll(SPARK_PARAM)
        val spark = SparkSession.builder().config(sparkConfig).getOrCreate()

        import spark.implicits._
        val file = spark.sparkContext.textFile("file:///home/cat/Downloads/common/log4j.properties")
        val fileNew = file.map { line =>
            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss.SSS")).concat(line)
        }
        fileNew.toDF().show()

        spark.close()
    }
}
