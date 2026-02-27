package com.localdirect.core

fun debugPrint(array: ByteArray) {
    println("===== DEBUG PRINT =====")
    for (i in array)
        print(i)
    println()
    println("=======================")
}

fun debugPrint(str: String) {
    println("===== DEBUG PRINT =====")
    println(str)
    println("=======================")
}