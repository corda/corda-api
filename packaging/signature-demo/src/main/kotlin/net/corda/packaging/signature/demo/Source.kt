package net.corda.packaging.signature.demo

import java.io.InputStream
import java.nio.file.Path

sealed class Source {
    class Stream(val inputStream: InputStream, private val description : String? = null) : Source() {
        override fun toString() = description ?: inputStream.toString()
    }
    class File(val inputFile: Path) : Source() {
        override fun toString() = inputFile.toString()
    }
}