package com.zhujiejun.spark.text

import com.google.common.io.MoreFiles
import com.zhujiejun.const.spark.Constant._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.nio.file.{Files, Paths}

object ZYW {
    def main(args: Array[String]): Unit = {
        if (Files.isDirectory(Paths.get(savePath))) MoreFiles.deleteRecursively(Paths.get(savePath))
        val sparkConfig: SparkConf = new SparkConf().setAppName("ZYW").setMaster("local[*]")
        val spark = SparkSession.builder().config(sparkConfig).getOrCreate()
        val file = spark.sparkContext.textFile(zywPath).repartition(1)

        file.map { line =>
            val newline = line.replaceFirst(gxyjr, s"$gxyjr${dformat.format(count)}")
            count = count + 1
            newline
        }.sortBy(f = { line =>
            line.split(" ")(3)
        }, ascending = true).saveAsTextFile(savePath)

        spark.close()
    }
}
