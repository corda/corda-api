package net.corda.v5.application.marshalling

/**
 * When objects are serialized to Json using custom serializers and they require to be encoded into base64 encoded, this
 * enum can be used to configure how that base64 is written into the Json.
 */
enum class JsonSerializedBase64Config {
    MIME,
    MIME_NO_LINEFEEDS,
    MODIFIED_FOR_URL,
    PEM
}
