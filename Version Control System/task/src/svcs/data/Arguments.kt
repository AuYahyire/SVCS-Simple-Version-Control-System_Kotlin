package svcs.data

enum class Arguments(val value: String) {
    // Each enum value corresponds to an SVCS command with its description
    CONFIG("Get and set a username."),
    ADD("Add a file to the index."),
    LOG("Show commit logs."),
    COMMIT("Save changes."),
    CHECKOUT("Restore a file."),
    HELP("""
        These are SVCS commands:
        config     ${CONFIG.value}
        add        ${ADD.value}
        log        ${LOG.value}
        commit     ${COMMIT.value}
        checkout   ${CHECKOUT.value}
    """.trimIndent())
}