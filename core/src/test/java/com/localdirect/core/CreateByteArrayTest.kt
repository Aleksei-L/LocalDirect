package com.localdirect.core

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class CreateByteArrayTest {
    @Test
    fun createByteArray1() {
        val res = "10101".createByteArray()
        val trueRes = byteArrayOf(1, 0, 1, 0, 1)

        assertArrayEquals(res, trueRes)
    }

    @Test
    fun createByteArray2() {
        val res = "0".createByteArray()
        val trueRes = byteArrayOf(0)

        assertArrayEquals(res, trueRes)
    }

    @Test
    fun createByteArray3() {
        val res = "".createByteArray()
        val trueRes = byteArrayOf()

        assertArrayEquals(res, trueRes)
    }

    @Test
    fun createByteArray4() {
        val res = "111111".createByteArray()
        val trueRes = byteArrayOf(1, 1, 1, 1, 1, 1)

        assertArrayEquals(res, trueRes)
    }
}