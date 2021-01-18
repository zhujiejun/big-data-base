package com.zhujiejun.comm.algorithm.partion001

import com.google.common.base.Stopwatch
import com.zhujiejun.comm.algorithm.partion001.LeecodeTest._
import com.zhujiejun.util._
import org.apache.commons.lang3.RandomUtils
import org.junit.{After, Before, Test}

import java.util.concurrent.TimeUnit

object LeecodeTest {
    var target = 6
    var nums: Array[Int] = Array(1, 3, 4, 5, 7, 8, 9, 0)
}

class LeecodeTest {
    var watch: Stopwatch = _

    @Before
    def begin(): Unit = {
        watch = Stopwatch.createStarted()
        target = RandomUtils.nextInt(1, 500)
        nums = LeecodeUtil.geneIntArray(1, 123456, 12345)
    }

    @After
    def end(): Unit = {
        println(s"---------total time consumption is ${watch.elapsed(TimeUnit.MILLISECONDS)} ms.---------")
    }

    @Test
    def twoSum(): Unit = {
        //Leecode.twoSum01(nums = nums, target = target) foreach println
        Leecode.twoSum02(nums = nums, target = target) foreach println
    }
}

