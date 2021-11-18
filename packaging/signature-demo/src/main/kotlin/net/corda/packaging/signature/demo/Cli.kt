package net.corda.packaging.signature.demo

import net.corda.packaging.signature.demo.command.Sign
import net.corda.packaging.signature.demo.command.Verify
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi
import org.fusesource.jansi.AnsiConsole
import org.slf4j.LoggerFactory
import picocli.CommandLine
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "package-cli",
    description = ["Cli tool to deal with Corda package files signatures"]
)
object Cli : StandardCommand() {

    @CommandLine.Option(names = ["-v", "--verbose", "--log-to-console"], description = ["If set, prints logging to the console as well as to a file."])
    var verbose: Boolean = false

    private fun printWarning(message: String) = System.err.println(ansi().fg(Ansi.Color.YELLOW).a(message).reset())
    private fun printError(message: String) = System.err.println(ansi().fg(Ansi.Color.RED).a(message).reset())

    @JvmStatic
    @Suppress("ComplexMethod")
    fun main(args: Array<String>) {

        val cmd = CommandLine(this).apply {
            // Make sure any provided paths are absolute. Relative paths have caused issues and are less clear in logs.
            registerConverter(Path::class.java) { Paths.get(it).toAbsolutePath().normalize() }
            val stack = mutableListOf<StandardCommand>()
            stack.addAll(subCommands)
            while (stack.isNotEmpty()) {
                val last = stack.removeLast()
                val subcommand = CommandLine(last)
                commandSpec.addSubcommand(null, subcommand)
                stack.addAll(last.subCommands)
            }
        }

        AnsiConsole.systemInstall()

        val defaultAnsiMode = if (OS.isWindows) {
            // This line makes sure ANSI escapes work on Windows, where they aren't supported out of the box.
            CommandLine.Help.Ansi.ON
        } else {
            CommandLine.Help.Ansi.AUTO
        }

        val logger = LoggerFactory.getLogger(Cli::class.java)

        cmd.apply {
            out = PrintWriter(System.out)
            err = PrintWriter(System.err)
            executionStrategy = CommandLine.RunLast()
            colorScheme = CommandLine.Help.defaultColorScheme(defaultAnsiMode)

            executionExceptionHandler = CommandLine.IExecutionExceptionHandler { ex, _: CommandLine, _: CommandLine.ParseResult ->
                logger.error(ex.message, ex)
                if (verbose) {
                    throw ex
                } else {
                    printError(ex.message?.takeIf(String::isNotBlank) ?: "Use --verbose for more details")
                    ExitCodes.FAILURE.code
                }
            }

            parameterExceptionHandler = CommandLine.IParameterExceptionHandler { ex, _ ->
                val throwable = ex!!.cause ?: ex
                if (verbose) {
                    throwable.printStackTrace()
                }
                printError(ex.message?.takeIf(String::isNotBlank) ?: "Use --verbose for more details")
                ExitCodes.FAILURE.code
            }
        }
        logger.info("Application Args: ${args.joinToString(" ")}")
        @Suppress("SpreadOperator")
        exitProcess(cmd.execute(*args))
    }

    override fun call(): Int {
        throw IllegalArgumentException("A command must be specified")
    }

    override val subCommands = listOf(Sign, Verify)
}