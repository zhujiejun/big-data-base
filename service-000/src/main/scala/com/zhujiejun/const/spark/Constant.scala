package com.zhujiejun.const.spark

object Constant {
    val SERVICE_006_NAME = "app006"

    val COMMON_PARAM = Map(
        //"spark.cores" -> "local[*]"
        "spark.cores" -> "spark://node101:7077"
    )

    val SPARK_PARAM: Array[(String, String)] = Map(
        "spark.driver.cores" -> "6",
        "spark.driver.memory" -> "512m",
        "spark.executor.cores" -> "6",
        "spark.executor.memory" -> "2g",
        "spark.kryoserializer.buffer.max" -> "256m",
        "spark.serializer" -> "org.apache.spark.serializer.KryoSerializer"
    ).toArray
}


