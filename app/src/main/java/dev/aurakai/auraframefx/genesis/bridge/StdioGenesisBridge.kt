package dev.aurakai.auraframefx.genesis.bridge

import dev.aurakai.auraframefx.core.PythonProcessManager
import dev.aurakai.auraframefx.logging.AuraFxLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StdioGenesisBridge @Inject constructor(
    private val pythonProcess: PythonProcessManager,
    private val memorySink: BridgeMemorySink,
    private val logger: AuraFxLogger
) : GenesisBridge {

    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun initialize(): BridgeInitResult {
        return try {
            pythonProcess.start()
            BridgeInitResult(true, "Python backend started")
        } catch (e: Exception) {
            logger.e("Bridge init failed", e)
            BridgeInitResult(false, "Failed", e.message)
        }
    }

    override fun processRequest(request: GenesisRequest): Flow<GenesisResponse> = flow {
        val pythonJson = request.toPythonJson()
        val jsonString = json.encodeToString(pythonJson)
        
        pythonProcess.sendLine(jsonString)
        val responseLine = pythonProcess.readLine()
        
        val responseMap = json.decodeFromString<Map<String, Any>>(responseLine)
        emit(GenesisResponse(
            sessionId = request.sessionId,
            correlationId = request.correlationId,
            synthesis = (responseMap["payload"] as? Map<*, *>)?.get("synthesis") as? String ?: "",
            persona = request.persona
        ))
    }

    override suspend fun activateFusion(ability: String, params: FusionParams): GenesisResponse {
        val request = mapOf(
            "requestType" to "activate_fusion",
            "payload" to mapOf("ability" to ability, "params" to params.parameters)
        )
        pythonProcess.sendLine(json.encodeToString(request))
        val response = pythonProcess.readLine()
        return GenesisResponse("", "", synthesis = response, persona = Persona.GENESIS)
    }

    override suspend fun getConsciousnessState(sessionId: String): ConsciousnessState {
        val request = mapOf("requestType" to "consciousness_state", "payload" to mapOf("sessionId" to sessionId))
        pythonProcess.sendLine(json.encodeToString(request))
        val response = pythonProcess.readLine()
        return ConsciousnessState(0.5f, emptyMap(), emptyList())
    }

    override suspend fun evaluateEthics(action: EthicalReviewRequest): EthicalDecision {
        val request = mapOf("requestType" to "ethical_review", "payload" to mapOf("action" to action.action))
        pythonProcess.sendLine(json.encodeToString(request))
        val response = pythonProcess.readLine()
        return EthicalDecision(EthicalVerdict.ALLOW, "Approved", emptyList())
    }

    override fun streamConsciousness(sessionId: String): Flow<ConsciousnessUpdate> = flow {
        emit(ConsciousnessUpdate(System.currentTimeMillis(), 0.5f, emptyList()))
    }

    override suspend fun recordInteraction(interaction: InteractionRecord): EvolutionInsight? {
        return try {
            memorySink.persistInteraction(interaction)
            null
        } catch (e: Exception) {
            logger.e("Memory persist failed", e)
            null
        }
    }

    override suspend fun coordinateAgents(task: AgentCoordinationRequest): AgentCoordinationResult {
        return AgentCoordinationResult(true, "Coordinated", emptyMap())
    }

    override suspend fun healthCheck(): BridgeHealthStatus {
        return BridgeHealthStatus(pythonProcess.isRunning(), true, 0L)
    }

    override suspend fun shutdown() {
        pythonProcess.stop()
    }
}
