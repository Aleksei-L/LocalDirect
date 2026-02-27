package com.localdirect.core

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class GetLocalAddressesTest {
    @Test
    fun getLocalAddresses1() {
        val addresses = getLocalAddresses(
            "192.168.0.100".toIpArray(),
            "255.255.255.0".toIpArray()
        )

        assertEquals(256, addresses.size)
    }

    @Test
    fun getLocalAddresses2() {
        val addresses = getLocalAddresses(
            "192.168.0.100".toIpArray(),
            "255.255.255.253".toIpArray()
        ).toTypedArray()
        val trueRes = listOf(
            byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0),
            byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,1),
            byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0),
            byteArrayOf(1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,1)
        ).toTypedArray()

        assertEquals(4, addresses.size)
        assertArrayEquals(trueRes, addresses)
    }
}