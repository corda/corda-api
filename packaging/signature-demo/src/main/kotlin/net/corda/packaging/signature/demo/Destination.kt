package net.corda.packaging.signature.demo

import java.io.OutputStream
import java.nio.file.Path

sealed class Destination {
    class Stream(val outputStream: OutputStream, private val description : String? = null) : Destination() {
        override fun toString() = description ?: outputStream.toString()
    }
    class File(val outputFile: Path) : Destination() {
        override fun toString() = outputFile.toString()
    }
}