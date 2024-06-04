package com.ksf.solve

//125 验证回文串
fun isPalindrome(s: String): Boolean {
    var left = 0
    var right = s.length - 1
    while (left < right) {
        while (left < right && !s[left].isLetterOrDigit()) {
            left++
        }
        while (left < right && !s[right].isLetterOrDigit()) {
            right--
        }

        if (s[left].uppercaseChar() != s[right].uppercaseChar()) {
            return false
        }
        left++
        right--
    }
    return true
}