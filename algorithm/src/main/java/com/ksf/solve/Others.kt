package com.ksf.solve

import java.time.LocalDateTime

//292 石子游戏
fun canWinNim(n: Int): Boolean {
    return n % 4 != 0
}

//877 石子游戏
fun stoneGame(piles: IntArray): Boolean {
    return true
}

fun main() {
    var change = "INBOUND"
    val expectFirstResponseTime =
        change.takeIf { it == "INBOUND" }
            ?.let{
                println("sada")
                "ddd"
            }

    print(expectFirstResponseTime)
}