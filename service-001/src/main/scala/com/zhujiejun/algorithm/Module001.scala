package com.zhujiejun.algorithm

import scala.collection.mutable
import com.zhujiejun.algorithm.entity.ListNode

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

    def lengthOfLongestSubstring001(s: String): Int = {
        var ans = 0
        val n = s.length
        // 哈希集合，记录每个字符是否出现过
        val occ = new mutable.HashSet[Char]()
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        var rk = -1
        for (i <- 0 until n) {
            if (i != 0) { // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1))
            }
            while ( {
                rk + 1 < n && !occ.contains(s.charAt(rk + 1))
            }) { // 不断地移动右指针
                occ.add(s.charAt(rk + 1))
                rk += 1
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1)
        }
        ans
    }
}

