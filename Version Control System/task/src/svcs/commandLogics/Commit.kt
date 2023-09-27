package svcs.commandLogics

import svcs.data.ArgumentsStrings.MESSAGE_WAS_NOT_PASSED
import svcs.data.ArgumentsStrings.NOTHING_TO_COMMIT
import svcs.data.ArgumentsStrings.CHANGES_ARE_COMMITTED
import java.io.File

/**
 * Represents a class responsible for handling the 'commit' command in a version control system.
 * This command is used to create commits with associated messages for changes made to tracked files.
 *
 * The 'Commit' class extends 'ArgumentsLogic' and overrides its properties and methods to implement
 * the specific behavior for the 'commit' command.
 *
 * @property secondArgument The second argument passed to the 'commit' command, typically the commit message.
 * @property restOfArguments The remaining arguments passed to the 'commit' command.
 * @property fileName The name of the index file, which is "index.txt" in this case.
 * @property file The 'File' object representing the index file.
 * @property commitDirectoryName The name of the directory where commits are stored.
 * @property commitDirectoryFile The 'File' object representing the commit directory.
 * @property writeCommitInLog A reference to the 'writeCommitInLog' function from the 'Log' class for writing commit information.
 */
class Commit(private val secondArgument: String?, private val restOfArguments: String?) : ArgumentsLogic {
    override val fileName = "index.txt"
    override val file = File("${vcsPath}${separator}${fileName}")
    private val writeCommitInLog = Log().writeCommitInLog

    /**
     * Executes the 'commit' command logic. It checks if the commits directory exists and creates it if it doesn't.
     * Then, it manages the 'commit' argument based on the provided arguments.
     */
    override fun executeArgument() {
        super.executeArgument()
        if(!checkIfCommitsDirExists()){
            commitDirectoryFile.mkdir()
        }
        manageCommitArgument()
    }

    /**
     * Manages the 'commit' argument based on the provided arguments. It handles cases where the second argument
     * is empty or null.
     */
    private fun manageCommitArgument() {
        when (secondArgument) {
            "" -> println(MESSAGE_WAS_NOT_PASSED.value)
            null -> println(MESSAGE_WAS_NOT_PASSED.value)
            else -> performCommit()

        }
    }

    /**
     * Performs the 'commit' operation by calculating a hash value for the changes, comparing it with
     * existing commits, and creating a new commit directory if necessary.
     */
    private fun performCommit() {
        val filesTrackedInIndexList = file.readLines().toMutableList()
        filesTrackedInIndexList.removeAt(0) //Delete the "Tracked files:" string from list.

        val dataFromAllFiles = readFiles(filesTrackedInIndexList)

        val hashValueOfWholeFiles = dataFromAllFiles.hashAlgorithm()

        val checkIfHashIsTheSameOfOthersCommits = compareHashDirs(hashValueOfWholeFiles)

        if(!checkIfHashIsTheSameOfOthersCommits) {
            commitChanges(filesTrackedInIndexList, hashValueOfWholeFiles)
            println(CHANGES_ARE_COMMITTED.value)
        } else {
            println(NOTHING_TO_COMMIT.value)
        }



    }

    /**
     * Reads the contents of files tracked in the index and returns the data.
     *
     * @param filesTrackedInIndexList A list of file paths tracked in the index.
     * @return A mutable list containing the data from the tracked files.
     */
    private fun readFiles(filesTrackedInIndexList: MutableList<String>): MutableList<String> {
        val data = mutableListOf("")

        for (fileInIndexList in filesTrackedInIndexList) {
            val fileToRead = File(fileInIndexList)
            val dataInsideFile = fileToRead.readText()
            data.add(dataInsideFile)
        }

        return data
    }

    /**
     * Compares the hash value of the changes with existing commit directories to check if the same changes
     * have been committed before.
     *
     * @param hashValueOfWholeFiles The hash value of the changes to be committed.
     * @return 'true' if the same changes have been committed before, 'false' otherwise.
     */
    private fun compareHashDirs(hashValueOfWholeFiles: Long): Boolean {
        val directoryWithSameName = File(commitDirectoryFile, hashValueOfWholeFiles.toString())

        return directoryWithSameName.exists() && directoryWithSameName.isDirectory
    }

    /**
     * Commits the changes by creating a new commit directory, copying tracked files into it,
     * and writing commit information to the log.
     *
     * @param filesTrackedInIndexList A list of file paths tracked in the index.
     * @param hashValueOfWholeFiles The hash value of the changes being committed.
     */
    private fun commitChanges(filesTrackedInIndexList: MutableList<String>, hashValueOfWholeFiles: Long) {
        val newDirectory = File(commitDirectoryFile, hashValueOfWholeFiles.toString())
        newDirectory.mkdir()

        filesTrackedInIndexList.forEach {
            val fileIn = File(it)
            val fileOut = File(newDirectory, it)
            fileIn.copyTo(fileOut)
        }

        if (secondArgument != null) {
            val message = "$secondArgument $restOfArguments"
            writeCommitInLog(message, hashValueOfWholeFiles.toString())
            } else {
                println("Second argument is empty and commitChanges has been executed, this should never happen.")
        }

    }

    /**
     * Custom hash algorithm for computing a hash value for a list of strings.
     *
     * @return The computed hash value as a long integer.
     */
    private fun MutableList<String>.hashAlgorithm(): Long {
        var result: Long = 0

        for (data in this) {
            result = 31 * result + data.hashCode()
        }

        return result
    }
}