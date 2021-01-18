package com.zhujiejun.comm.algorithm.partion001

import com.google.common.base.Stopwatch
import com.zhujiejun.comm.algorithm.partion001.LeecodeTest._
import com.zhujiejun.util._
import org.apache.commons.lang3.RandomUtils
import org.junit.{After, Before, Test}

import java.util.concurrent.TimeUnit

object LeecodeTest {
    var target = 6
    var str: String = ""
    var watch: Stopwatch = _
    var nums: Array[Int] = Array(1, 3, 4, 5, 7, 8, 9, 0)
    var num1: Array[Int] = Array(1, 3, 4, 5, 7, 8, 9, 0)
    var num2: Array[Int] = Array(1, 3, 4, 5, 7, 8, 9, 0)
}

class LeecodeTest {
    @Before
    def begin(): Unit = {
        watch = Stopwatch.createStarted()
        target = RandomUtils.nextInt(1, 500)
        nums = LeecodeUtil.geneIntArray(1, 123456, 12345)
        num1 = LeecodeUtil.geneIntArray(1, 123456789, 12345678)
        num2 = LeecodeUtil.geneIntArray(1, 123456789, 12345678)
        str = "consumdjshuahdirqqtryrqtysfzafhjgjhsgxgdwhahzteerxwoqhdgxgzfTption"
    }

    @After
    def end(): Unit = {
        println(s"""total time consumption is ${watch.elapsed(TimeUnit.MILLISECONDS)} ms, about ${watch.elapsed(TimeUnit.SECONDS)} second!""")
    }

    //001.两数之和
    @Test
    def twoSum(): Unit = {
        Leecode.twoSum01(nums = nums, target = target) foreach println //force
        //Leecode.twoSum02(nums = nums, target = target) foreach println //map
    }

    //002.两数相加
    def addTwoNumbers(): Unit = {}

    //003.无重复字符的最长子串
    @Test
    def lengthOfLongestSubstring(): Unit = {
        println(Leecode.lengthOfLongestSubstring(str))
    }

    //004.寻找两个有序数组的中位数
    @Test
    def findMedianSortedArrays(): Unit = {
        //val median = Leecode.findMedianSortedArrays01(num1 = num1, num2 = num2) //二分查找
        val median = Leecode.findMedianSortedArrays02(num1 = num1, num2 = num2) //划分数组
        println(s"---------the median is $median----------")
    }

    //005.最长回文子串
    @Test
    def longestPalindrome(): Unit = {
        //val str1 = Leecode.longestPalindrome01(str) //动态规划
        //val str1 = Leecode.longestPalindrome02(str) //中心扩展
        val str1 = Leecode.longestPalindrome03(str) //Manacher
        println(s"---------the str is $str1---------")
    }

}

