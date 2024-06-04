package com.ksf.solve

import com.ksf.dataStructure.ListNode
import java.util.*
import kotlin.math.max

//19
fun removeNthFromEnd1(head: ListNode?, n: Int): ListNode? {
    val dummy = ListNode(-1).apply { this.next = head }
    var fast = dummy
    var slow = dummy
    var n = n
    while (n > 0) {
        fast = fast.next!!
        n--
    }

    while (fast.next != null) {
        slow = slow.next!!
        fast = fast.next!!
    }

    slow.next = slow.next?.next

    return dummy.next
}


//21 合并两个有序列表
fun mergeTwoLists1(list1: ListNode?, list2: ListNode?): ListNode? {
    var dummy = ListNode(-1)
    var list1 = list1
    var list2 = list2
    var temp = dummy
    while (list1 != null && list2 != null) {
        if (list1.`val` < list2.`val`) {
            temp.next = list1
            list1 = list1.next
            temp = temp.next!!
        } else {
            temp.next = list2
            list2 = list2.next
            temp = temp.next!!
        }
    }

    if (list1 != null) temp.next = list1
    if (list2 != null) temp.next = list2

    return dummy.next
}

fun mergeKLists1(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    var dummy = ListNode(-1)
    var temp = dummy

    val priorityQueue = PriorityQueue<ListNode?>(Comparator.comparing { it.`val` })
    lists.forEach { it?.let { priorityQueue.add(it) } }

    while (priorityQueue.isNotEmpty()) {
        var poll = priorityQueue.poll()

        temp.next = poll
        temp = temp.next!!

        poll!!.next?.let { priorityQueue.add(it) }
    }

    return dummy.next
}


fun reverseKGroup1(head: ListNode?, k: Int): ListNode? {
    val dummy1 = ListNode(-1).apply { this.next = head }
    var pre = dummy1
    var cur = dummy1.next

    tag@ while (cur != null) {
        var head = cur
        for (i in 1 until k) {
            if (cur!!.next == null) break@tag
            cur = cur.next
        }

        var next = cur!!.next
        cur.next = null

        var reserve = reverse2(pre.next)

        pre.next = reserve
        head.next = next

        cur = next
        pre = head
    }

    return dummy1.next
}

private fun reverse2(head: ListNode?): ListNode? {
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


fun swapPairs11(head: ListNode?): ListNode? {
    if (head?.next == null) return head

    val temp = head.next
    head.next = swapPairs(head.next!!.next)
    temp!!.next = head

    return temp

}

fun rotateRight1(head: ListNode?, k: Int): ListNode? {
    var temp = head
    var fast = 1
    while (temp?.next != null) {
        temp = temp.next
        fast++
    }
    temp!!.next = head

    for (i in 0 until fast - k) {
        temp = temp!!.next
    }

    val result = temp!!.next
    temp.next = null

    return result
}

fun deleteDuplicates1(head: ListNode?): ListNode? {
    val dummy = ListNode(-111).apply { this.next = head }
    var p = dummy

    var preVal = p.`val`
    while (p.next != null) {
        if (p.next!!.`val` == preVal) {
            p.next = p.next!!.next
        } else {
            p = p.next!!
            preVal = p.`val`
        }
    }
    return dummy.next
}

fun partition1(head: ListNode?, x: Int): ListNode? {
    val dummy1 = ListNode(-1)
    var p1 = dummy1
    var dummy2 = ListNode(-2)
    var p2 = dummy2
    var p = head

    while (p != null) {
        if (p.`val` < x) {
            p1.next = p
            p1 = p1.next!!
        } else {
            p2.next = p
            p2 = p2.next!!
        }

        p = p.next
    }
    p2.next = null
    p1.next = dummy2.next
    return dummy1.next
}

fun reverseBetween1(head: ListNode?, left: Int, right: Int): ListNode? {
    val dummy1 = ListNode(-1).apply { this.next = head }
    var dummy = dummy1

    var i = 1
    while (i < left) {
        dummy = dummy.next!!
        i++
    }
    val left = dummy
    val mid = left.next!!

    while (i <= right) {
        dummy = dummy.next!!
        i++
    }
    val right = dummy.next
    dummy.next = null

    val reverse = reverse1(left.next!!)

    left.next = reverse
    mid.next = right

    return dummy1.next
}

fun reverse1(head: ListNode?): ListNode {

    var head = head
    var pre: ListNode? = null
    while (head != null) {
        val temp = head.next
        head.next = pre
        pre = head

        head = temp
    }

    return pre!!

}

fun hasCycle1(head: ListNode?): Boolean {
    if (head?.next == null) {
        return false
    }
    var fast = ListNode(-1).apply { this.next = head }
    var slow = ListNode(-1).apply { this.next = head }
    while (fast.next != null) {
        fast = fast.next?.next!!
        slow = slow.next!!

        if (fast == slow) {
            return true
        }
    }
    return false
}

fun detectCycle1(head: ListNode?): ListNode? {
    if (head?.next == null) return null

    var fast = head
    var slow = head

    while (fast?.next != null) {
        fast = fast.next?.next
        slow = slow?.next
        if (fast == slow) {
            break
        }
    }

    slow = head
    while (fast != slow) {
        fast = fast?.next
        slow = slow?.next
    }

    return fast
}

fun reorderList1(head: ListNode?): Unit {
    var fast = head
    var slow = head

    fast = fast?.next?.next
    while (fast?.next != null) {
        fast = fast.next!!.next
        slow = slow?.next
    }

    var reverse = reverse3(slow?.next)
    slow?.next = null

    var head = head
    while (head != null || reverse != null) {
        var temp = head?.next
        var rev = reverse!!.next

        head?.next = reverse
        reverse.next = temp

        reverse = rev
        head = temp
    }
}

fun reverse3(head: ListNode?): ListNode? {
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

fun sortList11(head: ListNode?): ListNode? {
    return null
}

fun getIntersectionNode1(headA: ListNode?, headB: ListNode?): ListNode? {
    headA?.next = headB
    headB?.next = headA

    var headA = headA
    var headB = headB
    while (headA != headB) {
        headA = if (headA == null) headB else headA.next
        headB = if (headB == null) headA else headB.next
    }

    return headA
}

fun removeElements1(head: ListNode?, `val`: Int): ListNode? {
    var dummy = ListNode(-1111).apply { this.next = head }
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

fun reverseList1(head: ListNode?): ListNode? {

    var cur = head
    var pre: ListNode? = null
    while (cur != null) {
        val next = cur.next
        cur.next = pre
        pre = cur
        cur = next
    }

    return pre

}

fun oddEvenList1(head: ListNode?): ListNode? {
    if (head?.next?.next == null) return head

    var odd = head
    var even1 = head.next
    var even = even1

    while (even?.next != null) {
        odd?.next = even.next
        odd = odd?.next

        even.next = odd?.next
        even = even.next
    }

    odd?.next = even1

    return head

}

fun middleNode1(head: ListNode?): ListNode? {
    var fast = head
    var slow = head
    while (fast?.next?.next != null) {
        fast = fast.next!!.next
        slow = slow!!.next
    }

    return slow
}

fun twoSum11(nums: IntArray, target: Int): IntArray {
    var map = mutableMapOf<Int, Int>()
    for (i in nums.indices) {
        var cur = nums[i]
        var left = target - cur
        if (map.containsKey(left)) {
            return intArrayOf(map[left]!!, i)
        } else {
            map[cur] = i
        }
    }
    return intArrayOf()
}

fun maxArea1(height: IntArray): Int {
    var left = 0
    var right = height.size - 1
    var res = 0
    while (left != right) {
        if (height[left] > height[right]) {
            right--

            res = max(res, height[right] * (right - left))
        } else {
            left++
            res = max(res, height[left] * (right - left))
        }

    }
    return res
}

fun removeDuplicates1(nums: IntArray): Int {
    var slow = 0
    var fast = 1
    while (fast < nums.size) {
        if (nums[slow] != nums[fast]) {
            slow++
            nums[slow] = nums[fast]
        }
        fast++
    }

    return slow + 1
}

fun removeElement1(nums: IntArray, `val`: Int): Int {
    var slow = 0
    var fast = 0
    while (fast < nums.size) {
        if (nums[fast] != `val`) {
            nums[slow] = nums[fast]
            slow++
        }
        fast++
    }

    return slow
}

fun searchRange1(nums: IntArray, target: Int): IntArray {
    if (nums.isEmpty()) return intArrayOf(-1, -1)

    return intArrayOf(findLeft1(nums, target), findRight1(nums, target))
}

fun findLeft1(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        val mid = (right + left) / 2
        if (nums[mid] == target) {
            right = mid - 1
        } else if (nums[mid] < target) {
            left = mid + 1
        } else {
            right = mid - 1
        }
    }

    if (left >= nums.size) return -1
    return if (target == nums[left]) left else -1
}

fun findRight1(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size
    while (left <= right) {
        val mid = (left + right) / 2
        if (nums[mid] == target) {
            left = mid + 1
        } else if (nums[mid] < target) {
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    if (left >= nums.size) return -1
    return if (target == nums[left]) left else -1
}

fun searchInsert1(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        var mid = (left + right) / 2
        if (target == nums[mid]) {
            return mid
        } else if (target < nums[mid]) {
            right = mid - 1
        } else {
            left = mid + 1
        }
    }

    return left
}

fun singleNumber1(nums: IntArray): Int {
    var temp = 0
    for (num in nums) {
        temp = temp xor num
    }
    return temp
}

fun twoSum1(numbers: IntArray, target: Int): IntArray {
    var left = 0;
    var right = numbers.size - 1
    while (left < right) {
        var result = numbers[left] + numbers[right]
        if (result == target) {
            return intArrayOf(left + 1, right + 1)
        } else if (result < target) {
            left++
        } else {
            right--
        }
    }
    return intArrayOf()
}

fun missingNumber1(nums: IntArray): Int {
    var sum = 0
    var idx = nums.size
    for (i in nums.indices) {
        sum += nums[i]
        idx += i
    }

    return idx - sum
}

fun moveZeroes1(nums: IntArray): Unit {
    var slow = 0
    var fast = 0
    while (fast < nums.size) {
        if (nums[fast] != 0) {
            nums[slow] = nums[fast]
            slow++
        }
        fast++
    }

    while (slow < nums.size) {
        nums[slow] = 0
        slow++
    }
}

fun findDuplicate1(nums: IntArray): Int {
    var fast = 0
    var slow = 0

    do {
        fast = nums[nums[fast]]
        slow = nums[slow]
    } while (fast != slow)

    slow = 0

    while (fast != slow) {
        fast = nums[fast]
        slow = nums[slow]
    }

    return slow
}

fun lengthOfLongestSubstring1(s: String): Int {
    var fast = 0
    var slow = 0
    val map = mutableMapOf<Char, Int>()
    var maxLenght = 0
    while (fast < s.length) {
        val cur = s[fast]
        map[cur] = map[cur]?.plus(1) ?: 1
        fast++

        while (slow < fast && map[cur]!! > 1) {
            val temp = s[slow]
            slow++
            map[temp] = map[temp]!! - 1
        }

        maxLenght = max(maxLenght, fast - slow)
    }

    return maxLenght
}


fun isValid(s: String): Boolean {
    val map = mapOf<String, String>("(" to ")", "[" to "]", "{" to "}")
    val stack = Stack<String>()
    for (char in s) {
        val charStr: String = char.toString()
        if (map.containsKey(charStr)) {
            stack.push(map[charStr])
        } else {
            if (stack.isEmpty()) return false

            val pop = stack.pop()
            if (charStr != pop) {
                return false
            }
        }
    }

    return stack.isEmpty()
}

fun minWindow1(s: String, t: String): String {
    var left = 0
    var right = 0
    var minStr = s
    val map = mutableMapOf<Char, Int>()
    val need = mutableMapOf<Char, Int>()
    var valid = 0
    for (char in t) {
        need[char] = need[char]?.plus(1) ?: 1
    }

    while (right < s.length) {
        val char = s[right]
        right++

        if (need.containsKey(char)) {
            map[char] = map[char]?.plus(1) ?: 1
            if (need[char] == map[char]) valid++
        }

        while (valid == need.size) {
            val substring = s.substring(left, right)
            minStr = if (minStr.length > substring.length) substring else minStr

            val cur = s[left]
            left++

            if (need.containsKey(cur)) {
                map[cur] = map[cur]!! - 1
                if (need[cur]!! > map[cur]!!) valid--
            }

        }


    }

    println(minStr)
    return minStr
}

fun reverseString1(s: CharArray): Unit {
    var left = 0
    var right = s.size - 1
    while (left < right) {
        var temp = s[left]
        s[left] = s[right]
        s[right] = temp
        left++
        right--
    }
}

fun findAnagrams1(s: String, p: String): List<Int> {
    val need = mutableMapOf<Char, Int>()
    p.forEach { need[it] = need[it]?.plus(1) ?: 1 }
    val result = mutableListOf<Int>()

    var left = 0
    var right = 0
    var pair: Pair<Int, Int>?
    do {
        pair = aaa(right, s, need, left, p)
        if (pair != null) {
            left = pair.second
            right = pair.second
            result.add(pair.first)
        }
    } while (pair != null)


    return result
}

private fun aaa(
    right: Int,
    s: String,
    need: MutableMap<Char, Int>,
    left: Int,
    p: String
): Pair<Int, Int>? {
    var right1 = right
    var left1 = left
    var valid = 0
    val window = mutableMapOf<Char, Int>()

    while (right1 < s.length) {
        val next = s[right1]
        right1++

        if (need.containsKey(next)) {
            window[next] = window[next]?.plus(1) ?: 1
            if (need[next] == window[next]) valid++
        }

        while (valid == need.size) {
            if ((right1 - left1) == p.length) {
                return Pair(left, right)
            }

            val pre = s[left1]
            left1++

            if (need.containsKey(pre)) {
                if (need[next] == window[next]) valid--
                window[next] = window[next]!! - 1
            }
        }
    }
    return null
}

fun checkInclusion1(s1: String, s2: String): Boolean {
    var need = mutableMapOf<Char, Int>()
    s1.forEach { need[it] = need[it]?.plus(1) ?: 1 }

    var left = 0
    var right = 0
    var vaild = 0
    val windows = mutableMapOf<Char, Int>()
    while (right < s2.length) {
        val next = s2[right]
        right++

        if (need.containsKey(next)) {
            windows[next] = windows[next]?.plus(1) ?: 1
            if (windows[next] == need[next]) vaild++
        }

        while (vaild == need.size) {
            if ((right - left) == s1.length) {
                return true
            }

            val pre = s2[left]
            left++
            if (need.containsKey(pre)) {
                if (need[pre] == windows[pre]) vaild--
                windows[pre] = windows[pre]!! - 1
            }
        }
    }
    return false
}


fun main() {
}