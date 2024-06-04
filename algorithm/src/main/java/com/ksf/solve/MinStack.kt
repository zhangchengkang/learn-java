package com.ksf.solve

import kotlinx.coroutines.NonCancellable.children
import java.util.*

// 用一个变量维护最小值，当插入值比他小时，将最小值冗余入栈
class MinStack() {
    private var minValue: Int = Int.MAX_VALUE
    private val stack: Stack<Int> = Stack()

    fun push(`val`: Int) {
        if (`val` <= minValue) {
            stack.push(minValue)
            minValue = `val`
        }

        stack.push(`val`)
    }

    fun pop() {
        val pop = stack.pop()
        if (pop == minValue) {
            val min = stack.pop()
            minValue = min
        }
    }

    fun top(): Int {
        return stack.peek()
    }

    fun getMin(): Int {
        return minValue
    }
}

class Trie() {

    class TrieNode() {
        var isValid: Boolean = false
        val children: Array<TrieNode?> = arrayOfNulls(26)
    }

    private val root = TrieNode()


    fun insert(word: String) {
        var node = root

        for (char in word) {
            val code = char - 'a'
            if (node.children[code] == null) {
                node.children[code] = TrieNode()
            }

            node = node.children[code]!!
        }

        node.isValid = true
    }

    fun search(word: String): Boolean {
        var node = root
        for (char in word) {
            val code = char - 'a'
            if (node.children[code] == null) return false
            node = node.children[code]!!
        }

        return node.isValid
    }

    fun startsWith(prefix: String): Boolean {
        var node = root

        for (char in prefix) {
            val code = char - 'a'
            if (node.children[code] == null) return false
            node = node.children[code]!!
        }

        return true
    }

}


