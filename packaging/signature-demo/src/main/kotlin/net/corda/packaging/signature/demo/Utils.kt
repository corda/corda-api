package net.corda.packaging.signature.demo

inline fun <reified T> takeNotNull(errorMessage: String, vararg args : T?) : T {
    var result :T? = null
    for(arg in args) {
        arg?.let {
            result = when(result) {
                null -> it
                else -> throw IllegalArgumentException(errorMessage)
            }
        }
    }
    return result ?: throw IllegalArgumentException(errorMessage)
}