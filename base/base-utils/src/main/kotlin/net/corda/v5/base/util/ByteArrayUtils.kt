@file:JvmName("ByteArrayUtils")
package net.corda.v5.base.util

import net.corda.v5.base.types.OpaqueBytesSubSequence

/**
 * Wrap [size] bytes from this [ByteArray] starting from [offset] into a new [ByteArray].
 */
fun ByteArray.sequence(offset: Int = 0, size: Int = this.size) = OpaqueBytesSubSequence(this, offset, size)

/**
 * Converts this [ByteArray] into a [String] of hexadecimal digits.
 */
fun ByteArray.toHexString(): String = net.corda.v5.base.types.ByteArrays.toHexString(this)

/**
 * Converts this [String] of hexadecimal digits into a [ByteArray].
 * @throws IllegalArgumentException if the [String] contains incorrectly-encoded characters.
 */
fun String.parseAsHex(): ByteArray = net.corda.v5.base.types.ByteArrays.parseAsHex(this)
