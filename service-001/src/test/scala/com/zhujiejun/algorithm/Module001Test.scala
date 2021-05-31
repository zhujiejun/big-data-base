package com.zhujiejun.algorithm

import com.zhujiejun.algorithm.Module001._
import com.zhujiejun.algorithm.entity.ListNode
import org.junit.Test

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
        //val s = "abcabcbb"
        //val s = "pwwkew"
        val s = "pppppp"
        val length = lengthOfLongestSubstring001(s)
        println(length)
    }
}
