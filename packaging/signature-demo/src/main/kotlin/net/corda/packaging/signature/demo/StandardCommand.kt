package net.corda.packaging.signature.demo

import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    versionProvider = VersionProvider::class,
    mixinStandardHelpOptions = true,
    sortOptions = false,
    showDefaultValues = true,
    synopsisHeading = "%n@|bold,underline Usage|@:%n%n",
    descriptionHeading = "%n@|bold,underline Description|@:%n%n",
    parameterListHeading = "%n@|bold,underline Parameters|@:%n%n",
    optionListHeading = "%n@|bold,underline Options|@:%n%n",
    commandListHeading = "%n@|bold,underline Commands|@:%n%n")
abstract class StandardCommand : Callable<Int> {
    open val subCommands: List<StandardCommand> = emptyList()
}