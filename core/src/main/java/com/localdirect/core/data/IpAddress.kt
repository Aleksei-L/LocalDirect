package com.localdirect.core.data

import timber.log.Timber
import kotlin.math.pow

@OptIn(ExperimentalUnsignedTypes::class)
class IpAddress(
    /**
     * String with IP address and subnet mask
     */
    val stringIpAddressWithMask: String
) {
    /**
     * String with only IP address without subnet mask
     */
    val stringIpAddress = stringIpAddressWithMask.substringBefore('/')

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
        var ipOctets = stringIpAddressWithMask.split('.', '/')
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

    fun getLocalAddresses(): List<IpAddress> {
        if (mask == 0.toUByte() || mask == 31.toUByte()) {
            Timber.e("Incorrect subnet mask")
            return emptyList()
        }

        val list = mutableListOf<IpAddress>()
        val globalNetworkPart = ipAddress.sliceArray(0..<mask.toByte())
        val localNetworkPart = ipAddress.sliceArray(mask.toByte()..31)
        val localNetworkPartSize = localNetworkPart.size

        for (i in 0..<(2.0.pow(localNetworkPartSize)).toInt()) {
            val array = Integer
                .toBinaryString(i)
                .makeNBitLength(localNetworkPartSize)
                .createUByteArray()

            val resultArray = globalNetworkPart + array
            list.add(IpAddress("${resultArray.toIpString()}/$mask"))
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

    private fun UByteArray.toIpString(): String {
        var tmpString = ""
        var res = ""

        for (i in 0..7)
            tmpString += this[i]
        res += "${tmpString.toInt(2)}."
        tmpString = ""

        for (i in 8..15)
            tmpString += this[i]
        res += "${tmpString.toInt(2)}."
        tmpString = ""

        for (i in 16..23)
            tmpString += this[i]
        res += "${tmpString.toInt(2)}."
        tmpString = ""

        for (i in 24..31)
            tmpString += this[i]

        return "$res${tmpString.toInt(2)}"
    }

    private fun String.makeNBitLength(n: Int): String {
        var newString = this
        while (newString.length < n)
            newString = "0$newString"
        return newString
    }

    override fun toString(): String = stringIpAddressWithMask

    companion object {
        val Init = IpAddress("0.0.0.0/0")
    }
}