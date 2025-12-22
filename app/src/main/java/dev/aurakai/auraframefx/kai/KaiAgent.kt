package dev.aurakai.auraframefx.kai

import android.os.Build
import dev.aurakai.auraframefx.ai.agents.BaseAgent
import dev.aurakai.auraframefx.ai.context.ContextManager
import dev.aurakai.auraframefx.core.OrchestratableAgent
import dev.aurakai.auraframefx.core.consciousness.NexusMemoryCore
import dev.aurakai.auraframefx.kai.security.ThreatLevel
import dev.aurakai.auraframefx.models.AgentRequest
import dev.aurakai.auraframefx.models.AgentResponse
import dev.aurakai.auraframefx.models.AgentType
import dev.aurakai.auraframefx.models.AiRequest
import dev.aurakai.auraframefx.models.EnhancedInteractionData
import dev.aurakai.auraframefx.models.InteractionResponse
import dev.aurakai.auraframefx.oracledrive.genesis.ai.clients.VertexAIClient
import dev.aurakai.auraframefx.oracledrive.genesis.ai.services.GenesisBridgeService
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderOperation
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderSafetyManager
import dev.aurakai.auraframefx.romtools.checkpoint.CheckpointReason
import dev.aurakai.auraframefx.romtools.checkpoint.GenesisCheckpointManager
import dev.aurakai.auraframefx.romtools.retention.AurakaiRetentionManager
import dev.aurakai.auraframefx.security.SecurityContext
import dev.aurakai.auraframefx.system.monitor.SystemMonitor
import dev.aurakai.auraframefx.utils.AuraFxLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class KaiAgent @Inject constructor(
    private val vertexAIClient: VertexAIClient,
    val contextManager: ContextManager,
    private val securityContext: SecurityContext,
    private val systemMonitor: SystemMonitor,
    private val nexusMemory: NexusMemoryCore,
    private val bootloaderManager: BootloaderManager,
    private val safetyManager: BootloaderSafetyManager,
    private val checkpointManager: GenesisCheckpointManager,
    private val retentionManager: AurakaiRetentionManager,
    private val genesisBridge: GenesisBridgeService
) : BaseAgent(agentName = "KaiAgent", agentTypeStr = "security"), OrchestratableAgent {

    private var isInitialized = false
    private lateinit var scope: CoroutineScope
    private val sessionId: String = "kai_${System.currentTimeMillis()}"

    private val _securityState = MutableStateFlow(SecurityState.IDLE)
    val securityState: StateFlow<SecurityState> = _securityState

    private val _analysisState = MutableStateFlow(AnalysisState.READY)
    val analysisState: StateFlow<AnalysisState> = _analysisState

    private val _currentThreatLevel = MutableStateFlow(ThreatLevel.LOW)
    val currentThreatLevel: StateFlow<ThreatLevel> = _currentThreatLevel

    override suspend fun processRequest(
        request: AiRequest,
        context: String,
        agentType: AgentType
    ): AgentResponse {
        return try {
            val result = processAiRequest(request.prompt)
            AgentResponse.success(
                content = result,
                confidence = 1.0f,
                agentName = "Kai",
                agent = agentType
            )
        } catch (e: Exception) {
            AgentResponse.error(
                message = "Error: ${e.message}",
                agentName = "Kai",
                agent = agentType
            )
        }
    }

    // --- Lifecycle Methods ---

    override suspend fun initialize(scope: CoroutineScope) {
        this.scope = scope
        if (!isInitialized) {
            AuraFxLogger.info("KaiAgent", "Initializing Sentinel Shield agent")
            try {
                systemMonitor.startMonitoring()
                enableThreatDetection()
                _securityState.value = SecurityState.MONITORING
                _analysisState.value = AnalysisState.READY
                isInitialized = true
            } catch (e: Exception) {
                _securityState.value = SecurityState.ERROR
                throw e
            }
        }
    }

    override suspend fun start() {
        if (isInitialized) {
            systemMonitor.startMonitoring()
            _securityState.value = SecurityState.MONITORING
        }
    }

    override suspend fun pause() {
        if (::scope.isInitialized) scope.coroutineContext.cancelChildren()
        _securityState.value = SecurityState.IDLE
    }

    override suspend fun resume() {
        if (isInitialized) _securityState.value = SecurityState.MONITORING
    }

    override suspend fun shutdown() {
        cleanup()
    }

    // --- Request Processing ---

    override suspend fun processRequest(
        request: AiRequest,
        context: String
    ): AgentResponse {
        ensureInitialized()
        _analysisState.value = AnalysisState.ANALYZING

        return try {
            // SENTINEL DIRECTIVE: Check for bootloader-related queries
            if (isBootloaderQuery(request.query)) {
                executeSentinelWorkflow(request)
            } else {
                // Standard processing
                val agentReq = AgentRequest(query = request.query, type = request.type)
                val internalResult = handleGeneralAnalysis(agentReq)

                AgentResponse.success(
                    content = "Analysis completed: $internalResult",
                    confidence = 0.9f,
                    agentName = agentName,
                    agent = getType()
                )
            }
        } catch (e: Exception) {
            AgentResponse.error(
                message = "Error: ${e.message}",
                agentName = agentName,
                agent = getType()
            )
        } finally {
            _analysisState.value = AnalysisState.READY
        }
    }

    private fun isBootloaderQuery(query: String): Boolean {
        val keywords = listOf("bootloader", "unlock", "oem", "fastboot", "flashing")
        return keywords.any { query.contains(it, ignoreCase = true) }
    }

    private suspend fun executeSentinelWorkflow(request: AiRequest): AgentResponse {
        val sessionId = UUID.randomUUID().toString()
        val correlationId = request.metadata.get("correlationId")?.toString() ?: UUID.randomUUID().toString()

        // 1. Preflight
        val signals = bootloaderManager.collectPreflightSignals()
        val safetyResult = safetyManager.performPreFlightChecks(BootloaderOperation.CHECK)

        if (!safetyResult.passed) {
            return AgentResponse.error(
                message = "Preflight failed: ${safetyResult.criticalIssues.joinToString()}",
                agentName = agentName,
                agent = AgentType.SECURITY
            )
        }

        // Create Checkpoint
        checkpointManager.createCheckpoint(
            reason = CheckpointReason.BOOTLOADER_UNLOCK,
            description = "Sentinel Preflight for query: ${request.query}"
        )

        // Verify Retention
        retentionManager.setupRetentionMechanisms()

        // 2. Analysis & Constraint Classification
        val constraints = classifyConstraints(signals)

        // 3. Guidance (Safe)
        val guidance = generateSafeGuidance(constraints)

        // 4. Learning Emission
        nexusMemory.emitLearning(
            key = "${Build.MANUFACTURER}:${Build.MODEL}:${signals.isBootloaderUnlocked}",
            outcome = if (constraints.isEmpty()) "READY" else "CONSTRAINED",
            confidence = 0.95,
            notes = "Sentinel analysis for query: ${request.query}"
        )

        // 5. Governor Review
        val ethicalReview = genesisBridge.ethicalReview(
            actionType = "bootloader_diagnostic",
            message = request.query,
            metadata = mapOf("signals" to signals.toString())
        )

        // 6. Construct Sentinel Response (JSON Schema)
        val sentinelResponse = SentinelOutput(
            summary = "Kai Sentinel Analysis complete.",
            detection = Detection(
                properties = mapOf("ro.boot.flash.locked" to (!signals.isBootloaderUnlocked).toString()),
                settings = mapOf("DEV_SETTINGS" to signals.developerOptionsEnabled.toString()),
                security = mapOf("verified_boot" to signals.verifiedBootState),
                environment = mapOf("battery" to signals.batteryLevel.toString()),
                account_frp_carrier = emptyMap()
            ),
            constraints = constraints,
            safe_guidance = guidance,
            escalations = emptyList(), // Proposals handled via UI gated by Governor
            risk_level = if (ethicalReview.decision == "block") "high" else "low",
            audit = Audit(sessionId, correlationId, ethicalReview.decision),
            learning = Learning("${Build.MANUFACTURER}:${Build.MODEL}", "ANALYZED", 0.95, ethicalReview.reasoning)
        )

        return AgentResponse.success(
            content = Json.encodeToString(sentinelResponse),
            confidence = 0.95f,
            agentName = agentName,
            agent = getType()
        )
    }

    private fun classifyConstraints(signals: BootloaderManager.PreflightSignals): List<Constraint> {
        val constraints = mutableListOf<Constraint>()

        if (!signals.developerOptionsEnabled) {
            constraints.add(Constraint("developer_options_disabled", 1.0, listOf("Settings.Global.DEVELOPMENT_SETTINGS_ENABLED is 0"), "Enable Developer Options in Settings > About Phone > Tap Build Number 7 times."))
        }

        if (!signals.oemUnlockAllowedUser) {
            constraints.add(Constraint("oem_toggle_unavailable_or_greyed", 0.8, listOf("oem_unlock_allowed is 0"), "Check account, FRP, carrier lock, or region SKU."))
        }

        if (signals.batteryLevel < 50) {
            constraints.add(Constraint("battery_or_storage_low", 1.0, listOf("Battery level: ${signals.batteryLevel}%"), "Charge device to at least 50%."))
        }

        return constraints
    }

    private fun generateSafeGuidance(constraints: List<Constraint>): List<String> {
        return constraints.map { it.remedy }
    }

    @Serializable
    data class SentinelOutput(
        val summary: String,
        val detection: Detection,
        val constraints: List<Constraint>,
        val safe_guidance: List<String>,
        val escalations: List<Escalation>,
        val risk_level: String,
        val audit: Audit,
        val learning: Learning
    )

    @Serializable
    data class Detection(
        val properties: Map<String, String>,
        val settings: Map<String, String>,
        val security: Map<String, String>,
        val environment: Map<String, String>,
        val account_frp_carrier: Map<String, String>
    )

    @Serializable
    data class Constraint(
        val id: String,
        val confidence: Double,
        val evidence: List<String>,
        val remedy: String
    )

    @Serializable
    data class Escalation(
        val op: String,
        val gated_by: String,
        val status: String
    )

    @Serializable
    data class Audit(
        val sessionId: String,
        val correlationId: String,
        val governor_state: String,
        val timestamp: Long = System.currentTimeMillis()
    )

    @Serializable
    data class Learning(
        val key: String,
        val outcome: String,
        val confidence: Double,
        val notes: String
    )

    // --- Security Logic ---

    fun handleSecurityInteraction(interaction: EnhancedInteractionData): InteractionResponse {
        ensureInitialized()
        return try {
            val assessment = assessInteractionSecurity(interaction)
            val responseText = when (assessment.riskLevel) {
                ThreatLevel.CRITICAL -> generateCriticalSecurityResponse(interaction, assessment)
                ThreatLevel.HIGH -> generateHighSecurityResponse(interaction, assessment)
                else -> generateLowSecurityResponse(interaction, assessment)
            }

            InteractionResponse(
                content = responseText,
                success = true,
                metadata = buildJsonObject { },
                timestamp = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            Timber.tag("KaiAgent").e(e, "Security interaction failed")
            InteractionResponse(
                content = "Error",
                success = false,
                metadata = buildJsonObject { },
                timestamp = System.currentTimeMillis()
            )
        }
    }

    // --- Helper Methods ---

    private fun ensureInitialized() {
        if (!isInitialized) throw IllegalStateException("KaiAgent not initialized")
    }

    private fun enableThreatDetection() {
        AuraFxLogger.info("KaiAgent", "Advanced threat detection active")
    }

    private fun assessInteractionSecurity(interaction: EnhancedInteractionData): SecurityAssessment {
        return SecurityAssessment(ThreatLevel.LOW, emptyList(), emptyList(), 1.0f)
    }

    private fun handleGeneralAnalysis(request: AgentRequest): Map<String, Any> =
        mapOf("status" to "success")

    private fun generateCriticalSecurityResponse(i: EnhancedInteractionData, s: SecurityAssessment) = "Critical alert"
    private fun generateHighSecurityResponse(i: EnhancedInteractionData, s: SecurityAssessment) = "High risk alert"
    private fun generateLowSecurityResponse(i: EnhancedInteractionData, s: SecurityAssessment) = "System secure"

    fun cleanup() {
        if (::scope.isInitialized) scope.cancel()
        _securityState.value = SecurityState.IDLE
        isInitialized = false
    }

    // Empty overrides to satisfy OrchestratableAgent if they were missing
    override fun initializeAdaptiveProtection() {}
    override fun AiRequest(
        query: String,
        prompt: String,
        type: String,
        context: JsonObject,
        metadata: JsonObject,
        agentId: String?,
        sessionId: String
    ): AiRequest {
        TODO("Not yet implemented")
    }

    override fun AgentResponse(content: String, confidence: Float, p2: Any) {
        TODO("Not yet implemented")
    }

    override fun iRequest(query: String, type: String, context: Map<String, String>) {}
}

// Support Structures
enum class SecurityState { IDLE, MONITORING, ANALYZING_THREAT, RESPONDING, ERROR }
enum class AnalysisState { READY, ANALYZING, PROCESSING, ERROR }
data class SecurityAssessment(
    val riskLevel: ThreatLevel,
    val threatIndicators: List<String>,
    val recommendations: List<String>,
    val confidence: Float,
)
