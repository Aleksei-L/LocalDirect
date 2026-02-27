package com.localdirect.core

import org.junit.Assert.assertEquals
import org.junit.Test

class MakeNBitLengthTest {
    @Test
    fun makeNBitLength1() {
        val res = "10101".makeNBitLength(10)
        val trueRes = "0000010101"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun makeNBitLength2() {
        val res = "".makeNBitLength(5)
        val trueRes = "00000"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun makeNBitLength3() {
        val res = "1".makeNBitLength(6)
        val trueRes = "000001"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun makeNBitLength4() {
        val res = "1".makeNBitLength(6)
        val trueRes = "000001"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun makeNBitLength5() {
        val res = "1".makeNBitLength(1)
        val trueRes = "1"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun makeNBitLength6() {
        val res = "1010".makeNBitLength(3)
        val trueRes = "1010"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun makeNBitLength7() {
        val res = "10101".makeNBitLength(5)
        val trueRes = "10101"

        debugPrint(res)
        assertEquals(res, trueRes)
    }
}