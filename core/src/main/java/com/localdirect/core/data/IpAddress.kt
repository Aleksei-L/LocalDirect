package com.localdirect.core.data

import timber.log.Timber
import kotlin.math.pow

@OptIn(ExperimentalUnsignedTypes::class)
class IpAddress(
    val stringIpAddress: String
) {
    /**
     * Ip address in octets form
     *
     * For 192.168.0.100 -> 192, 168, 0, 100
     */
    val octets = UByteArray(4)

    /**
     * Ip address in binary form
     *
     * For 192.168.0.100 -> 1,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0
     */
    val ipAddress = UByteArray(32)

    /**
     * Subnet mask
     */
    var mask = 0.toUByte()
        private set

    init {
        var ipOctets = stringIpAddress.split('.', '/')
        mask = ipOctets.last().toUByte()
        ipOctets = ipOctets.dropLast(1)

        var ipIndex = 0
        var octetIndex = 0

        for (octet in ipOctets) {
            octets[octetIndex++] = octet.toUByte()
            val binaryOctet = Integer
                .toBinaryString(octet.toInt())
                .makeNBitLength(8)
            for (byte in binaryOctet)
                ipAddress[ipIndex++] = byte.digitToInt().toUByte()
        }
    }

    fun getLocalAddresses(): List<UByteArray> {
        if (mask == 0.toUByte() || mask == 31.toUByte()) {
            Timber.e("Incorrect subnet mask")
            return emptyList()
        }

        val list = mutableListOf<UByteArray>()
        val globalNetworkPart = ipAddress.sliceArray(0..<mask.toByte())
        val localNetworkPart = ipAddress.sliceArray(mask.toByte()..31)
        val localNetworkPartSize = localNetworkPart.size

        for (i in 0..<(2.0.pow(localNetworkPartSize)).toInt()) {
            val array = Integer
                .toBinaryString(i)
                .makeNBitLength(localNetworkPartSize)
                .createUByteArray()

            list.add(globalNetworkPart + array)
        }

        return list
    }

    private fun String.createUByteArray(): UByteArray {
        val size = this.length
        val array = UByteArray(size)
        var index = 0

        for (char in this)
            array[index++] = char.digitToInt().toUByte()

        return array
    }

    private fun String.makeNBitLength(n: Int): String {
        var newString = this
        while (newString.length < n)
            newString = "0$newString"
        return newString
    }
}