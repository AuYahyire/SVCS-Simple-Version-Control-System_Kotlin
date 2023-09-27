package svcs.commandLogics

import java.io.File

/**
 * A class representing the logic for the "checkout" command in a version control system.
 *
 * This class is responsible for handling the "checkout" command, which allows switching to a specific commit.
 *
 * @property secondArgument The second argument provided for the "checkout" command, which is the commit ID to switch to.
 */
class Checkout(private val secondArgument: String?) : ArgumentsLogic {
    override val fileName = "commits"
    override val file = File(vcsPath, fileName)

    /**
     * Executes the "checkout" argument logic.
     *
     * This method checks if the commits directory exists, and if not, it creates it.
     * It then checks the provided commit ID and switches to the specified commit if it exists.
     * If the commit ID is empty or null, an appropriate message is printed.
     */
    override fun executeArgument() {
        super.executeArgument()
        if (!checkIfCommitsDirExists()){
            commitDirectoryFile.mkdir()
        }
        when (secondArgument) {
            "" -> println("Commit id was not passed.")
            null -> println("Commit id was not passed.")
            else -> switchToCommit()
        }
    }


    /**
     * Switches to the specified commit.
     *
     * This method attempts to switch to the specified commit by copying its contents to the rootPath.
     * If the provided commit ID is valid and the commit exists, it performs the switch and prints a
     * success message. If the commit does not exist or there are errors during the switch, an error
     * message is printed.
     */
    private fun switchToCommit() {
        val directoryId = secondArgument?.let { File(file, it) }
        if (directoryId != null) {
            if (directoryId.exists() && directoryId.isDirectory) {
                directoryId.copyRecursively(rootPath, true)
                println("Switched to commit $secondArgument.")
            } else {
                println("Commit does not exist.")
            }
        }
    }
}