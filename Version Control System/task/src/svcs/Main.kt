package svcs

import svcs.commandLogics.*
import svcs.data.Arguments

fun main(args: Array<String>) {
    // Join the command-line arguments into a single string
    val splitArgs = args.joinToString(" ").split(" ")
    val command = splitArgs[0]
    val secondArgument = if (splitArgs.size > 1) splitArgs[1] else null
    val restOfArguments = if (splitArgs.size > 2) {
        splitArgs.subList(2, splitArgs.size).joinToString(" ")
    } else {
        null
    }

    // Convert the command to uppercase for case-insensitive comparison
    when (command.uppercase()) {
        // Check if the command matches known SVCS command names
        Arguments.CONFIG.name -> Config(secondArgument).executeArgument()
        Arguments.ADD.name -> Add(secondArgument).executeArgument()
        Arguments.LOG.name -> Log().executeArgument()
        Arguments.COMMIT.name -> Commit(secondArgument, restOfArguments).executeArgument()
        Arguments.CHECKOUT.name -> Checkout(secondArgument).executeArgument()

        // Check for the help command with a double hyphen prefix
        "--${Arguments.HELP.name}" -> println(Arguments.HELP.value)

        //Prints help if no argument is passed.
        "" -> println(Arguments.HELP.value)

        // If the command doesn't match any known commands, print an error message
        else -> println("'$command' is not a SVCS command.")
    }
}
