package com.zhujiejun.algorithm

import com.zhujiejun.algorithm.entity.ListNode

import scala.collection.mutable

object Module001 {
    //两数之和
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

    //两数相加
    def addTwoNumbers001(l1: ListNode, l2: ListNode): ListNode = {
        var list1 = l1
        var list2 = l2
        var carry: Int = 0
        var head: ListNode = null
        var tail: ListNode = null
        while (list1 != null || list2 != null) {
            val n1: Int = if (list1 != null) list1.value else 0
            val n2: Int = if (list2 != null) list2.value else 0
            val sum: Int = n1 + n2 + carry
            if (head == null) {
                head = new ListNode(sum % 10)
                tail = new ListNode(sum % 10)
            } else {
                tail.next = new ListNode(sum % 10)
                tail = tail.next
            }
            carry = sum / 10
            if (list1 != null) list1 = list1.next
            if (list2 != null) list2 = list2.next
        }
        if (carry > 0) tail.next = new ListNode(carry)
        head
    }

    //无重复字符的最长子串
    def lengthOfLongestSubstring001(s: String): Int = {
        var ans = 0
        val n = s.length
        // 哈希集合，记录每个字符是否出现过
        val occ = new mutable.HashSet[Char]
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        var rp = -1
        for (lp <- 0 until n) {
            if (lp != 0) { // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(lp - 1))
            }
            while ( {
                rp + 1 < n && !occ.contains(s.charAt(rp + 1))
            }) { // 不断地移动右指针
                occ.add(s.charAt(rp + 1))
                rp += 1
            }
            // 第 lp 到 rp 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rp - lp + 1)
        }
        ans
    }
}

