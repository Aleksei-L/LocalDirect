package com.localdirect.core

import org.junit.Assert.assertEquals
import org.junit.Test

class ByteArrayToIpStringTest {
    @Test
    fun byteArrayToIpString1() {
        val array = byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)
        val res = array.toIpString()
        val trueRes = "192.168.0.1"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun byteArrayToIpString2() {
        val array = byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0)
        val res = array.toIpString()
        val trueRes = "192.168.100.16"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun byteArrayToIpString3() {
        val array = byteArrayOf(0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)
        val res = array.toIpString()
        val trueRes = "127.0.0.1"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun byteArrayToIpString4() {
        val array = byteArrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)
        val res = array.toIpString()
        val trueRes = "255.255.255.255"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun byteArrayToIpString5() {
        val array = byteArrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0)
        val res = array.toIpString()
        val trueRes = "255.255.255.0"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun byteArrayToIpString6() {
        val array = byteArrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        val res = array.toIpString()
        val trueRes = "0.0.0.0"

        debugPrint(res)
        assertEquals(res, trueRes)
    }

    @Test
    fun byteArrayToIpString7() {
        val array = byteArrayOf(0,0,0,0,1,0,1,0,0,0,1,1,0,1,1,1,1,1,0,1,0,1,0,1,0,0,0,0,0,0,1,0)
        val res = array.toIpString()
        val trueRes = "10.55.213.2"

        debugPrint(res)
        assertEquals(res, trueRes)
    }
}