package com.zhujiejun.util

import org.apache.commons.lang3.RandomUtils

import scala.collection.mutable

object LeecodeUtil {
    def geneIntArray(lower: Int, supper: Int, amount: Int): Array[Int] = {

        if (amount < (supper - lower)) {
            var set: mutable.Set[Int] = mutable.Set()
            for (_ <- 0 to amount) {
                val num = RandomUtils.nextInt(lower, supper)
                set += num
            }
            if (set.size < amount) {
                geneIntArray(lower, supper, amount - set.size)
            }
            set.toArray
        } else {
            Array()
        }


    }

    def main(args: Array[String]): Unit = {
        geneIntArray(1, 150, 75) foreach println
    }
}
