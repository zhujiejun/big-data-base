package com.zhujiejun.comm.algorithm.partion001

import com.google.common.base.Stopwatch
import com.zhujiejun.comm.algorithm.partion001.LeecodeTest._
import org.junit.{After, Before, Test}

import java.util.concurrent.TimeUnit

object LeecodeTest {
    val target = 6
    val nums: Array[Int] = Array(1, 3, 4, 5, 7, 8, 9, 0)
}

class LeecodeTest {
    var watch: Stopwatch = _

    @Before
    def begin(): Unit = {
        watch = Stopwatch.createStarted()

    }

    @After
    def end(): Unit = {
        println(s"---------total time consumption is ${watch.elapsed(TimeUnit.MILLISECONDS)} ms.---------")
    }

    @Test
    def twoSum01(): Unit = {

        val result = Leecode.twoSum01(nums = nums, target = target)
        result foreach println
    }

    @Test
    def twoSum02(): Unit = {
        val result = Leecode.twoSum02(nums = nums, target = target)
        result foreach println
    }
}

