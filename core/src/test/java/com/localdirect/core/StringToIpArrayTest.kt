package com.localdirect.core

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class StringToIpArrayTest {
    @Test
    fun stringToIpAddress1() {
        val res = "192.168.0.1".toIpArray()
        val trueRes = byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }

    @Test
    fun stringToIpAddress2() {
        val res = "192.168.100.16".toIpArray()
        val trueRes = byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }

    @Test
    fun stringToIpAddress3() {
        val res = "127.0.0.1".toIpArray()
        val trueRes = byteArrayOf(0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }

    @Test
    fun stringToIpAddress4() {
        val res = "255.255.255.255".toIpArray()
        val trueRes = byteArrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }

    @Test
    fun stringToIpAddress5() {
        val res = "255.255.255.0".toIpArray()
        val trueRes = byteArrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }

    @Test
    fun stringToIpAddress6() {
        val res = "0.0.0.0".toIpArray()
        val trueRes = byteArrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }
    @Test
    fun stringToIpAddress7() {
        val res = "10.55.213.2".toIpArray()
        val trueRes = byteArrayOf(0,0,0,0,1,0,1,0,0,0,1,1,0,1,1,1,1,1,0,1,0,1,0,1,0,0,0,0,0,0,1,0)

        debugPrint(res)
        assertArrayEquals(res, trueRes)
    }
}