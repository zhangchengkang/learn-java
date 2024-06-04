package com.ksf.solve

import com.ksf.dataStructure.ListNode
import com.ksf.dataStructure.TreeNode
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


//3 无重复字符串的最长子串
fun lengthOfLongestSubstring(s: String): Int {
    val map = mutableMapOf<Char, Int>()
    var left = 0
    var right = 0
    var max = 0

    while (right < s.length) {
        val rightChar = s[right]
        right++
        map[rightChar] = (map[rightChar] ?: 0) + 1

        while (left < right && map[rightChar]!! > 1) {
            val leftChar = s[left]
            left++
            map[leftChar] = map[leftChar]!! - 1
        }

        max = max(max, right - left)
    }

    return max
}

//7 正数反转
fun reverseNum(x: Int): Int {
    var res = 0
    var x = x
    while (x != 0) {

        if (res < Int.MIN_VALUE / 10 || res > Int.MAX_VALUE / 10) return 0

        res = res * 10 + x % 10
        x /= 10
    }

    return res
}


//14 最长公共前缀
fun longestCommonPrefix(strs: Array<String>): String {
    var preFix = strs[0]

    for (i in 1 until strs.size) {
        preFix = findCommonPrefix(preFix, strs[i])
        if (preFix.isEmpty()) return ""
    }

    return preFix
}

fun findCommonPrefix(preFix: String, str: String): String {
    var index = 0
    val size = min(preFix.length, str.length)
    while (index < size && preFix[index] == str[index]) {
        index++
    }

    return preFix.substring(0, index)
}

//15 三数之和
fun threeSum(nums: IntArray): List<List<Int>> {
    val result = mutableListOf<MutableList<Int>>()
    if (nums.size < 3) return result

    nums.sort()

    for (i in nums.indices) {
        if (nums[i] > 0) return result

        //去重
        if (i > 0 && nums[i] == nums[i - 1]) continue

        var left = i + 1
        var right = nums.size - 1

        while (left < right) {
            val target = nums[left] + nums[i] + nums[right]
            if (target == 0) {
                result.add(mutableListOf(nums[left], nums[i], nums[right]))

                //去重
                while (left < right && nums[left] == nums[left + 1]) left++
                while (left < right && nums[right] == nums[right - 1]) right--

                left++
                right--
            } else if (target > 0) {
                right--
            } else {
                left++
            }
        }
    }

    return result
}


//17 电话号码的字母匹配
var letterCombinationRes = mutableListOf<String>()
var letterCombinationMap = mutableMapOf<Char, List<String>>()
fun letterCombinations(digits: String): List<String> {
    if (digits.isEmpty()) return letterCombinationRes
    letterCombinationMap['2'] = listOf("a", "b", "c")
    letterCombinationMap['3'] = listOf("d", "e", "f")
    letterCombinationMap['4'] = listOf("g", "h", "i")
    letterCombinationMap['5'] = listOf("j", "k", "l")
    letterCombinationMap['6'] = listOf("m", "n", "o")
    letterCombinationMap['7'] = listOf("p", "q", "r", "s")
    letterCombinationMap['8'] = listOf("t", "u", "v")
    letterCombinationMap['9'] = listOf("w", "x", "y", "z")
    letterCombinations(digits, "", 0)
    return letterCombinationRes
}

fun letterCombinations(digits: String, target: String, start: Int) {
    if (digits.length == start) {
        letterCombinationRes.add(target)
        return
    }

    var target = target

    val strings = letterCombinationMap[digits[start]]!!
    for (j in strings.indices) {
        val char: String = strings[j]
        target += char
        letterCombinations(digits, target, start + 1)
        target = target.removeSuffix(char)
    }
}

//25 K个一组反转链表
fun reverseKGroup2(head: ListNode?, k: Int): ListNode? {
    var dummy = ListNode(-1).apply { this.next = head }
    var pre = dummy
    var cur = dummy.next

    tag@ while (cur != null) {
        for (i in 1 until k) {
            cur = cur!!.next
            if (cur == null) break@tag
        }

        val nextSegmentFirst = cur!!.next
        cur.next = null

        val curSegmentLast = pre.next

        reverse5(pre.next!!)

        pre.next = cur
        curSegmentLast!!.next = nextSegmentFirst

        pre = curSegmentLast
        cur = nextSegmentFirst

    }

    return dummy.next
}

fun reverse5(head: ListNode): ListNode {
    var pre: ListNode? = null
    var cur: ListNode? = head

    while (cur != null) {
        val next = cur.next

        cur.next = pre
        pre = cur
        cur = next
    }

    return pre!!
}

//31 下一个排列
fun nextPermutation(nums: IntArray): Unit {
    //倒序是最大的，从后往前找非倒序的第一个
    var flag = -1
    for (i in nums.size - 1 downTo 1) {
        if (nums[i] > nums[i - 1]) {
            flag = i - 1
            break
        }
    }
    if (flag == -1) {
        nums.sort()
        return
    }


    //交换flag和后半段仅比flag大的
    var flag1 = -1
    var tt = Int.MAX_VALUE
    for (i in flag + 1 until nums.size) {
        if (nums[i] > nums[flag] && tt > nums[i]) {
            tt = nums[i]
            flag1 = i
        }
    }

    val temp = nums[flag]
    nums[flag] = nums[flag1]
    nums[flag1] = temp

    //排序后半段，让后半段最小
    nums.sort(flag + 1, nums.size)
}

//33 搜索旋转数组
fun search(nums: IntArray, target: Int): Int {
    if (nums.isEmpty()) return -1
    if (nums.size == 1) return if (nums[0] == target) 0 else -1

    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        val mid = (left + right) / 2
        if (nums[mid] == target) {
            return mid
        }

        //判断mid以左是不是有序的
        if (nums[0] <= nums[mid]) {
            if (nums[0] <= target && target < nums[mid]) {
                right = mid - 1
            } else {
                left = mid + 1
            }
        } else {
            if (nums[mid] < target && target <= nums[nums.size - 1]) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
    }

    return -1
}

//49 字母异位词分组
fun groupAnagrams(strs: Array<String>): List<List<String>> {
    return strs.groupBy { it.toCharArray().sortedArray().contentToString() }.values.toList()
}


//55 跳跃游戏
fun canJump(nums: IntArray): Boolean {
    var step = 0
    for (i in nums.indices) {
        step = max(step, nums[i])
        if (step == 0 && (i + 1 != nums.size)) return false
        step--
    }
    return true
}

//56 合并区间
fun merge(intervals: Array<IntArray>): Array<IntArray> {
    val result = mutableListOf<IntArray>()
    intervals.sortBy { it[0] }
    result.add(intervals[0])

    for (i in 1 until intervals.size) {
        val last = result.last()
        if (last[1] >= intervals[i][0]) {
            result.removeLast()
            result.add(intArrayOf(last[0], max(last[1], intervals[i][1])))
        } else {
            result.add(intervals[i])
        }
    }

    return result.toTypedArray()
}

//62 不同路径
fun uniquePaths(m: Int, n: Int): Int {
    val dp = Array(m) {
        IntArray(n)
    }

    for (i in 0 until m) {
        dp[i][0] = 1
    }

    for (i in 0 until n) {
        dp[0][i] = 1
    }

    for (i in 1 until m) {
        for (j in 1 until n) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        }
    }

    return dp[m - 1][n - 1]
}

//70 爬楼梯
fun climbStairs(n: Int): Int {
    if (n == 0) return 0
    if (n == 1) return 1
    if (n == 2) return 2

    var prepre = 1
    var pre = 2
    var max = 0
    for (i in 3..n) {
        max = pre + prepre
        prepre = pre
        pre = max
    }

    return max
}

//75 颜色分类
fun sortColors(nums: IntArray) {
    var n0 = 0
    var n1 = 0
    for (i in nums.indices) {
        val num = nums[i]
        nums[i] = 2
        if (num < 2) {
            nums[n1++] = 1
        }
        if (num < 1) {
            nums[n0++] = 0
        }
    }
}

//79 单词搜索
fun exist(board: Array<CharArray>, word: String): Boolean {

    for (i in board.indices) {
        for (j in board[0].indices) {
            if (word[0] == board[i][j]) {
                val result = backExist(board, word, 0, i, j)
                if (result) return true
            }
        }
    }

    return false
}

fun backExist(board: Array<CharArray>, word: String, current: Int, i: Int, j: Int): Boolean {
    if (i < 0 || j < 0 || i >= board.size || j >= board[0].size) return false
    if (word[current] != board[i][j]) return false
    if (word.length == current + 1) return true

    board[i][j] = '.'//标记一下，防止重复使用
    var result = backExist(board, word, current + 1, i + 1, j)
        || backExist(board, word, current + 1, i - 1, j)
        || backExist(board, word, current + 1, i, j + 1)
        || backExist(board, word, current + 1, i, j - 1)
    board[i][j] = word[current]

    return result
}

//96 不同的二叉搜索数
//二叉搜说数的中序遍历是有序数组
var numTreesMap = mutableMapOf<Int, Int>()
fun numTrees(n: Int): Int {
    if (n == 0 || n == 1) return 1
    numTreesMap[n]?.let { return it }

    var result = 0
    for (i in 1..n) {
        val left = numTrees(i - 1)
        val right = numTrees(n - i)
        result += left * right
    }
    numTreesMap[n] = result

    return result
}

//103 二叉树的锯齿层级遍历
fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    if (root == null) return result

    val deque = ArrayDeque<TreeNode>()
    deque.addFirst(root)
    var flag = false

    while (deque.isNotEmpty()) {
        val size = deque.size
        val level = ArrayDeque<Int>()

        flag = !flag

        for (i in 0 until size) {
            val cur = deque.removeFirst()
            cur.left?.let { deque.addLast(it) }
            cur.right?.let { deque.addLast(it) }

            if (flag) {
                level.addLast(cur.`val`)
            } else {
                level.addFirst(cur.`val`)
            }
        }

        result.add(level)
    }

    return result
}

//105 从前序与中序遍历序列 构造二叉树
private var indexMap: MutableMap<Int, Int> = mutableMapOf<Int, Int>()
fun myBuildTree(
    preorder: IntArray,
    inorder: IntArray?,
    preorder_left: Int,
    preorder_right: Int,
    inorder_left: Int,
    inorder_right: Int
): TreeNode? {
    //左右子树会越来越小 所以递归出口就是左边界大于有边界
    if (preorder_left > preorder_right) {
        return null
    }

    // 前序遍历中的第一个节点就是根节点
    // 在中序遍历中定位根节点
    val inorder_root = indexMap[preorder[preorder_left]]!!

    // 先把根节点建立出来
    val root = TreeNode(preorder[preorder_left])
    // 得到左子树中的节点数目
    val size_left_subtree = inorder_root - inorder_left
    // 递归地构造左子树，并连接到根节点
    // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
    root.left = myBuildTree(
        preorder,
        inorder,
        preorder_left + 1,
        preorder_left + size_left_subtree,
        inorder_left,
        inorder_root - 1
    )
    // 递归地构造右子树，并连接到根节点
    // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
    root.right = myBuildTree(
        preorder,
        inorder,
        preorder_left + size_left_subtree + 1,
        preorder_right,
        inorder_root + 1,
        inorder_right
    )
    return root
}

fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
    val n = preorder.size
    // 构造哈希映射，帮助我们快速定位根节点
    for (i in 0 until n) {
        indexMap[inorder[i]] = i
    }
    return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1)
}


//198打家劫色
fun rob(nums: IntArray): Int {
    var dp = IntArray(nums.size + 1)
    dp[1] = nums[0]

    for (i in 2..nums.size) {
        dp[i] = max(dp[i - 1], dp[i - 2] + nums[i - 1])
    }

    return dp[nums.size]
}

//128 最长连续序列
fun longestConsecutive(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    nums.sort()
    var max = 1
    var cur = 1
    for (i in 1 until nums.size) {
        if (nums[i] == nums[i - 1]) continue
        if (nums[i] == nums[i - 1] + 1) {
            cur += 1
            max = max(cur, max)
        } else {
            cur = 1
        }
    }

    return max
}

//152 乘积最大子数组
fun maxProduct(nums: IntArray): Int {
    var dp = Array(nums.size) {
        IntArray(2)
    }
    dp[0][0] = nums[0]
    dp[0][1] = nums[0]

    for (i in 1 until nums.size) {
        val cur = nums[i]
        if (cur < 0) {
            dp[i][0] = max(dp[i - 1][1] * cur, cur)
            dp[i][1] = min(dp[i - 1][0] * cur, cur)
        } else {
            dp[i][0] = max(dp[i - 1][0] * cur, cur)
            dp[i][1] = min(dp[i - 1][1] * cur, cur)
        }
    }

    var res = dp[0][0]
    for (i in 1 until nums.size) {
        res = max(res, dp[i][0])
    }
    return res
}

//169 多数元素
// 用map计数，排序，或者摩尔投票
fun majorityElement(nums: IntArray): Int {
    var count = 0
    var result = 0
    for (i in nums.indices) {
        if (count == 0) {
            result = nums[i]
            count++
        } else {
            if (result == nums[i]) {
                count++
            } else {
                count--
            }
        }
    }

    return result
}

//191 位1的个数
fun hammingWeight(n: Int): Int {
    var result = 0
    var n = n
    while (n != 0) {
        result++
        n = n and (n - 1)
    }

    return result
}

//200 岛屿数量
fun numIslands(grid: Array<CharArray>): Int {
    var result = 0
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == '1') result++
            numIslandsDFS(grid, i, j)
        }
    }

    return result
}

fun numIslandsDFS(grid: Array<CharArray>, i: Int, j: Int) {
    if (i < 0 || i >= grid.size || j < 0 || j >= grid[i].size) return
    if (grid[i][j] == '0') return

    grid[i][j] = '0'

    numIslandsDFS(grid, i + 1, j)
    numIslandsDFS(grid, i - 1, j)
    numIslandsDFS(grid, i, j + 1)
    numIslandsDFS(grid, i, j - 1)
}

//202 快乐数
fun isHappy(n: Int): Boolean {
    if (n == 1) return true
    var fast = getNextNum(n)
    var slow = n

    while (fast != 1 && fast != slow) {
        fast = getNextNum(getNextNum(fast))
        slow = getNextNum(slow)
    }

    return fast == 1
}

fun getNextNum(n: Int): Int {
    var result = 0
    var n = n
    while (n != 0) {
        var i = n % 10
        result += i * i
        n /= 10
    }

    return result
}


//206 反转链表
fun reverseList3(head: ListNode?): ListNode? {
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

//215 数组中的第K个最大元素
//基于堆排序的选择方法
fun findKthLargest(nums: IntArray, k: Int): Int {
    val size = nums.size
    for (i in size / 2 - 1 downTo 0) {
        adjustHeap(nums, i, size)
    }

    for (i in size - 1 downTo nums.size - k + 1) {
        swapChild(nums, i, 0)
        adjustHeap(nums, 0, i)
    }

    return nums[0]
}

fun adjustHeap(nums: IntArray, parent: Int, end: Int) {
    val left = parent * 2 + 1
    val right = left + 1
    var target = parent

    if (left < end && nums[left] > nums[target]) target = left
    if (right < end && nums[right] > nums[target]) target = right

    if (target != parent) {
        swapChild(nums, target, parent)
        adjustHeap(nums, target, end)
    }
}

fun swapChild(nums: IntArray, i: Int, j: Int) {
    val temp = nums[i]
    nums[i] = nums[j]
    nums[j] = temp
}

//基于快速排序的选择方法
fun findKthLargest1(_nums: IntArray, k: Int): Int {
    return quickSortFind(_nums, 0, _nums.size - 1, k)
}

fun quickSortFind(nums: IntArray, left: Int, right: Int, k: Int): Int {

    val target = nums[left]
    var first = left
    var last = right

    //注意是从大到小，因为我们找的是第K 大
    while (first < last) {
        while (first < last && nums[last] <= target) last--
        nums[first] = nums[last]

        while (first < last && nums[first] >= target) first++
        nums[last] = nums[first]
    }
    nums[first] = target

    if (first + 1 == k) return nums[first]

    return if (first + 1 > k) {
        quickSortFind(nums, left, first - 1, k)
    } else {
        quickSortFind(nums, first + 1, right, k)
    }
}


//221 最大正方形
fun maximalSquare(matrix: Array<CharArray>): Int {
    var result = 0

    val dp = Array(matrix.size) {
        IntArray(matrix[0].size)
    }
    for (i in matrix.indices) {
        dp[i][0] = matrix[i][0].toString().toInt()
        result = max(result, dp[i][0])
    }

    for (i in matrix[0].indices) {
        dp[0][i] = matrix[0][i].toString().toInt()
        result = max(result, dp[0][i])
    }

    for (i in 1 until matrix.size) {
        for (j in 1 until matrix[i].size) {
            if (matrix[i][j] == '1') {
                dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1
                result = max(result, dp[i][j])
            }
        }
    }

    return result * result
}

//236 二叉树的最近公共祖先
fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
    if (root == null || root.`val` == p!!.`val` || root.`val` == q!!.`val`) {
        return root
    }
    val left = lowestCommonAncestor(root.left, p, q)
    val right = lowestCommonAncestor(root.right, p, q)

    if (left == null) return right
    if (right == null) return left

    return root
}

//237 删除链表中的节点
fun deleteNode(node: ListNode?) {
    node!!.`val` = node.next!!.`val`
    node.next = node.next!!.next
}

//238 除自身以外数组的乘积
fun productExceptSelf(nums: IntArray): IntArray {
    val arr = IntArray(nums.size)
    arr[0] = 1

    for (i in 1 until nums.size) {
        arr[i] = arr[i - 1] * nums[i - 1]
    }

    var tmep = 1
    for (i in nums.size - 2 downTo 0) {
        tmep *= nums[i + 1]
        arr[i] = arr[i] * tmep
    }

    return arr
}

//279 完全平方数
fun numSquares(n: Int): Int {
    val dp = IntArray(n + 1)
    for (i in 1..n) {
        dp[i] = i
        var j = 1
        while (i - j * j >= 0) {
            dp[i] = min(dp[i], dp[i - j * j] + 1)
            j++
        }
    }
    return dp[n]
}


//338 比特位计数
//2的整次方只有1个1，非整用状态转移推导
fun countBits(n: Int): IntArray {
    val dp = IntArray(n + 1)
    var flag = 0
    for (i in 1..n) {
        if ((i and (i - 1)) == 0) {//2的倍数
            flag = i
        }
        dp[i] = dp[i - flag] + 1
    }

    return dp
}

//347 前K个高频元素
fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val map = nums.groupBy { it }.mapValues { it.value.size }
    val sortedBy = map.toList().sortedBy { it.second }.reversed()
    return sortedBy.subList(0, k).map { it.first }.toIntArray()
}

//426

var pathSum3Res = 0

//437 路径总和3
fun pathSum3(root: TreeNode?, targetSum: Int): Int {
    val mutableMapOf = mutableMapOf<Int, Int>()
    mutableMapOf[0] = 1
    pathSum3Fun(root, targetSum, 0, mutableMapOf)
    return pathSum3Res
}

fun pathSum3Fun(root: TreeNode?, targetSum: Int, cur: Int, map: MutableMap<Int, Int>) {
    if (root == null) return

    var cur = cur
    cur += root.`val`

    pathSum3Res += map.getOrDefault(cur - targetSum, 0)
    map[cur] = map.getOrDefault(cur, 0) + 1


    pathSum3Fun(root.left, targetSum, cur, map)
    pathSum3Fun(root.right, targetSum, cur, map)

    map[cur] = map.getOrDefault(cur, 0) - 1
}


//448 找到所有数组中消失的数字
fun findDisappearedNumbers(nums: IntArray): List<Int> {
    val mutableListOf = mutableListOf<Int>()

    for (i in nums.indices) {
        if (nums[abs(nums[i]) - 1] > 0) {
            nums[abs(nums[i]) - 1] *= -1
        }
    }

    for (i in nums.indices) {
        if (nums[i] > 0) {
            mutableListOf.add(i + 1)
        }
    }

    return mutableListOf
}


//461 汉明距离
fun hammingDistance(x: Int, y: Int): Int {
    var i = x xor y
    var res = 0
    while (i != 0) {
        if ((i and 1) != 0) {
            res += 1
        }
        i = i shr 1
    }
    return res
}

//494 目标和
var findTargetSumWaysResult = 0
fun findTargetSumWays1(nums: IntArray, target: Int): Int {
    findTargetSumWaysBack(nums, 0, target)

    return findTargetSumWaysResult
}

fun findTargetSumWaysBack(nums: IntArray, start: Int, target: Int) {
    if (nums.size == start) {
        if (target == 0) findTargetSumWaysResult++
        return
    }

    findTargetSumWaysBack(nums, start + 1, target - nums[start])
    findTargetSumWaysBack(nums, start + 1, target + nums[start])
}

//560 和为K的子数组
fun subarraySum(nums: IntArray, k: Int): Int {
    val n = nums.size
    var ans = 0
    val sum = IntArray(n + 1)
    for (i in 1..n) sum[i] = sum[i - 1] + nums[i - 1]
    val map: MutableMap<Int, Int> = HashMap()
    map[0] = 1

    for (i in 1..n) {
        val t = sum[i]
        val d = t - k
        ans += map.getOrDefault(d, 0)
        map[t] = map.getOrDefault(t, 0) + 1
    }

    return ans
}

//567 反转字符串中的单词
fun reverseWords(s: String): String {
    var toCharArray = s.toCharArray()

    var start = 0
    for (i in toCharArray.indices) {
        if (toCharArray[i] == ' ') {
            reverseWord(toCharArray, start, i - 1)
            start = i + 1
            continue
        }

        if (i == toCharArray.size - 1) {
            reverseWord(toCharArray, start, i)
        }
    }

    return String(toCharArray)
}

fun reverseWord(toCharArray: CharArray, start: Int, i: Int) {
    var start = start
    var i = i

    while (start < i) {
        val temp = toCharArray[start]
        toCharArray[start] = toCharArray[i]
        toCharArray[i] = temp
        start++
        i--
    }
}


//581 最短无序连续子数组
fun findUnsortedSubarray(nums: IntArray): Int {
    val clone = nums.clone()
    clone.sort()

    var left = 0
    var right = nums.size - 1

    while (left <= right && nums[left] == clone[left]) left++
    while (left <= right && nums[right] == clone[right]) right--

    return right - left + 1
}

//617 合并二叉树
fun mergeTrees(root1: TreeNode?, root2: TreeNode?): TreeNode? {
    if (root2 == null && root1 == null) return null
    if (root1 == null) return root2
    if (root2 == null) return root1

    root1.`val` = root1.`val`.plus(root2.`val`)
    root1.left = mergeTrees(root1.left, root2.left)
    root1.right = mergeTrees(root1.right, root2.right)

    return root1
}

//647 回文子串
fun countSubstrings(s: String): Int {
    var result = 0

    for (i in s.indices) {
        result += countSubstringsFun(s, i, i)
        result += countSubstringsFun(s, i, i + 1)
    }

    return result
}

fun countSubstringsFun(s: String, start: Int, end: Int): Int {
    if (start < 0 || end >= s.length) return 0
    if (s[start] == s[end]) return 1 + countSubstringsFun(s, start - 1, end + 1)
    return 0
}

//739 每日温度
fun dailyTemperatures(temperatures: IntArray): IntArray {
    val result = IntArray(temperatures.size)
    val stack = ArrayDeque<Int>()

    for (i in temperatures.indices) {

        while (stack.isNotEmpty() && temperatures[i] > temperatures[stack.first()]) {
            result[stack.first()] = i - stack.first()
            stack.removeFirst()
        }

        stack.addFirst(i)
    }

    return result
}
