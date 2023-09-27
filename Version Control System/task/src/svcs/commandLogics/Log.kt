package svcs.commandLogics

import svcs.data.ArgumentsStrings.NO_COMMITS_YET
import java.io.File

/**
 * Represents a class responsible for handling the 'log' command in a version control system.
 * This command is used to display a log of commits made in the repository.
 *
 * The 'Log' class extends 'ArgumentsLogic' and overrides its properties and methods to implement
 * the specific behavior for the 'log' command.
 *
 * @property fileName The name of the log file, which is "log.txt" in this case.
 * @property file The 'File' object representing the log file.
 * @property writeCommitInLog A lambda function for writing commit information to the log.
 */
class Log() : ArgumentsLogic {
    override val fileName = "log.txt"
    override val file = File("${vcsPath}${separator}${fileName}")

    val writeCommitInLog: (String, String) -> Unit = this::writeInLog

    /**
     * Executes the 'log' command logic. It checks if the log file exists and creates it if it doesn't.
     * Then, it reads and prints the contents of the log file.
     */
    override fun executeArgument() {
        super.executeArgument()
        if(!checkIfFileExists()) {
            file.createNewFile()
        }
        readFile()
    }

    /**
     * Reads and prints the contents of the log file. If the file is empty, it prints a message indicating
     * that there are no commits yet.
     */
    private fun readFile() {
        val fileData = file.readLines()

        if(fileData.isNotEmpty()) {
        fileData.forEach {
            println(it)
        } }
        else {
            println(NO_COMMITS_YET.value)
        }
    }

    /**
     * Writes commit information to the log file, including commit hash, author, and commit message.
     * If the log file already exists, it appends the new commit information to the existing data.
     *
     * @param commitMessage The message associated with the commit.
     * @param commitHash The unique identifier for the commit.
     */
    private fun writeInLog(commitMessage: String, commitHash: String) {
        val authorLine = File(vcsPath, "config.txt")
            .readLines()
            .toMutableList()
            .filter { it.contains("Name:") }
        val author = authorLine[0].substringAfter("Name:").trim()

        if (file.exists()) {
            val oldFileData = file.readText()
            file.writeText("commit $commitHash\nAuthor: $author\n$commitMessage\n")
            file.appendText(oldFileData)
        } else {
            file.writeText("commit $commitHash\nAuthor: $author\n$commitMessage\n")
        }


    }

}