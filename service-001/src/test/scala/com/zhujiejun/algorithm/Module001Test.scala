package com.zhujiejun.algorithm

import com.zhujiejun.algorithm.Module001._
import org.junit.Test

@Test
class Module001Test {
    @Test
    def test_twoSum001(): Unit = {
        val target = 12
        val nums = Array[Int](1, 3, 5, 7, 9)
        val ints = twoSum001(nums, target)
        println(ints.mkString("Array(", ", ", ")"))
    }
}
