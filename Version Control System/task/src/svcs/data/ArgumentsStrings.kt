package svcs.data

/**
 * Enumeration representing various string messages used in the version control system arguments.
 * Each enum value contains a corresponding message string.
 *
 * @property value The string message associated with the enum value.
 */
enum class ArgumentsStrings(val value: String) {
    /**
     * Message for the 'config' argument when no username is provided.
     */
    CONFIG_NO_FILE_OR_NAME("Please, tell me who you are."),

    /**
     * Message for the 'config' argument when displaying the username.
     * The %s placeholder will be replaced with the actual username.
     */
    CONFIG_OUTPUT_NAME("The username is %s."),

    /**
     * Message for the 'add' argument when no file is provided.
     */
    INDEX_NO_FILE_OR_NAME("Add a file to the index."),

    /**
     * Message for the 'add' argument when a file is successfully tracked.
     * The %s placeholder will be replaced with the actual file name.
     */
    INDEX_OUTPUT_FILE_TRACKED("The file '%s' is tracked."),

    /**
     * Message for the 'add' argument when the specified file cannot be found.
     * The %s placeholder will be replaced with the actual file name.
     */
    INDEX_FILE_DOES_NOT_EXIST("Can't find '%s'."),

    MESSAGE_WAS_NOT_PASSED("Message was not passed."),

    NOTHING_TO_COMMIT("Nothing to commit."),

    NO_COMMITS_YET("No commits yet."),

    CHANGES_ARE_COMMITTED("Changes are committed.")
}
