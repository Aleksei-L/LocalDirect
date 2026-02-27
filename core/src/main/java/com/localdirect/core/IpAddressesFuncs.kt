package com.localdirect.core

import timber.log.Timber
import kotlin.math.pow

fun String.makeNBitLength(n: Int): String {
    var newString = this
    while (newString.length < n)
        newString = "0$newString"
    return newString
}

fun String.toIpArray(): ByteArray {
    val ipOctets = this.split('.')
    val array = ByteArray(32)
    var index = 0

    for (octet in ipOctets) {
        val binaryOctet = Integer
            .toBinaryString(octet.toInt())
            .makeNBitLength(8)
        for (byte in binaryOctet)
            array[index++] = byte.digitToInt().toByte()
    }

    return array
}

fun ByteArray.toIpString(): String {
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

fun String.createByteArray(): ByteArray {
    val size = this.length
    val array = ByteArray(size)
    var index = 0

    for (char in this)
        array[index++] = char.digitToInt().toByte()

    return array
}

fun getLocalAddresses(lanIp: ByteArray, mask: ByteArray): List<ByteArray> {
    val index = mask.indexOfFirst { it == 0.toByte() }
    if (index == -1 || index == 31) {
        Timber.e("Incorrect subnet mask")
        return emptyList()
    }

    val list = mutableListOf<ByteArray>()
    val globalNetworkPart = lanIp.sliceArray(0..<index)
    val localNetworkPart = lanIp.sliceArray(index..31)
    val localNetworkPartSize = localNetworkPart.size

    for (i in 0..<(2.0.pow(localNetworkPartSize)).toInt()) {
        val array = Integer
            .toBinaryString(i)
            .makeNBitLength(localNetworkPartSize)
            .createByteArray()

        list.add(globalNetworkPart + array)
    }

    return list
}