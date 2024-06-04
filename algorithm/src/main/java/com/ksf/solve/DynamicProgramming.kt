package com.ksf.solve

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    maxProfit5(intArrayOf(1, 2))
}


//53 最大子数组和
//滑动窗口
fun maxSubArray(nums: IntArray): Int {
    var left = 0
    var right = 0
    var windowSum = 0
    var maxSum = Int.MIN_VALUE
    while (right < nums.size) {
        windowSum += nums[right]
        right++
        maxSum = if (windowSum > maxSum) windowSum else maxSum

        while (windowSum < 0) {
            windowSum -= nums[left]
            left++
        }
    }

    return maxSum
}

//动态规划
fun maxSubArray1(nums: IntArray): Int {
    val array = IntArray(nums.size)
    array[0] = nums[0]
    for (i in 1 until nums.size) {
        if (array[i - 1] < 0) {
            array[i] = nums[i]
        } else {
            array[i] = nums[i] + array[i - 1]
        }
    }

    return array.maxOrNull() ?: 0
}

//64 最小路径和
fun minPathSum(grid: Array<IntArray>): Int {
    val dp = Array(grid.size) {
        IntArray(grid[0].size)
    }

    dp[0][0] = grid[0][0]
    for (i in 1 until grid.size) dp[i][0] = grid[i][0] + dp[i - 1][0]
    for (j in 1 until grid[0].size) dp[0][j] = grid[0][j] + dp[0][j - 1]

    for (i in 1 until grid.size) {
        for (j in 1 until grid[0].size) {
            dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
        }
    }

    return dp[grid.size - 1][grid[0].size - 1]
}

//72 编辑距离
//记忆搜索法
fun minDistance(word1: String, word2: String): Int {
    val array = Array(word1.length + 1) {
        IntArray(word2.length + 1).apply { this.fill(-1) }
    }
    return minDistanceFun(word1, word1.length - 1, word2, word2.length - 1, array)
}

fun minDistanceFun(word1: String, left1: Int, word2: String, left2: Int, array: Array<IntArray>): Int {
    if (left1 < 0) return left2 + 1
    if (left2 < 0) return left1 + 1

    if (array[left1][left2] != -1) return array[left1][left2]

    if (word1[left1] == word2[left2]) {
        array[left1][left2] = minDistanceFun(word1, left1 - 1, word2, left2 - 1, array)
        return array[left1][left2]
    }
    array[left1][left2] = min(
        minDistanceFun(word1, left1 - 1, word2, left2, array) + 1,
        minDistanceFun(word1, left1, word2, left2 - 1, array) + 1,
        minDistanceFun(word1, left1 - 1, word2, left2 - 1, array) + 1
    )

    return array[left1][left2]
}

fun min(a: Int, b: Int, c: Int): Int = min(a, min(b, c))

//动态规划
fun minDistance1(word1: String, word2: String): Int {
    val array = Array(word1.length + 1) {
        IntArray(word2.length + 1)
    }
    for (i in 1..word1.length) array[i][0] = i
    for (j in 1..word2.length) array[0][j] = j

    for (i in 1..word1.length) {
        for (j in 1..word2.length) {
            if (word1[i - 1] == word2[j - 1]) {
                array[i][j] = array[i - 1][j - 1]
            } else {
                array[i][j] = min(array[i - 1][j - 1], array[i - 1][j], array[i][j - 1]) + 1
            }
        }
    }

    return array[word1.length][word2.length]
}

//121 买卖股票时机
fun maxProfit(prices: IntArray): Int {
    val dp = Array(prices.size) {
        IntArray(2)
    }

    dp[0][1] = -prices[0]
    for (i in 1 until prices.size) {
        dp[i][0] = max(dp[i - 1][1] + prices[i], dp[i - 1][0])
        dp[i][1] = max(-prices[i], dp[i - 1][1])
    }

    return dp[prices.size - 1][0]
}

//122 买卖股票时机2
fun maxProfit2(prices: IntArray): Int {
    val dp = Array(prices.size) {
        IntArray(2)
    }

    dp[0][1] = -prices[0]
    for (i in 1 until prices.size) {
        dp[i][0] = max(dp[i - 1][1] + prices[i], dp[i - 1][0])
        dp[i][1] = max(dp[i - 1][0] - prices[i], dp[i - 1][1])
    }

    return dp[prices.size - 1][0]
}

//123 买卖股票时机3
fun maxProfit3(prices: IntArray): Int {
    val dp = Array(prices.size) {
        Array(3) {
            IntArray(2)
        }
    }

    dp[0][1][1] = -prices[0]
    dp[0][2][1] = -prices[0]
    for (i in 1 until prices.size) {
        for (k in 1..2) {
            dp[i][k][0] = max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i])
            dp[i][k][1] = max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i])
        }
    }

    return dp[prices.size - 1][2][0]
}

//188 买卖股票最佳时机4
fun maxProfit4(k: Int, prices: IntArray): Int {
    val dp = Array(prices.size) {
        Array(k + 1) {
            IntArray(2)
        }
    }

    for (i in 0..k) {
        dp[0][k][0] = 0
        dp[0][k][1] = -prices[0]
    }

    for (i in 1 until prices.size) {
        dp[i][0][0] = 0
        dp[i][0][1] = max(dp[i - 1][0][1], dp[i - 1][0][0] - prices[i])
    }

    for (i in 1 until prices.size) {
        for (j in 1..k) {
            dp[i][j][1] = max(dp[i - 1][j - 1][0] - prices[i], dp[i - 1][j][1])
            dp[i][j][0] = max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][0])
        }
    }

    return dp[prices.size - 1][k][0]
}

//309 买卖股票最佳时机含冷冻期
fun maxProfit5(prices: IntArray): Int {
    val dp = Array(prices.size) {
        IntArray(2)
    }

    dp[0][1] = -prices[0]
    for (i in 1 until prices.size) {
        dp[i][0] = max(dp[i - 1][0], dp[i - 1][1] + prices[i])
        dp[i][1] = max(dp[i - 1][1], (if (i < 2) 0 else dp[i - 2][0]) - prices[i])
    }

    return dp[prices.size - 1][0]
}

//714 买卖股票最佳时机含手续费
fun maxProfit6(prices: IntArray, fee: Int): Int {
    var dp = Array(prices.size) {
        IntArray(2)
    }

    dp[0][1] = -prices[0] - fee
    for (i in 1 until prices.size) {
        dp[i][0] = max(dp[i - 1][0], dp[i - 1][1] + prices[i])
        dp[i][1] = max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee)
    }

    return dp[prices.size - 1][0]
}

//139 单词拆分
fun wordBreak(s: String, wordDict: List<String>): Boolean {
    val dp = BooleanArray(s.length + 1)
        .apply { this.fill(false) }
        .apply { this[0] = true }

    for (i in 1..s.length) {
        tag@ for (j in 0 until i) {
            if (dp[j] && wordDict.contains(s.substring(j, i))) {
                dp[i] = true
                break@tag
            }
        }
    }

    return dp[s.length]
}


//300 最长递增子序列
fun lengthOfLIS(nums: IntArray): Int {
    val cache = IntArray(nums.size + 1).apply { this.fill(1) }

    for (i in nums.indices) {
        for (j in 0 until i) {
            if (nums[i] > nums[j]) {
                cache[i] = max(cache[i], cache[j] + 1)
            }
        }
    }

    return cache.maxOrNull() ?: 0
}

//354 俄罗斯套娃信封
fun maxEnvelopes(envelopes: Array<IntArray>): Int {
    var cache = IntArray(envelopes.size + 1).apply { this.fill(1) }

    Arrays.sort(envelopes) { a, b -> if (a[0] == b[0]) b[1] - a[1] else a[0] - b[0] }
    for (i in envelopes.indices) {
        for (j in 0 until i) {
            val first = envelopes[i]
            val second = envelopes[j]
            if (first.first() > second.first() && first.last() > second.last()) {
                cache[i] = max(cache[i], cache[j] + 1)
            }
        }
    }

    return cache.maxOrNull() ?: 0
}

//509 斐波那契数
fun fib(n: Int): Int {
    var a = 0
    var b = 1
    var sum: Int
    for (i in 0 until n) {
        sum = a + b
        a = b
        b = sum
    }
    return a
}

//516 最长回文子序列
fun longestPalindromeSubseq(s: String): Int {
    val dp = Array(s.length + 1) {
        IntArray(s.length + 1)
    }
    for (i in s.length - 1 downTo 0) { // 从后往前遍历 保证情况不漏
        dp[i][i] = 1 // 初始化
        for (j in i + 1 until s.length) {
            if (s[i] == s[j]) {
                dp[i][j] = dp[i + 1][j - 1] + 2
            } else {
                dp[i][j] = max(dp[i][j], max(dp[i + 1][j], dp[i][j - 1]))
            }
        }
    }

    return dp[0][s.length]
}

//583 两个字符串的删除操作
fun minDistanceString(word1: String, word2: String): Int {
    val dp = Array(word1.length + 1) {
        IntArray(word2.length + 1)
    }

    for (i in 1..word1.length) dp[i][0] = i
    for (j in 1..word2.length) dp[0][j] = j

    for (i in 1..word1.length) {
        for (j in 1..word2.length) {
            if (word1[i - 1] == word2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1]
            } else {
                dp[i][j] = min(dp[i][j - 1], dp[i - 1][j]) + 1
            }
        }
    }

    return dp[word1.length][word2.length]
}


//712 两个字符串的最小ASCII删除和
fun minimumDeleteSum(s1: String, s2: String): Int {
    var dp = Array(s1.length + 1) {
        IntArray(s2.length + 1)
    }
    for (i in 1..s1.length) dp[i][0] = dp[i - 1][0] + s1[i - 1].code
    for (j in 1..s2.length) dp[0][j] = dp[0][j - 1] + s2[j - 1].code

    for (i in 1..s1.length) {
        for (j in 1..s2.length) {
            if (s1[i - 1] == s2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1]
            } else {
                dp[i][j] = min(dp[i][j - 1] + s2[j - 1].code, dp[i - 1][j] + s1[i - 1].code)
            }
        }
    }

    return dp[s1.length][s2.length]
}

//1143 最长公共子序列
fun longestCommonSubsequence(text1: String, text2: String): Int {
    var dp = Array(text1.length + 1) {
        IntArray(text2.length + 1)
    }
    dp[0][0] = 0

    for (i in 1..text1.length) {
        for (j in 1..text2.length) {
            if (text1[i - 1] == text2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
            } else {
                dp[i][j] = max(dp[i][j - 1], dp[i - 1][j])
            }
        }
    }

    return dp[text1.length][text2.length]
}

//1312 让字符串成为回文串的最少插入次数
fun minInsertions(s: String): Int {
    val dp = Array(s.length) {
        IntArray(s.length)
    }

    for (i in s.length - 1 downTo 0) {
        for (j in i + 1 until s.length) {
            if (s[i] == s[j]) {
                dp[i][j] = dp[i + 1][j - 1]
            } else {
                dp[i][j] = min(dp[i + 1][j], dp[i][j - 1]) + 1
            }
        }
    }

    return dp[0][s.length - 1]
}

/**
背包问题解法：

01 背包问题：

如果是 01 背包，即数组中的元素不可重复使用，外循环遍历 arrs， 内循环遍历 target，且内循环倒序：

完全背包问题：

（1）如果是完全背包，即数组中的元素可重复使用并且不考虑元素之间顺序，arrs 放在外循环（保证 arrs 按顺序），target在内循环。且内循环正序。

(2）如果组合问题需考虑元素之间的顺序，需将 target 放在外循环，将 arrs 放在内循环，且内循环正序。
 */


//322 零钱兑换
//记忆搜索法
val coinMapOf = mutableMapOf<Int, Int>()
fun coinChange(coins: IntArray, amount: Int): Int {
    if (amount == 0) return 0
    if (amount < 0) return -1
    val cache = coinMapOf[amount]
    if (cache != null) return cache

    var minCoin = Int.MAX_VALUE
    for (coin in coins) {
        val dp = coinChange(coins, amount - coin)
        if (dp == -1) continue
        minCoin = min(minCoin, dp + 1)
    }
    coinMapOf[amount] = if (minCoin == Int.MAX_VALUE) -1 else minCoin
    return coinMapOf[amount]!!
}

//动态规划
fun coinChange1(coins: IntArray, amount: Int): Int {
    val cache = IntArray(amount + 1)
    Arrays.fill(cache, amount + 1)
    cache[0] = 0

    for (i in 1..amount) {
        for (coin in coins) {
            if (i - coin < 0) continue
            cache[i] = min(cache[i], 1 + cache[i - coin])
        }
    }

    return if (cache[amount] == amount + 1) -1 else cache[amount]
}


//416 分割等和子集
fun canPartition(nums: IntArray): Boolean {
    if (nums.size < 2) return false
    val sum = nums.sum()
    if (sum and 1 > 0) return false
    val target = sum / 2
    if (target < nums.maxOrNull()!!) return false

    val dp = BooleanArray(target + 1)
    dp[0] = true

    for (num in nums) {
        for (j in target downTo 0) {
            if (j - num >= 0) {
                dp[j] = dp[j] || dp[j - num]
            }
        }
    }
    return dp[target]
}


//494 目标和
// 记 x=正数和 y=负数和
// target = x -y
// sum = x + y
// x = (target + sum)/2
fun findTargetSumWays2(nums: IntArray, target: Int): Int {
    if (abs(target) > abs(nums.sum())) return 0

    val target = (target + nums.sum()) / 2
    val dp = Array(nums.size + 1) {
        IntArray(target + 1)
    }

    dp[0][0] = 1

    for (i in 1..nums.size) {
        val num = nums[i - 1]
        for (j in 0..target) {
            if (j >= num) {
                dp[i][j] = dp[i - 1][j - num] + dp[i - 1][j]
            } else {
                dp[i][j] = dp[i - 1][j]
            }
        }
    }

    return dp[nums.size][target]
}

fun findTargetSumWays(nums: IntArray, target: Int): Int {
    val target = (nums.sum() + target) / 2
    val dp = IntArray(target + 1)
    dp[0] = 1

    for (num in nums) {
        for (i in target downTo num) {
            dp[i] += dp[i - num];
        }
    }

    return dp[target]
}