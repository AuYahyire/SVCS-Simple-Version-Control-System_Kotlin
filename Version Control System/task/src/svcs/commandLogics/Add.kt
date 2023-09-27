package svcs.commandLogics

import svcs.data.ArgumentsStrings
import java.io.File

/**
 * Represents the logic for the 'add' command in a version control system.
 *
 * @param secondArgument The second argument provided to the 'add' command.
 */
class Add(private val secondArgument: String?) : ArgumentsLogic {
    override val fileName = "index.txt"
    override val file = File("${vcsPath}${separator}${fileName}")

    /**
     * Executes the 'add' argument logic, adding files to the version control system.
     */
    override fun executeArgument() {
        super.executeArgument()
        if (!checkIfFileExists()) {
            file.createNewFile()
        }
        manageIndexFileContent()
    }

    /**
     * Manages the content of the index file, adding or listing tracked files.
     */
    private fun manageIndexFileContent() {
        // Read the existing content of the index file into a mutable list of lines
        val fileData = file.readLines().toMutableList()
        val fileToBeTracked = File("${rootPath}${separator}${secondArgument}")

        // Check if "Tracked files:" is found in the index file
        val trackedFileTitleFound = fileData.contains("Tracked files:")

        // Case 1: Second argument is null or empty
        if (secondArgument.isNullOrEmpty()) {
            if (!trackedFileTitleFound) {
                print(ArgumentsStrings.INDEX_NO_FILE_OR_NAME.value)
            } else {
                // Print the data of the file
                fileData.forEach { println(it) }
            }
            return
        }

        // Case 2: "Tracked files:" exists in the index file
        if (trackedFileTitleFound) {
            // Add a new line with the argument provided if it is a valid file
            if (fileToBeTracked.exists() && fileToBeTracked.isFile) {
                fileData.add(secondArgument)
                file.writeText(fileData.joinToString("\n"))
                print(ArgumentsStrings.INDEX_OUTPUT_FILE_TRACKED.value.format(secondArgument))
            } else {
                print(ArgumentsStrings.INDEX_FILE_DOES_NOT_EXIST.value.format(secondArgument))
            }
        } else {
            // Case 3: "Tracked files:" is not found
            if (fileToBeTracked.exists() && fileToBeTracked.isFile) {
                // Create a new "Tracked files:" field with the provided argument
                fileData.add("Tracked files:")
                fileData.add(secondArgument)
                file.writeText(fileData.joinToString("\n"))
                print(ArgumentsStrings.INDEX_OUTPUT_FILE_TRACKED.value.format(secondArgument))
            } else {
                print(ArgumentsStrings.INDEX_FILE_DOES_NOT_EXIST.value.format(secondArgument))
            }
        }
    }
}
