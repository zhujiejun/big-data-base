package com.zhujiejun.const.spark

import java.text.DecimalFormat

object Constant {
    var count = 1
    var gxyjr = "搞笑一家人"
    val dformat = new DecimalFormat("000")
    val savePath = "/opt/workspace/java/big-data-base/service-006/src/main/resources/zyw"
    val zywPath = "/opt/workspace/java/big-data-base/service-006/src/main/resources/zyw.txt"
    val SERVICE_006_NAME = "app006"

    val COMMON_PARAM = Map(
        //"spark.cores" -> "local[*]"
        "spark.cores" -> "spark://node101:7077"
    )

    val SPARK_PARAM: Array[(String, String)] = Map(
        "spark.driver.cores" -> "6",
        "spark.driver.memory" -> "512m",
        "spark.executor.cores" -> "6",
        "spark.executor.memory" -> "512m",
        //        "spark.kryoserializer.buffer.max" -> "256m",
        //        "spark.serializer" -> "org.apache.spark.serializer.KryoSerializer"
    ).toArray
}


