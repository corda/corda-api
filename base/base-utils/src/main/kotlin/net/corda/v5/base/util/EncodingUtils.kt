@file:JvmName("EncodingUtils")
package net.corda.v5.base.util

import net.corda.v5.base.types.ByteArrays.parseAsHex
import net.corda.v5.base.types.ByteArrays.toHexString
import java.nio.charset.Charset
import java.util.Base64

// This file includes useful encoding methods and extension functions for the most common encoding/decoding operations.

// [ByteArray] encoders

/** Convert a byte array to a Base58 encoded [String]. */
fun ByteArray.toBase58(): String = Base58.encode(this)

/** Convert a byte array to a Base64 encoded [String]. */
fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)

/** Convert a byte array to a hex (Base16) capitalized encoded [String]. */
fun ByteArray.toHex(): String = toHexString(this)

// [String] encoders and decoders

/** Base58-String to the actual real [String], i.e. "JxF12TrwUP45BMd" -> "Hello World". */
fun String.base58ToRealString() = String(base58ToByteArray(), Charset.defaultCharset())

/** Base64-String to the actual real [String], i.e. "SGVsbG8gV29ybGQ=" -> "Hello World". */
fun String.base64ToRealString() = String(base64ToByteArray())

/** HEX-String to the actual real [String], i.e. "48656C6C6F20576F726C64" -> "Hello World". */
fun String.hexToRealString() = String(hexToByteArray())

fun String.base58ToByteArray(): ByteArray = Base58.decode(this)

fun String.base64ToByteArray(): ByteArray = Base64.getDecoder().decode(this)

/** Hex-String to [ByteArray]. Accept any hex form (capitalized, lowercase, mixed). */
fun String.hexToByteArray(): ByteArray = parseAsHex(this)

// Encoding changers

/** Encoding changer. Base58-[String] to Base64-[String], i.e. "SGVsbG8gV29ybGQ=" -> JxF12TrwUP45BMd" */
fun String.base58toBase64(): String = base58ToByteArray().toBase64()

/** Encoding changer. Base58-[String] to Hex-[String], i.e. "SGVsbG8gV29ybGQ=" -> "48656C6C6F20576F726C64" */
fun String.base58toHex(): String = base58ToByteArray().toHex()

/** Encoding changer. Base64-[String] to Base58-[String], i.e. "SGVsbG8gV29ybGQ=" -> JxF12TrwUP45BMd" */
fun String.base64toBase58(): String = base64ToByteArray().toBase58()

/** Encoding changer. Base64-[String] to Hex-[String], i.e. "SGVsbG8gV29ybGQ=" -> "48656C6C6F20576F726C64" */
fun String.base64toHex(): String = base64ToByteArray().toHex()

/** Encoding changer. Hex-[String] to Base58-[String], i.e. "48656C6C6F20576F726C64" -> "JxF12TrwUP45BMd" */
fun String.hexToBase58(): String = hexToByteArray().toBase58()

/** Encoding changer. Hex-[String] to Base64-[String], i.e. "48656C6C6F20576F726C64" -> "SGVsbG8gV29ybGQ=" */
fun String.hexToBase64(): String = hexToByteArray().toBase64()


