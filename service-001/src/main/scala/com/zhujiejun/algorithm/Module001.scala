package com.zhujiejun.algorithm

import scala.collection.mutable

object Module001 {
    def twoSum001(nums: Array[Int], target: Int): Array[Int] = {
        val map = mutable.Map[Int, Int]()
        for (i <- nums.indices) {
            val complement = target - nums(i)
            if (map.contains(complement))
                return Array[Int](map(complement), i)
            map.+=(nums(i) -> i)
        }
        throw new IllegalArgumentException("No two sum solution")
    }
}

