package com.ksf.solve

//22 括号生成
var parenthesisRes = mutableListOf<String>()
fun generateParenthesis(n: Int): List<String> {
    generateParenthesis("", n, n)
    return parenthesisRes
}

fun generateParenthesis(track: String, left: Int, right: Int) {
    if (left > right || left < 0) return

    if (left == 0 && right == 0) {
        parenthesisRes.add(track)
        return
    }

    var track = track
    track += ("(")
    generateParenthesis(track, left - 1, right)
    track = track.removeSuffix("(")

    track += (")")
    generateParenthesis(track, left, right - 1)
    track.removeSuffix(")")
}

//37 数独
fun solveSudoku(board: Array<CharArray>): Unit {
    sudoBackTrack(board, 0, 0)
}

fun sudoBackTrack(board: Array<CharArray>, row: Int, column: Int): Boolean {
    if (column > 8) return sudoBackTrack(board, row + 1, 0)
    if (row > 8) return true
    if (board[row][column] != '.') return sudoBackTrack(board, row, column + 1)

    for (i in '1'..'9') {
        if (!isvalidSudo(board, row, column, i)) continue

        board[row][column] = i
        if (sudoBackTrack(board, row, column + 1)) return true
        board[row][column] = '.'
    }

    return false
}

fun isvalidSudo(board: Array<CharArray>, row: Int, column: Int, target: Char): Boolean {
    for (i in 0..8) {
        //判断同列
        if (board[row][i] == target) return false
        //判断同行
        if (board[i][column] == target) return false
    }

    //判断同框
    val startRow = (row / 3) * 3
    val startColumn = (row / 3) * 3

    for (i in startRow until startRow + 3) {
        for (j in startColumn until startColumn + 3) {
            if (board[i][j] == target) return false
        }
    }

    return true
}

//39 组合总和
var combinationSumRes = mutableListOf<MutableList<Int>>()
fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    val track = mutableListOf<Int>()
    combinationBackTrack(candidates, track, target, 0)
    return combinationSumRes
}

//定义一个start ，相当于去重
fun combinationBackTrack(candidates: IntArray, track: MutableList<Int>, target: Int, start: Int) {
    if (target < 0) return
    if (target == 0) combinationSumRes.add(mutableListOf<Int>().apply { this.addAll(track) })

    for (i in start until candidates.size) {
        track.add(candidates[i])
        combinationBackTrack(candidates, track, target - candidates[i], i)
        track.removeLast()
    }
}


//40 组合总和2 元素可以重复
var combinationSumRes2 = mutableListOf<MutableList<Int>>()
fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
    val track = mutableListOf<Int>()
    var used = BooleanArray(candidates.size)
    // 排序，方便去重
    candidates.sort()
    combinationBackTrack2(candidates, track, target, 0, used)
    return combinationSumRes2
}

fun combinationBackTrack2(candidates: IntArray, track: MutableList<Int>, target: Int, start: Int, used: BooleanArray) {
    if (target < 0) return
    if (target == 0) combinationSumRes2.add(mutableListOf<Int>().apply { this.addAll(track) })

    for (i in start until candidates.size) {
        if (i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]) continue
        track.add(candidates[i])
        used[i] = true

        combinationBackTrack2(candidates, track, target - candidates[i], i + 1, used)

        track.removeLast()
        used[i] = false
    }
}

// 46 全排列
var permuteRes = mutableListOf<MutableList<Int>>()
fun permute(nums: IntArray): List<List<Int>> {
    val level = mutableListOf<Int>()
    val used = BooleanArray(nums.size)
    permuteBackTrack(nums, level, used)
    return permuteRes
}

fun permuteBackTrack(nums: IntArray, level: MutableList<Int>, used: BooleanArray) {
    if (level.size == nums.size) {
        permuteRes.add(mutableListOf<Int>().apply { this.addAll(level) })
        return
    }

    for (i in nums.indices) {
        if (used[i]) continue

        used[i] = true
        level.add(nums[i])
        permuteBackTrack(nums, level, used)
        level.removeLast()
        used[i] = false
    }
}

// 47 全排列2  带重复元素，需去重
var permuteRes1 = mutableListOf<MutableList<Int>>()
fun permuteUnique(nums: IntArray): List<List<Int>> {
    val level = mutableListOf<Int>()
    val used = BooleanArray(nums.size)
    //排序，让重复的元素在一起
    nums.sort()
    permuteBackTrack1(nums, level, used)
    return permuteRes1
}

fun permuteBackTrack1(nums: IntArray, level: MutableList<Int>, used: BooleanArray) {
    if (level.size == nums.size) {
        permuteRes1.add(mutableListOf<Int>().apply { this.addAll(level) })
        return
    }

    for (i in nums.indices) {
        // 重复元素就不加了，必须是前一个被用过了
        if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) continue

        used[i] = true
        level.add(nums[i])
        permuteBackTrack1(nums, level, used)
        level.removeLast()
        used[i] = false
    }
}

// 51 N皇后
var queensRes = mutableListOf<List<String>>()
fun solveNQueens(n: Int): List<List<String>> {
    //初始化棋盘
    val chessboard = Array(n) { BooleanArray(n) }

    queensBackTrack(chessboard, 0)

    return queensRes
}

fun queensBackTrack(chessboard: Array<BooleanArray>, row: Int) {
    val chessLen = chessboard.size
    if (row == chessLen) {
        queensRes.add(boardToString(chessboard))
        return
    }

    //遍历每行
    for (column in 0 until chessLen) {
        // 能不能落子
        if (!isValidQueen(chessboard, row, column)) continue

        // 占坑
        chessboard[row][column] = true
        // 判断下一行
        queensBackTrack(chessboard, row + 1)
        // 脱坑
        chessboard[row][column] = false
    }

}

fun isValidQueen(chessboard: Array<BooleanArray>, row: Int, column: Int): Boolean {
    for (i in 0 until row) {
        //判断正上列
        if (chessboard[i][column]) return false

        //判断左上列
        if (column - (row - i) >= 0) {
            if (chessboard[i][column - (row - i)]) return false
        }
        //判断右上列
        if (column + (row - i) < chessboard.size) {
            if (chessboard[i][column + (row - i)]) return false
        }
    }

    return true
}

fun boardToString(chessboard: Array<BooleanArray>): List<String> {
    return chessboard.map { row ->
        row.joinToString("") { if (it) "Q" else "." }
    }
}


//77 组合
var combineRes = mutableListOf<List<Int>>()
fun combine(n: Int, k: Int): List<List<Int>> {
    val track = mutableListOf<Int>()
    combineBackTrack(n, k, track, 1)

    return combineRes
}

fun combineBackTrack(n: Int, k: Int, track: MutableList<Int>, start: Int) {
    if (track.size == k) {
        combineRes.add(mutableListOf<Int>().apply { this.addAll(track) })
        return
    }

    for (i in start..n) {
        track.add(i)
        combineBackTrack(n, k, track, i + 1)
        track.removeLast()
    }
}

//78 子集
val subsetRes = mutableListOf<MutableList<Int>>()
fun subsets(nums: IntArray): List<List<Int>> {
    subsetRes.add(mutableListOf())
    subsetBackTrack(nums, 0, mutableListOf())
    return subsetRes
}

fun subsetBackTrack(nums: IntArray, start: Int, track: MutableList<Int>) {
    if (start > nums.size - 1) return

    for (i in start until nums.size) {
        track.add(nums[i])
        subsetRes.add(mutableListOf<Int>().apply { this.addAll(track) })
        subsetBackTrack(nums, i + 1, track)
        track.removeLast()
    }
}


// 90 子集 包含重复元素
var subsetsWithDupRes = mutableListOf<MutableList<Int>>()
fun subsetsWithDup(nums: IntArray): List<List<Int>> {
    val track = mutableListOf<Int>()
    val used = BooleanArray(nums.size)
    subsetsWithDupRes.add(mutableListOf())

    nums.sort()
    subsetsWithDupBack(nums, 0, track, used)

    return subsetsWithDupRes
}

fun subsetsWithDupBack(nums: IntArray, start: Int, track: MutableList<Int>, used: BooleanArray) {
    if (start > nums.size - 1) return

    for (i in start until nums.size) {
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue
        track.add(nums[i])
        used[i] = true
        subsetsWithDupRes.add(mutableListOf<Int>().apply { this.addAll(track) })
        subsetsWithDupBack(nums, i + 1, track, used)
        used[i] = false
        track.removeLast()
    }
}

//698 划分为k个相等的子集
fun canPartitionKSubsets(nums: IntArray, k: Int): Boolean {
    val sum = nums.sum()
    if (sum % k != 0) return false
    return partitionKSubsetBackTrack(nums, 0, sum / k, sum / k, BooleanArray(nums.size), k)
}

fun partitionKSubsetBackTrack(
    nums: IntArray,
    start: Int,
    finalTarget: Int,
    target: Int,
    used: BooleanArray,
    k: Int
): Boolean {
    if (k == 0) return true
    if (target == 0) return partitionKSubsetBackTrack(nums, 0, finalTarget, finalTarget, used, k - 1)

    var target = target

    for (i in start until nums.size) {
        if (used[i]) continue
        if (target - nums[i] < 0) continue

        target -= nums[i]
        used[i] = true

        if (partitionKSubsetBackTrack(nums, i + 1, finalTarget, target, used, k)) return true

        used[i] = false
        target += nums[i]
    }

    return false
}


fun main() {
    println("(((".removeSuffix("("))
    generateParenthesis(3)
}