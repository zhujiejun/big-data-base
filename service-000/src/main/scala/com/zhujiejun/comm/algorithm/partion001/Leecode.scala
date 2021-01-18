package com.zhujiejun.comm.algorithm.partion001

import org.apache.commons.lang3.StringUtils

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Leecode {
    //001.两数之和-force
    def twoSum01(nums: Array[Int], target: Int): Array[Int] = {
        val length = nums.length
        for (i <- 0 until length) {
            for (j <- i + 1 until length) {
                if (nums(i) + nums(j) == target) {
                    return Array(i, j)
                }
            }
        }
        Array(0)
    }

    //001.两数之和-map
    def twoSum02(nums: Array[Int], target: Int): Array[Int] = {
        val length = nums.length
        var saved: mutable.Map[Int, Int] = mutable.Map()
        for (i <- 0 until length) {
            val remains = target - nums(i)
            if (saved.contains(remains)) {
                return Array(saved.getOrElse(remains, 0), i)
            }
            saved += (nums(i) -> i)
        }
        Array(0)
    }

    //002.两数相加
    def addTwoNumbers(l1: List[Int], l2: List[Int]): List[Int] = {
        null
    }

    //003.无重复字符的最长子串
    def lengthOfLongestSubstring(s: String): Int = {
        val n = s.length
        //哈希集合，记录每个字符是否出现过
        var occ: mutable.Set[Character] = mutable.Set()
        //右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        var (rk, answer) = (-1, 0)
        for (i <- 0 until n) {
            if (i != 0) {
                //左指针向右移动一格，移除一个字符
                occ -= s.charAt(i - 1)
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                //不断地移动右指针
                occ += s.charAt(rk + 1)
                rk = rk + 1
            }
            //第 i 到 rk 个字符是一个极长的无重复字符子串
            answer = Math.max(answer, rk - i + 1)
        }
        answer
    }

    private def getKthElement(num1: Array[Int], num2: Array[Int], k: Int): Int = {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
         * 这里的 "/" 表示整除
         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
         * 这样 pivot 本身最大也只能是第 k-1 小的元素
         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
         */
        var k0 = k
        val kthElement = 0
        var (index1, index2) = (0, 0)
        val (length1, length2) = (num1.length, num1.length)
        while (true) {
            //边界情况
            if (index1 == length1) {
                return num2(index2 + k0 - 1)
            }
            if (index2 == length2) {
                return num2(index1 + k0 - 1)
            }
            if (k0 == 1) {
                return Math.min(num1(index1), num2(index2))
            }
            //正常情况
            val half = k0 / 2
            val newIndex1 = Math.min(index1 + half, length1) - 1
            val newIndex2 = Math.min(index2 + half, length2) - 1
            val pivot1 = num1(newIndex1)
            val pivot2 = num2(newIndex2)
            if (pivot1 <= pivot2) {
                k0 = k0 - (newIndex1 - index1 + 1)
                index1 = newIndex1 + 1
            } else {
                k0 = k0 - (newIndex2 - index2 + 1)
                index2 = newIndex2 + 1
            }
        }
        kthElement
    }

    //004.寻找两个有序数组的中位数-二分查找
    def findMedianSortedArrays01(num1: Array[Int], num2: Array[Int]): Double = {
        val (length1, length2) = (num1.length, num1.length)
        val totalLength = length1 + length2
        if (totalLength % 2 == 1) {
            val midIndex = totalLength / 2
            val median = getKthElement(num1, num2, midIndex + 1)
            median
        } else {
            val (midIndex1, midIndex2) = (totalLength / 2 - 1, totalLength / 2)
            val median = (getKthElement(num1, num2, midIndex1 + 1) + getKthElement(num1, num2, midIndex2 + 1)) / 2.0
            median
        }
    }

    //004.寻找两个有序数组的中位数-划分数组
    def findMedianSortedArrays02(num1: Array[Int], num2: Array[Int]): Double = {
        val (length1, length2) = (num1.length, num1.length)
        if (length1 > length2) {
            return findMedianSortedArrays02(num2, num1)
        }
        //median1：前一部分的最大值
        //median2：后一部分的最小值
        var (left, right) = (0, length1)
        var (median1, median2) = (0, 0)
        while (left < right) {
            //前一部分包含 num1[0 .. i-1] 和 nums2[0 .. j-1]
            //后一部分包含 num1[i .. m-1] 和 nums2[j .. n-1]
            val i: Int = (left + right) / 2
            val j: Int = (length1 + length2 + 1) / 2 - i

            //num_im1, num_i, num_jm1, num_j 分别表示
            //num1[i-1], num1[i], num2[j-1], num2[j]
            val num_im1 = if (i == 0) Integer.MIN_VALUE else num1(i - 1)
            val num_i = if (i == length1) Integer.MAX_VALUE else num1(i)
            val num_jm1 = if (j == 0) Integer.MIN_VALUE else num2(j - 1)
            val num_j = if (j == length2) Integer.MIN_VALUE else num2(j)

            if (num_im1 <= num_j) {
                median1 = Math.max(num_im1, num_jm1)
                median2 = Math.min(num_i, num_j)
                left = i + 1
            } else {
                right = i - 1
            }
        }
        if ((length1 + length2) % 2 == 0) (median1 + median2) / 2.0 else median1
    }

    //005.最长回文子串-动态规划
    def longestPalindrome01(s: String): String = {
        val n = s.length()
        val dp = Array.ofDim[Boolean](n, n)
        var answer: String = StringUtils.EMPTY
        for (l <- 0 until n) {
            for (i <- 0 until n if i + l < n) {
                val j = i + l
                if (l == 0) {
                    dp(i)(j) = true
                } else if (l == 1) {
                    dp(i)(j) = s.charAt(i) == s.charAt(j)
                } else {
                    dp(i)(j) = (s.charAt(i) == s.charAt(j)) && dp(i + 1)(j - 1)
                }
                if (dp(i)(j) && l + 1 > answer.length()) {
                    answer = s.substring(i, i + l + 1)
                }
            }
        }
        answer
    }

    private def expandAroundCenter(s: String, left: Int, right: Int): Int = {
        var (left0: Int, right0: Int) = (left, right)
        while (left0 >= 0 && right0 < s.length() && s.charAt(left0) == s.charAt(right0)) {
            left0 = left0 - 1
            right0 = right0 + 1
        }
        right0 - left0 - 1
    }

    //005.最长回文子串-中心扩展算法
    def longestPalindrome02(s: String): String = {
        if (StringUtils.isBlank(s)) {
            return StringUtils.EMPTY
        }
        var (start, end) = (0, 0)
        for (i <- 0 until s.length) {
            val len1 = expandAroundCenter(s, i, i)
            val len2 = expandAroundCenter(s, i, i + 1)
            val len = math.max(len1, len2)
            if (len > end - start) {
                start = i - (len - 1) / 2
                end = i + len / 2
            }
        }
        s.substring(start, end + 1)
    }

    private def expandManacher(s: String, left: Int, right: Int): Int = {
        var (left0: Int, right0: Int) = (left, right)
        while (left0 >= 0 && right0 < s.length() && s.charAt(left0) == s.charAt(right0)) {
            left0 = left0 - 1
            right0 = right0 + 1
        }
        (right0 - left0 - 2) / 2;
    }

    //005.最长回文子串-Manacher
    def longestPalindrome03(s: String): String = {
        var (start, end) = (0, -1)
        val t = new StringBuffer("#")
        for (i <- 0 until s.length) {
            t.append(s.charAt(i))
            t.append('#')
        }
        t.append('#')
        val s0 = t.toString
        val arm_len: ListBuffer[Int] = ListBuffer[Int]()
        var (right, j) = (-1, -1)
        for (i <- 0 until s0.length) {
            var cur_arm_len: Int = 0
            if (right >= i) {
                val i_sym: Int = j * 2 - i
                val min_arm_len: Int = Math.min(arm_len(i_sym), right - i)
                cur_arm_len = expandManacher(s0, i - min_arm_len, i + min_arm_len)
            } else {
                cur_arm_len = expandManacher(s0, i, i)
            }
            arm_len += cur_arm_len
            if (i + cur_arm_len > right) {
                j = i
                right = i + cur_arm_len
            }
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len
                end = i + cur_arm_len
            }
        }
        val answer: StringBuffer = new StringBuffer()
        for (i <- start to end) {
            if (s0.charAt(i) != '#') {
                answer.append(s0.charAt(i))
            }
        }
        answer.toString
    }
}
