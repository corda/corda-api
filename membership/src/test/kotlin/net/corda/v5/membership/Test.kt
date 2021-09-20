package net.corda.v5.membership

import net.corda.data.identity.WireMemberInfo
import net.corda.v5.membership.MemberInfoExtensionPoC.Companion.endpoints
import org.apache.avro.file.DataFileReader
import org.apache.avro.file.DataFileWriter
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DatumWriter
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificDatumWriter
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Test {

    companion object {
        private val keyEncodingService = DummyKeyEncodingService()
        private val memberInfo = createExampleMemberInfo(
            keyEncodingService = keyEncodingService
        )
        private val memberProvidedContext = memberInfo.memberProvidedContext.convertToListOfWireKeyValuePair()
        private val mgmProvidedContext = memberInfo.mgmProvidedContext.convertToListOfWireKeyValuePair()
        private val wireMemberInfo = WireMemberInfo(memberProvidedContext, mgmProvidedContext)
    }

    @Test
    fun `serializing and deserializing MembershipPackage using avro`() {
        var user: WireMemberInfo? = null
        val userDatumWriter: DatumWriter<WireMemberInfo> = SpecificDatumWriter(
            WireMemberInfo::class.java
        )
        val dataFileWriter: DataFileWriter<WireMemberInfo> = DataFileWriter(userDatumWriter)

        dataFileWriter.create(wireMemberInfo.schema, File("avro-member-info.avro"))
        dataFileWriter.append(wireMemberInfo)
        dataFileWriter.close()

        val userDatumReader: DatumReader<WireMemberInfo> = SpecificDatumReader(
            WireMemberInfo::class.java
        )
        val dataFileReader: DataFileReader<WireMemberInfo> =
            DataFileReader(File("avro-member-info.avro"), userDatumReader)

        var recreatedMemberInfo: MemberInfoPoC? = null
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user)
            recreatedMemberInfo = convertToMemberInfoPoC(
                user.memberContext.convertToContext(),
                user.mgmContext.convertToContext(),
                keyEncodingService
            )
        }

        assertEquals(memberInfo, recreatedMemberInfo)
        assertEquals(memberInfo.identityKeys, recreatedMemberInfo?.identityKeys)
        assertEquals(memberInfo.party, recreatedMemberInfo?.party)
        assertEquals(memberInfo.endpoints, recreatedMemberInfo?.endpoints)
    }
}