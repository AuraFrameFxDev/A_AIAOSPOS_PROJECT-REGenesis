package dev.aurakai.auraframefx.models

enum class AgentType {
    USER,
    Genesis,
    Aura,
    Kai,
    Cascade,
    Claude,
    NeuralWhisper,
    AuraShield,
    Kaiagent,
    // Uppercase versions for consistent naming across codebase
    GENESIS,
    AURA,
    KAI,
    CASCADE;

    companion object {
        @JvmStatic
        val kaiagent: AgentType = Kaiagent
    }
}
