package com.ksf.solve

import com.ksf.dataStructure.ListNode
import com.ksf.dataStructure.Node
import com.ksf.dataStructure.TreeNode
import com.ksf.solve.min
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * BFS  广度优先搜索（Breadth-First Search），
 * DFS  深度优先搜索（Depth-First Search）。
 */


//94 二叉树的中序遍历 递归写法 --DFS
fun inorderTraversal(root: TreeNode?): List<Int> {
    val mutableList = mutableListOf<Int>()
    if (root == null) return mutableList

    inorderTraversalFun(root, mutableList)
    return mutableList
}

fun inorderTraversalFun(node: TreeNode?, list: MutableList<Int>) {
    node?.let {
        inorderTraversalFun(node.left, list)
        list.add(node.`val`)
        inorderTraversalFun(node.right, list)
    }
}

//迭代写法--BFS
fun inorderTraversal1(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    if (root == null) return result

    val deque = ArrayDeque<TreeNode>()
    var cur = root
    while (cur != null || deque.isNotEmpty()) {
        if (cur != null) {
            deque.addFirst(cur)
            cur = cur.left
        } else {
            val first = deque.removeFirst()
            result.add(first.`val`)
            cur = first.right
        }
    }
    return result
}


//144二叉树前序遍历  递归写法--dfs
fun preorderTraversal(root: TreeNode?): List<Int> {
    val mutableListOf = mutableListOf<Int>()
    preorderTraversalFun(root, mutableListOf)
    return mutableListOf
}

fun preorderTraversalFun(node: TreeNode?, list: MutableList<Int>) {
    if (node == null) return

    list.add(node.`val`)
    preorderTraversalFun(node.left, list)
    preorderTraversalFun(node.right, list)
}

//迭代写法--bfs
fun preorderTraversal1(root: TreeNode?): List<Int> {
    val mutableListOf = mutableListOf<Int>()
    root?.let {
        val arrayDeque = ArrayDeque<TreeNode>()
        arrayDeque.addFirst(it)

        while (arrayDeque.isNotEmpty()) {
            val last = arrayDeque.removeFirst()
            mutableListOf.add(last.`val`)
            last.right?.let { arrayDeque.addFirst(last.right!!) }
            last.left?.let { arrayDeque.addFirst(last.left!!) }
        }
    }
    return mutableListOf
}

//145二叉树后序遍历  递归写法--dfs
fun postorderTraversal(root: TreeNode?): List<Int> {
    val mutableListOf = mutableListOf<Int>()
    postorderTraversalFun(root, mutableListOf)
    return mutableListOf
}

fun postorderTraversalFun(node: TreeNode?, list: MutableList<Int>) {
    if (node == null) return

    postorderTraversalFun(node.left, list)
    postorderTraversalFun(node.right, list)
    list.add(node.`val`)
}

//迭代写法--bfs
fun postorderTraversal1(root: TreeNode?): List<Int> {
    val mutableListOf = mutableListOf<Int>()
    if (root == null) return mutableListOf
    val deque = ArrayDeque<TreeNode>()
    deque.addFirst(root)

    while (deque.isNotEmpty()) {
        val last = deque.removeFirst()
        mutableListOf.add(0, last.`val`)
        last.left?.let { deque.addFirst(it) }
        last.right?.let { deque.addFirst(it) }
    }
    return mutableListOf
}

//98 验证二叉搜索树
var pre = Long.MIN_VALUE
fun isValidBST(root: TreeNode?): Boolean {
    if (root == null) return true
    val left = isValidBST(root.left)
    if (!left) return false
    if (root.`val`.toLong() <= pre) return false

    pre = root.`val`.toLong()
    return isValidBST(root.right)
}


//100 相同的树
fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
    if (p == null && q == null) {
        return true
    } else if (p != null && q != null) {
        if (p.`val` != q.`val`) return false
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
    } else {
        return false
    }
}

//101对称二叉树
fun isSymmetric(root: TreeNode?): Boolean {
    if (root == null) return true
    return isSymmetricFun(root.left, root.right)
}

fun isSymmetricFun(left: TreeNode?, right: TreeNode?): Boolean {
    if (left == null && right == null) {
        return true
    } else if (left != null && right != null) {
        if (left.`val` != right.`val`) return false
        return isSymmetricFun(left.left, right.right) && isSymmetricFun(left.right, right.left)
    } else {
        return false
    }
}


//102 二叉树的层序遍历 BFS
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val mutableListOf = mutableListOf<List<Int>>()
    if (root == null) return mutableListOf

    val queue = ArrayDeque<TreeNode>()
    queue.addFirst(root)

    while (queue.isNotEmpty()) {
        val size = queue.size
        val child = mutableListOf<Int>()
        for (i in 1..size) {
            val removeLast = queue.removeLast()
            child.add(removeLast.`val`)

            removeLast.left?.let { queue.addFirst(it) }
            removeLast.right?.let { queue.addFirst(it) }
        }
        mutableListOf.add(child)
    }
    return mutableListOf
}

//102 二叉树的层序遍历 DFS
fun levelOrder1(root: TreeNode?): List<List<Int>> {
    var result = mutableListOf<MutableList<Int>>()

    backTrackLevelOrder(0, root, result)

    return result
}

fun backTrackLevelOrder(index: Int, root: TreeNode?, result: MutableList<MutableList<Int>>) {
    if (root == null) return
    if (index >= result.size) result.add(mutableListOf())
    result[index].add(root.`val`)

    root.left?.let { backTrackLevelOrder(index + 1, it, result) }
    root.right?.let { backTrackLevelOrder(index + 1, it, result) }
}


//104 二叉树最大深度
fun maxDepth(root: TreeNode?): Int {
    return if (root == null) {
        0
    } else {
        val left = maxDepth(root.left)
        val right = maxDepth(root.right)
        max(left, right) + 1
    }
}

//107 二叉树的层序遍历II
fun levelOrderBottom(root: TreeNode?): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    if (root == null) return result

    val deque = ArrayDeque<TreeNode>()
    deque.addFirst(root)

    while (deque.isNotEmpty()) {
        val size = deque.size
        val level = mutableListOf<Int>()
        for (i in 1..size) {
            val last = deque.removeLast()
            level.add(last.`val`)

            last.left?.let { deque.addFirst(it) }
            last.right?.let { deque.addFirst(it) }
        }
        result.add(0, level)
    }
    return result
}


//108 将有序数组转为二叉搜索树
fun sortedArrayToBST(nums: IntArray): TreeNode? {
    return sortedArrayToBST(nums, 0, nums.size - 1)
}

fun sortedArrayToBST(nums: IntArray, left: Int, right: Int): TreeNode? {
    if (left > right) return null

    val mid = (left + right) / 2
    val treeNode = TreeNode(nums[mid])
    treeNode.left = sortedArrayToBST(nums, left, mid - 1)
    treeNode.right = sortedArrayToBST(nums, mid + 1, right)

    return treeNode
}

//109 将有序链表转换为二叉搜索树
fun sortedListToBST(head: ListNode?): TreeNode? {
    if (head == null) return null
    if (head.next == null) return TreeNode(head.`val`)

    var q = head
    var p = head
    var pre: ListNode? = null
    while (q?.next != null) {
        pre = p
        p = p!!.next
        q = q.next!!.next
    }
    pre!!.next = null

    val node = TreeNode(p!!.`val`)
    node.left = sortedListToBST(head)
    node.left = sortedListToBST(p.next)

    return node
}

//110 平衡二叉树
fun isBalanced(root: TreeNode?): Boolean {
    return isBalancedFun(root) >= 0
}

fun isBalancedFun(root: TreeNode?): Int {
    if (root == null) return 0

    val left = isBalancedFun(root.left)
    if (left < 0) return left
    val right = isBalancedFun(root.right)
    if (right < 0) return right

    return if (abs(left - right) < 2) max(left, right) + 1 else -1
}


//111 二叉树最小深度
//DFS
fun minDepth(root: TreeNode?): Int {
    if (root == null) return 0

    val left = minDepth(root.left)
    val right = minDepth(root.right)

    return if (root.left == null && root.right == null) {
        1
    } else if (root.left != null && root.right != null) {
        min(left, right) + 1
    } else {
        //left 或者 right 有一个为null，也就是长度0
        left + right + 1
    }
}

fun minDepth1(root: TreeNode?): Int {
    if (root == null) return 0
    val deque = ArrayDeque<TreeNode>()
    deque.addFirst(root)
    var depth = 1

    while (deque.isNotEmpty()) {
        val size = deque.size

        for (i in 1..size) {
            val node = deque.removeLast()
            if (node.left == null && node.right == null) return depth
            node.left?.let { deque.addFirst(it) }
            node.right?.let { deque.addFirst(it) }
        }
        depth++
    }

    return depth
}

//112 路径总和
var curPathSum = 0
fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {

    if (root == null) return false
    if (root.left == null && root.right == null) {
        return curPathSum + root.`val` == targetSum
    }

    curPathSum += root.`val`
    val res1 = hasPathSum(root.left, targetSum)
    val res2 = hasPathSum(root.right, targetSum)

    curPathSum -= root.`val`

    return res1 || res2
}

//113 路径总和2
val result = mutableListOf<List<Int>>()
val curResult = mutableListOf<Int>()
fun pathSum(root: TreeNode?, targetSum: Int): List<List<Int>> {
    if (root == null) return result
    var targetSum = targetSum

    curResult.add(root.`val`)
    targetSum -= root.`val`

    if (root.left == null && root.right == null && targetSum == 0) {
        val mutableListOf = mutableListOf<Int>()
        mutableListOf.addAll(curResult)
        result.add(mutableListOf)
    }

    pathSum(root.left, targetSum)
    pathSum(root.right, targetSum)

    curResult.removeLast()

    return result
}


//114 二叉树展开为链表
fun flatten(root: TreeNode?): Unit {
    if (root == null) return

    flatten(root.left)
    flatten(root.right)

    var left = root.left
    var right = root.right

    root.left = null
    root.right = left

    var temp = root
    while (temp!!.right != null) {
        temp = temp.right
    }
    temp.right = right
}


//116 填充每个节点的下一个右侧节点指针
fun connect(root: Node?): Node? {
    connectFun(root?.left, root?.right)
    return root
}

fun connectFun(left: Node?, right: Node?) {
    if (left == null || right == null) {
        return
    } else {
        left.next = right
        connectFun(left.left, left.right)
        connectFun(right.left, right.right)
        connectFun(left.right, right.left)
    }
}

//124 二叉树中的最大路径和
var maxPathSum = Integer.MIN_VALUE
fun maxPathSum(root: TreeNode?): Int {
    maxPathSumFun(root)
    return maxPathSum
}

fun maxPathSumFun(node: TreeNode?): Int {
    return if (node == null) {
        0
    } else {
        val left = max(maxPathSumFun(node.left), 0)
        val right = max(maxPathSumFun(node.right), 0)

        val curMax = node.`val` + left + right
        maxPathSum = max(maxPathSum, curMax)

        node.`val` + max(left, right)
    }
}


//129 求根节点到叶节点到数字之和
fun sumNumbers(root: TreeNode?): Int {
    return sumNumbersFun(root, 0)
}

fun sumNumbersFun(root: TreeNode?, pre: Int): Int {
    if (root == null) return 0

    var temp = pre * 10 + root.`val`
    if (root.left == null && root.right == null) return temp

    return sumNumbersFun(root.left, temp) + sumNumbersFun(root.right, temp)
}

//199 二叉树的右视图
fun rightSideView(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    if (root == null) return result

    val deque = ArrayDeque<TreeNode>()
    deque.addFirst(root)
    var h = 0

    while (deque.isNotEmpty()) {
        val size = deque.size
        h++

        for (i in 1..size) {
            val last = deque.removeLast()
            last.right?.let { deque.addFirst(it) }
            last.left?.let { deque.addFirst(it) }
            if (result.size < h) result.add(last.`val`)
        }
    }

    return result
}

//222 完全二叉树的节点个数
fun countNodes(root: TreeNode?): Int {
    if (root == null) return 0

    val left = countNodes(root.left)
    val right = countNodes(root.right)

    return left + right + 1
}

//226 翻转二叉树
fun invertTree(root: TreeNode?): TreeNode? {
    if (root == null) return root

    val temp = root.left
    root.left = root.right
    root.right = temp

    invertTree(root.left)
    invertTree(root.right)

    return root
}

//230 二叉搜索树中第K小元素
var kthSmallest = -1
var kthSmallestTemp = 0
fun kthSmallest(root: TreeNode?, k: Int): Int {
    if (root == null) return kthSmallest
    val k = k

    kthSmallest(root.left, k)
    kthSmallestTemp++
    if (k == kthSmallestTemp) kthSmallest = root.`val`
    kthSmallest(root.right, k)

    return kthSmallest
}

//257 二叉树的所有路径
var binaryTreePathsResult = mutableListOf<String>()
var binaryTreePathsLevel = mutableListOf<Int>()
fun binaryTreePaths(root: TreeNode?): List<String> {
    root?.let { binaryTreePathsFun(it) }
    return binaryTreePathsResult
}

fun binaryTreePathsFun(root: TreeNode) {
    binaryTreePathsLevel.add(root.`val`)

    if (root.left == null && root.right == null) {
        binaryTreePathsResult.add(binaryTreePathsLevel.joinToString("->"))
        binaryTreePathsLevel.removeLast()
        return
    }

    root.left?.let { binaryTreePathsFun(it) }
    root.right?.let { binaryTreePathsFun(it) }
    binaryTreePathsLevel.removeLast()
}

//404 左叶子只和
fun sumOfLeftLeaves(root: TreeNode?): Int {
    if (root == null) return 0
    val left = sumOfLeftLeaves(root.left)
    val right = sumOfLeftLeaves(root.right)

    var num = 0
    if (root.left != null
        && root.left!!.left == null
        && root.left!!.right == null
    )
        num = root.left!!.`val`

    return num + left + right
}

//450 删除二叉搜索树中的节点
fun deleteNode(root: TreeNode?, key: Int): TreeNode? {
    if (root == null) return null
    if (root.`val` == key) {
        if (root.left == null) return root.right
        if (root.right == null) return root.left

        val rightMin = findMin(root.right!!)
        root.right = deleteNode(root.right, rightMin.`val`)
        rightMin.left = root.left
        rightMin.right = root.right
        return rightMin

    } else {
        if (root.`val` < key) root.right = deleteNode(root.right, key)
        else root.left = deleteNode(root.left, key)
    }

    return root
}

fun findMin(node: TreeNode): TreeNode {
    if (node.left == null) return node
    return findMin(node.left!!)
}

//501 二叉搜索树中的众数
var curMode = 0
var maxMode = 0
var arrayMode = mutableListOf<Int>()
var preMode = 0
fun findMode(root: TreeNode?): IntArray {
    if (root == null) return intArrayOf()
    preMode = root.`val`
    findModeFun(root)
    return arrayMode.toIntArray()
}

fun findModeFun(root: TreeNode?) {
    if (root == null) return
    findModeFun(root.left)

    if (root.`val` == preMode) {
        curMode += 1

    } else {
        curMode = 1
    }

    if (curMode > maxMode) {
        maxMode = curMode
        arrayMode.clear()
    }
    if (curMode == maxMode) {
        arrayMode.add(root.`val`)
    }

    preMode = root.`val`
    findModeFun(root.right)
}


//543  树的直径
var maxDiameter = 0

fun diameterOfBinaryTree(root: TreeNode?): Int {
    diameterOfBinaryTreeFun(root)
    return maxDiameter
}

fun diameterOfBinaryTreeFun(root: TreeNode?): Int {
    return if (root == null) {
        0
    } else {
        val left = diameterOfBinaryTreeFun(root.left)
        val right = diameterOfBinaryTreeFun(root.right)

        val cur = left + right
        maxDiameter = max(maxDiameter, cur)
        max(left, right) + 1
    }
}

//538 把二叉搜索树转化为累加树
fun convertBST(root: TreeNode?): TreeNode? {
    convertBSTFun(root)
    return root
}

var convertBSTFunTemp = 0
fun convertBSTFun(node: TreeNode?) {
    if (node == null) return

    convertBSTFun(node.right)
    convertBSTFunTemp += node.`val`
    node.`val` = convertBSTFunTemp
    convertBSTFun(node.left)
}

//700 二叉搜索树中的搜索
fun searchBST(root: TreeNode?, `val`: Int): TreeNode? {
    if (root == null) return null
    return if (root.`val` == `val`) {
        root
    } else if (root.`val` < `val`) {
        searchBST(root.right, `val`)
    } else {
        searchBST(root.left, `val`)
    }
}

//701 二叉树的插入操作
fun insertIntoBST(root: TreeNode?, `val`: Int): TreeNode? {
    if (root == null) return TreeNode(`val`)

    if (root.`val` < `val`) {
        root.right = insertIntoBST(root.right, `val`)
    }

    if (root.`val` > `val`) {
        root.left = insertIntoBST(root.left, `val`)
    }

    return root
}

