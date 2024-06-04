package com.ksf.solve

import com.ksf.dataStructure.ListNode
import java.util.*

//2 两数相加
fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var l1 = l1
    var l2 = l2
    val dummy = ListNode(-1)
    var l = dummy
    var i = 0
    while (l1 != null || l2 != null || i != 0) {
        val val1 = l1?.`val` ?: 0
        val val2 = l2?.`val` ?: 0
        val result = val1 + val2 + i
        i = result / 10
        l.next = ListNode(if (result >= 10) result % 10 else result)
        l = l.next!!
        l1 = if (l1 == null) l1 else l1.next
        l2 = if (l2 == null) l2 else l2.next
    }
    return dummy.next
}

// 19 删除链表的倒数第N个节点
// 双指针，先让快指针走n步，然后快慢一起走，当快指针走到终点时，慢指针正好走了k-n步，也就是倒数第n个节点
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    val pre = ListNode(0).apply {
        this.next = head
    }
    var fastNode: ListNode? = pre
    var slowNode: ListNode? = pre
    for (i in 0..n) {
        fastNode = fastNode?.next
    }
    while (fastNode != null) {
        slowNode = slowNode?.next
        fastNode = fastNode.next
    }
    slowNode?.next = slowNode?.next?.next
    return pre.next
}

// 21 合并两个有序链表
fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    val dummy = ListNode(-1)
    var p = dummy
    var p2 = list2
    var p1 = list1

    while (p1 != null && p2 != null) {
        if (p1.`val` > p2.`val`) {
            p.next = p2
            p2 = p2.next
        } else {
            p.next = p1
            p1 = p1.next
        }
        p = p.next!!

    }

    if (p1 != null) {
        p.next = p1
    }
    if (p2 != null) {
        p.next = p2
    }
    return dummy.next
}

// 23 合并k个升序列表
// 借用优先级队列
fun mergeKLists(lists: Array<ListNode?>): ListNode? {

    if (lists.isEmpty()) return null

    val priorityQueue = PriorityQueue<ListNode>(lists.size, compareBy { it.`val` })
    lists.map { it?.let { priorityQueue.add(it) } }

    val dummy = ListNode(-1)
    var p = dummy

    while (!priorityQueue.isEmpty()) {
        val poll = priorityQueue.poll()
        p.next = poll
        if (poll.next != null) {
            priorityQueue.add(poll.next)
        }
        p = p.next!!
    }

    return dummy.next
}

// 24 两两 交换 链表中的节点
fun swapPairs(head: ListNode?): ListNode? {
    val dummy = ListNode(-1).apply { this.next = head }

    var temp = dummy
    while (temp.next?.next != null) {
        val first = temp.next
        val second = temp.next!!.next

        temp.next = second
        first!!.next = second!!.next
        second.next = first

        temp = temp.next!!.next!!
    }

    return dummy.next
}

// 24 递归
fun swapPairs1(head: ListNode?): ListNode? {
    if (head?.next == null) return head
    val temp = head.next!!
    head.next = swapPairs1(head.next!!.next)
    temp.next = head

    return temp
}


// 25 K个一组翻转链表
fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
    val dummy = ListNode(-1).apply { this.next = head }

    var pre: ListNode? = dummy
    var cur: ListNode? = dummy.next

    tag@ while (cur != null) {
        for (i in 0 until k - 1) {
            cur = cur?.next
            if (cur == null) break@tag
        }

        val suc = cur?.next
        cur?.next = null
        val afterRevCur = pre?.next

        val reverse = reverse(pre?.next)

        pre?.next = reverse
        afterRevCur?.next = suc

        pre = afterRevCur
        cur = suc
    }

    return dummy.next

}

fun reverse(head: ListNode?): ListNode? {
    var pre: ListNode? = null
    var cur: ListNode? = head
    while (cur != null) {
        val temp = cur.next
        cur.next = pre

        pre = cur
        cur = temp
    }

    return pre
}

// 61 旋转链表
// 先结环，然后从n-k处切开
fun rotateRight(head: ListNode?, k: Int): ListNode? {
    if (head?.next == null || k == 0) {
        return head
    }

    var tail = head
    var length = 1
    while (tail?.next != null) {
        tail = tail.next
        length++
    }

    val kk = k % length
    if (kk == 0) return head

    tail!!.next = head
    for (i in 1..length - kk) {
        tail = tail!!.next
    }

    val dummy = tail!!.next
    tail.next = null
    return dummy
}

// 83 删除排序列表中的重复元素
// 判断cur.next 是否等于.next.next
fun deleteDuplicates(head: ListNode?): ListNode? {

    val dummy = ListNode(-111).apply { this.next = head }
    var p = dummy

    while (p.next != null) {
        if (p.`val` == p.next!!.`val`) {
            p.next = p.next!!.next
        } else {
            p = p.next!!
        }
    }

    return dummy.next
}

// 82 删除排序列表中的重复元素2
// 把pre值记下来
fun deleteDuplicates2(head: ListNode?): ListNode? {
    val dummy = ListNode(-111).apply { this.next = head }
    var p = dummy

    var preVal = p.`val`
    while (p.next!=null){
        if(p.next!!.`val`==preVal){
            p.next=p.next!!.next
        }else{
            p = p.next!!
            preVal = p.`val`
        }
    }
    return dummy.next
}


// 86 分隔链表
// 双指针
fun partition(head: ListNode?, x: Int): ListNode? {
    val dummy1 = ListNode(-1)
    val dummy2 = ListNode(-1)
    var p1 = dummy1
    var p2 = dummy2
    var p = head

    while (p != null) {
        if (p.`val` >= x) {
            p2.next = p
            p2 = p2.next!!
        } else {
            p1.next = p
            p1 = p1.next!!
        }

        val temp = p.next
        p.next = null
        p = temp

    }

    p1.next = dummy2.next
    return dummy1.next
}

//92 反转链表2
fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
    if (head?.next == null) return head
    val dummy = ListNode(-1).apply { this.next = head }

    //遍历获得 left 和 right 节点
    var leftNode = dummy
    for (i in 0 until left - 1) {
        leftNode = leftNode.next!!
    }

    var rightNode = leftNode
    for (i in 0..right - left) {
        rightNode = rightNode.next!!
    }

    // left 和 right 节点后面就是 中间和右边部分
    val middleList = leftNode.next
    val rightList = rightNode.next

    //切断引用
    leftNode.next = null
    rightNode.next = null

    //反转中间
    val reverseList = reverseList(middleList)

    //拼回去
    leftNode.next = reverseList
    middleList!!.next = rightList

    return dummy.next
}

// 141 环形链表
// 双指针。一个走两步一个走一步， 有相交就是有环
fun hasCycle(head: ListNode?): Boolean {
    if (head?.next == null) {
        return false
    }

    var oneStep = head
    var twoStep = head

    while (twoStep?.next != null) {
        twoStep = twoStep.next?.next
        oneStep = oneStep!!.next

        if (twoStep == oneStep) {
            return true
        }
    }

    return false
}

//142 环形链表
// 双指针。一个走两步一个走一步， 有相交就是有环
// 找到环，再把慢指针设为head，再走一遍，交点就是环的起点
fun detectCycle(head: ListNode?): ListNode? {
    if (head?.next == null) {
        return null
    }

    var oneStep = head
    var twoStep = head

    while (twoStep?.next != null) {
        twoStep = twoStep.next?.next
        oneStep = oneStep!!.next

        if (oneStep == twoStep) {
            break
        }
    }

    if (twoStep?.next == null) {
        return null
    }

    oneStep = head
    while (oneStep != twoStep) {
        twoStep = twoStep!!.next
        oneStep = oneStep!!.next
    }

    return oneStep
}

// 143 重排链表
//先找到中间节点，然后把后半段反转
fun reorderList(head: ListNode?): ListNode? {
    val dummy: ListNode = ListNode(-1).apply { this.next = head }

    //快慢指针找中间节点
    var oneStep = dummy.next
    var twoStep = dummy.next?.next

    while (twoStep?.next != null) {
        oneStep = oneStep?.next
        twoStep = twoStep.next?.next
    }

    //反转后半段
    var rigth = reverse(oneStep?.next)
    oneStep?.next = null

    //拼接
    var left = dummy.next
    while (left != null || rigth !== null) {
        val next1 = left?.next
        val next2 = rigth?.next

        left?.next = rigth
        rigth?.next = next1

        left = next1
        rigth = next2
    }

    return dummy.next
}

//147 对链表进行插入排序
fun insertionSortList(head: ListNode?): ListNode? {
    if (head == null) return head

    val dummy = ListNode(-1).apply { this.next = head }
    var last = dummy.next

    while (last?.next != null) {
        if (last.next!!.`val` > last.`val`) {
            last = last.next
            continue
        }

        val cur = last.next
        var temp = dummy
        while (cur!!.`val` > temp.next!!.`val`) {
            temp = temp.next!!
        }

        last.next = last.next!!.next
        cur.next = temp.next
        temp.next = cur
    }

    return dummy.next
}

//148 排序列表  递归二分 然后拼接
fun sortList(head: ListNode?): ListNode? {

    return sortChildList(head)
}

fun sortChildList(head: ListNode?): ListNode? {
    if (head?.next == null) return head

    //找中间节点
    var oneStep = head
    var twoStep = head.next
    while (twoStep?.next != null) {
        oneStep = oneStep?.next
        twoStep = twoStep.next?.next
    }

    var left = head
    var right = oneStep?.next
    oneStep?.next = null

    // 递归二分
    left = sortChildList(left)
    right = sortChildList(right)

    //合并有序列表
    val dummy = ListNode(-1)
    var temp = dummy
    while (left != null && right != null) {
        if (left.`val` > right.`val`) {
            temp.next = right
            right = right.next
        } else {
            temp.next = left
            left = left.next
        }
        temp = temp.next!!
    }
    if (left != null) temp.next = left
    if (right != null) temp.next = right
    return dummy.next
}

// 160 相交链表
// 合起来遍历
fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {

    var one = headA
    var two = headB


    while (one != two) {
        one = if (one == null) headB else one.next
        two = if (two == null) headA else two.next
    }

    return one
}

// 反转链表
// 遍历 将后一个的next指向pre
fun reverseList(head: ListNode?): ListNode? {

    var pre: ListNode? = null
    var cur = head

    while (cur != null) {
        val temp = cur.next

        cur.next = pre
        pre = cur

        cur = temp
    }

    return pre
}

//203 移除链表元素
fun removeElements(head: ListNode?, `val`: Int): ListNode? {
    val dummy = ListNode(-1).apply { this.next = head }
    var p = dummy
    while (p.next != null) {
        if (p.next!!.`val` == `val`) {
            p.next = p.next!!.next
        } else {
            p = p.next!!
        }
    }
    return dummy.next
}

//234 回文链表
fun isPalindrome(head: ListNode?): Boolean {
    if (head?.next?.next == null) return false

    val dummy = ListNode(-1).apply { this.next = head }
    var temp = dummy.next
    var oneStep = dummy
    var twoStep = dummy
    //寻找中点
    while (twoStep.next?.next != null) {
        twoStep = twoStep.next!!.next!!
        oneStep = oneStep.next!!
    }
    if (twoStep.next != null) oneStep = oneStep.next!!

    //反转后面
    var reverse = reverse(oneStep.next)

    while (reverse != null) {
        if (reverse.`val` != temp?.`val`) {
            return false
        }
        reverse = reverse.next
        temp = temp.next
    }

    return true
}

// 328 奇偶链表
// 奇.next=偶.next  偶.next=奇.next
fun oddEvenList(head: ListNode?): ListNode? {
    if (head?.next?.next == null) return head

    var odd = head
    val evenTemp = head.next
    var even = evenTemp

    while (even?.next != null) {
        odd?.next = even.next
        odd = odd?.next

        even.next = odd?.next
        even = even.next
    }

    odd?.next = evenTemp

    return head
}


// 876 链表的中间节点
// 双指针，一个走一步一个走两步
fun middleNode(head: ListNode?): ListNode? {
    if (head?.next == null) {
        return head
    }

    var oneStep = head
    var twoStep = head

    while (twoStep?.next != null) {
        twoStep = twoStep.next?.next
        oneStep = oneStep!!.next
    }

    return oneStep
}