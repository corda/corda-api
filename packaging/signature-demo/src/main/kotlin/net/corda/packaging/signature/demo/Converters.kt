package net.corda.packaging.signature.demo

import picocli.CommandLine
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class PathConverter : CommandLine.ITypeConverter<Path> {
    override fun convert(value: String): Path {
        return Paths.get(value)
    }
}

class DestinationConverter : CommandLine.ITypeConverter<Destination> {
    override fun convert(value: String): Destination {
        return Destination.File(Paths.get(value))
    }
}

class SourceConverter : CommandLine.ITypeConverter<Source> {
    override fun convert(value: String): Source {
        return Source.File(Paths.get(value))
    }
}

class FileContentConverter : CommandLine.ITypeConverter<String> {
    override fun convert(value: String) = String(Files.readAllBytes(Paths.get(value)))
}

class EnvVarConverter : CommandLine.ITypeConverter<String> {
    override fun convert(value: String) = System.getenv(value)
}
