package net.corda.packaging.signature.demo

import picocli.CommandLine
import java.util.jar.JarFile
import java.util.jar.Manifest

class VersionProvider : CommandLine.IVersionProvider {
    override fun getVersion(): Array<String> = javaClass.classLoader.getResources(JarFile.MANIFEST_NAME)
        .asIterator()
        .asSequence()
        .map {
            it.openStream().use(::Manifest).mainAttributes
        }.find {
            it.getValue("Implementation-Title") == "cordapp-builder"
        }?.let { mainAttributes ->
            arrayOf(
                "cordapp-builder",
                "version: ${mainAttributes.getValue("Implementation-Version")}",
                "revision: ${mainAttributes.getValue("Revision")}",
                "build-date: ${mainAttributes.getValue("Implementation-Build-Date")}",
                "license: ${mainAttributes.getValue("Corda-License")}"
            )
        } ?: arrayOf("No version information available")
}