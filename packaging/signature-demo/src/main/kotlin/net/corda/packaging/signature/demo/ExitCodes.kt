package net.corda.packaging.signature.demo

enum class ExitCodes(val code : Int) {
    SUCCESS(0), FAILURE(1);

    fun unsignedChar() : Int {
        var result = code
        while(result < 0) result += 0x100
        return result % 0x100
    }
}