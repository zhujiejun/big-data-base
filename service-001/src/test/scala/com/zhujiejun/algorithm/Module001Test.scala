package com.zhujiejun.algorithm

import com.google.common.base.Stopwatch
import com.zhujiejun.algorithm.Module001._
import com.zhujiejun.algorithm.entity.ListNode
import com.zhujiejun.comm.util.CommUtil._
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test

import java.util.concurrent.TimeUnit

@Test
class Module001Test {
    @Test
    //001.两数之和
    def test_twoSum001(): Unit = {
        val target = 12
        val nums = Array[Int](1, 3, 5, 7, 9)
        val ints = twoSum001(nums, target)
        println(ints.mkString("Array(", ", ", ")"))
    }

    @Test
    //002.两数相加
    def test_addTwoNumbers(): Unit = {
        //3-4-2
        val node1 = new ListNode(3)
        val node2 = new ListNode(4, node1)
        val node3 = new ListNode(2, node2)

        //4-6-5
        val node4 = new ListNode(4)
        val node5 = new ListNode(6, node4)
        val node6 = new ListNode(5, node5)

        var node = addTwoNumbers001(node3, node6)
        do {
            println(s"===========the value is ${node.value}==========")
        } while (node.hasNext())
    }

    @Test
    //003.无重复字符的最长子串
    def test_lengthOfLongestSubstring(): Unit = {
        //val s = "pwwkew"
        //val s = "abcabcbb"
        //val s = "FJryNEIRROBReKpFcrPYQnpOkMaecFWNsQoqcjhXdTvTLCPHXD"
        val watch: Stopwatch = Stopwatch.createStarted()
        val randomString = RandomStringUtils.randomAlphabetic(1 << 15)
        //println(randomString)
        println(s"the string and max substring length are [${randomString.length}], [${lengthOfLongestSubstring001(randomString)}].")
        println(s"the total time consumption is ${watch.elapsed(TimeUnit.MILLISECONDS)} ms.")
    }

    @Test
    //寻找两个有序数组的中位数
    def test_findMedianSortedArrays(): Unit = {
        //System.out.println(convert(generate(10)))
        //val nums1 = Array(1, 3, 5, 7, 9)
        //val nums2 = Array(0, 2, 4, 6, 8)

        val nums1 = generate(30)
        //println(s"the nums1 is ${convert(nums1)}")
        val nums2 = generate(35)
        //println(s"the nums2 is ${convert(nums2)}")
        val watch: Stopwatch = Stopwatch.createStarted()
        println(s"the median between nums1 and nums2 is [${findMedianSortedArrays002(nums1, nums2)}].")
        println(s"the total time consumption is ${watch.elapsed(TimeUnit.MILLISECONDS)} ms.")
    }
}
