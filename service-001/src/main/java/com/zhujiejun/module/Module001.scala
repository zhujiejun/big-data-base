package com.zhujiejun.module

import scala.collection.mutable

object Module001 {
    def twoSum001(nums: Array[Int], target: Int): Array[Int] = {
        val map = mutable.Map[Int, Int]()
        for (i <- nums.indices) {
            val complement = target - nums(i)
            if (map.contains(complement))
                return Array[Int](map.get(complement), i)
            map.+=(nums(i) -> i)
        }
        throw new IllegalArgumentException("No two sum solution")
    }

    def main(args: Array[String]): Unit = {
        val target = 12
        val nums = Array[Int](1, 3, 5, 7, 9)
        val ints = twoSum001(nums, target)
        println(ints.mkString("Array(", ", ", ")"))
    }
}