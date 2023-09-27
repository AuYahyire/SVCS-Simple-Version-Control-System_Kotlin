package svcs.commandLogics

import java.io.File

/**
 * Interface representing the common logic for version control system arguments.
 */
interface ArgumentsLogic {
    /**
     * The root directory of the project.
     */
    private val projectRoot: String
        get() = System.getProperty("user.dir")

    /**
     * The platform-specific file separator.
     */
    val separator: String
        get() = File.separator

    /**
     * The name of the version control system directory.
     */
    val vcsFilename: String
        get() = "vcs"

    /**
     * The path to the version control system directory.
     */
    val vcsPath: File
        get() = File("${projectRoot}${separator}${vcsFilename}")

    /**
     * The root path of the project as a File object.
     */
    val rootPath: File
        get() = File(projectRoot)

    /**
     * The name of the file associated with this argument logic.
     */
    val fileName: String

    /**
     * The File object associated with this argument logic.
     */
    val file: File

    val commitDirectoryName: String
        get() = "commits"

    val commitDirectoryFile: File
        get() = File("${vcsPath}${separator}${commitDirectoryName}")

    /**
     * Checks if the associated file exists and is a regular file.
     *
     * @return `true` if the file exists and is a regular file, `false` otherwise.
     */
    fun checkIfFileExists(): Boolean {
        return file.exists() && file.isFile
    }

    /**
     * Checks if the version control system directory exists.
     *
     * @return `true` if the directory exists, `false` otherwise.
     */
    fun checkIfVCSDirectoryExists(): Boolean {
        return vcsPath.exists() && vcsPath.isDirectory
    }

    /**
     * Creates the version control system directory if it doesn't exist.
     */
    fun createVCSDirectory() {
        vcsPath.mkdir()
    }

    /**
     * Executes the argument logic, which may involve checking and creating the VCS directory.
     */
    fun executeArgument() {
        if (!checkIfVCSDirectoryExists()) {
            createVCSDirectory()
        }
    }

    /**
     * Checks if the commits directory exists.
     *
     * @return 'true' if the commits directory exists, 'false' otherwise.
     */
   fun checkIfCommitsDirExists() : Boolean {
        return commitDirectoryFile.exists()
    }


}
