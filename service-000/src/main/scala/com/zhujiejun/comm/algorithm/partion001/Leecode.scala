package com.zhujiejun.comm.algorithm.partion001

import scala.collection.mutable

object Leecode {
    def twoSum01(nums: Array[Int], target: Int): Array[Int] = {
        val length = nums.length
        for (i <- 0 until length) {
            for (j <- i + 1 until length) {
                if (nums(i) + nums(j) == target) {
                    return Array(i, j)
                }
            }
        }
        Array(0)
    }

    def twoSum02(nums: Array[Int], target: Int): Array[Int] = {
        val length = nums.length
        var saved: mutable.Map[Int, Int] = mutable.Map()
        for (i <- 0 until length) {
            val remains = target - nums(i)
            if (saved.contains(remains)) {
                return Array(saved.getOrElse(remains, 0), i)
            }
            saved += (nums(i) -> i)
        }
        Array(0)
    }
}
