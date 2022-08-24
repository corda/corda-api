package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.PublicKey
import java.util.jar.JarInputStream

/**
 * Defines a data attachment that can be referenced within a transaction.
 *
 * Attachments are ZIP, or optionally a signed JAR file that contains one or more files of public, static data that
 * can be referenced from a transaction, and utilised within a contract. Examples of attachments include:
 *
 * - Calendar data
 * - Fixes (e.g. LIBOR)
 * - Smart contract code
 * - Legal documents
 * - Facts generated by oracles.
 *
 * @property id The unique hash of the attachment.
 * @property size The size of the attachment in bytes.
 * @property signatories The keys that have been used to sign the attachment, or empty if the attachment does not require signing.
 */
@CordaSerializable
@DoNotImplement
interface Attachment {

    val id: SecureHash
    val size: Int
    val signatories: Set<PublicKey>

    /**
     * Finds the specified file (case insensitively) within the attachment and copies it to the specified output stream.
     *
     * @param path The path of the file to find within the attachment.
     * @param output The output stream where the file should be copied to.
     * @throws [FileNotFoundException] if the specified path does not exist in the attachment.
     */
    fun extractFile(path: String, output: OutputStream)

    /**
     * TODO : Document
     */
    fun open(): InputStream

    /**
     * TODO : Document
     */
    fun openAsJar(): JarInputStream {
        val stream = open()
        return try {
            JarInputStream(stream)
        } catch (ex: IOException) {
            stream.use { throw ex }
        }
    }
}
