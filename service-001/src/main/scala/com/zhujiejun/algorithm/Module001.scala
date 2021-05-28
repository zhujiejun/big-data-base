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

    def addTwoNumbers001(l1: Nothing, l2: Nothing): Nothing = {
        val dummyHead = new Nothing(0)
        var p = l1
        var q = l2
        var curr = dummyHead
        var carry = 0
        while ( {
            p != null || q != null
        }) {
            val x = if (p != null) p.`val`
            else 0
            val y = if (q != null) q.`val`
            else 0
            val sum = carry + x + y
            carry = sum / 10
            curr.next = new Nothing(sum % 10)
            curr = curr.next
            if (p != null) p = p.next
            if (q != null) q = q.next
        }
        if (carry > 0) curr.next = new Nothing(carry)
        dummyHead.next
    }
}

