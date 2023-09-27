package svcs.commandLogics

import svcs.data.ArgumentsStrings
import java.io.File

/**
 * Config class handles the configuration logic and manages a config file.
 *
 * @property secondArgument The second argument provided to the program.
 */
class Config(private val secondArgument: String?) : ArgumentsLogic {

    // Define the name of the config file
    override val fileName = "config.txt"

    // Create a File object for the config file
    override val file = File("${vcsPath}${separator}${fileName}")

    /**
     * Executes the argument logic for Config.
     * Checks if VCS directory exists, creates a VCS directory if not,
     * checks if the config file exists, and manages the config file.
     */
    override fun executeArgument() {
        super.executeArgument() // Checks for VCS directory.
        if (!checkIfFileExists()) {
            file.createNewFile()
        }
        manageConfigFileContent()
    }

    /**
     * Manages the content of the config file.
     * This method reads the existing content of the config file,
     * updates or creates the "Name:" field in the config file,
     * and handles various scenarios related to it.
     */
    private fun manageConfigFileContent() {
        // Read the existing content of the config file into a mutable list of lines
        val fileData = file.readLines().toMutableList()

        // Initialize a boolean variable to check if "Name:" is found in the config file
        var nameFound = false

        // Iterate through each line in the config file
        for (line in fileData.indices) {
            if (fileData[line].contains("Name:")) {
                // "Name:" exists in this line
                nameFound = true

                // Overwrite the name with the argument provided if it is not null.
                if (!secondArgument.isNullOrEmpty()) {
                    fileData[line] = "Name: $secondArgument"
                    file.writeText(fileData.joinToString("\n"))
                }

                // Extract the value associated with "Name:" and trim any leading/trailing spaces
                val nameValue = fileData[line].substringAfter("Name:").trim()

                // Print the updated or existing "Name:" value
                print(ArgumentsStrings.CONFIG_OUTPUT_NAME.value.format(nameValue))

                // Exit the loop once "Name:" is found and processed
                break
            }
        }

        // Handle scenarios where the secondArgument is empty or null and "Name:" is not found
        if (secondArgument.isNullOrEmpty() && !nameFound) {
            print(ArgumentsStrings.CONFIG_NO_FILE_OR_NAME.value)
        }

        // Handle scenarios where the secondArgument is not empty, and "Name:" is not found
        if (!secondArgument.isNullOrEmpty() && !nameFound) {
            // Create a new "Name:" field with the provided argument and write it to the config file
            file.writeText("Name: $secondArgument")

            // Print the newly created "Name:" value
            print(ArgumentsStrings.CONFIG_OUTPUT_NAME.value.format(secondArgument))
        }
    }

}
