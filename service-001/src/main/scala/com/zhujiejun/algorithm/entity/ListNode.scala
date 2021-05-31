package com.zhujiejun.algorithm.entity

//Definition for singly-linked list.
class ListNode() {
    var value = 0
    var next: ListNode = null

    def this(value: Int) = {
        this()
        this.value = value
    }

    def this(value: Int, next: ListNode) = {
        this()
        this.value = value
        this.next = next
    }

    def hasNext(): Boolean = {
        this.next != null
    }
}
