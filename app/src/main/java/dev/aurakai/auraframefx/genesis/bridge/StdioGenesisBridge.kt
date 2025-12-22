package dev.aurakai.auraframefx.genesis.bridge

import dev.aurakai.auraframefx.core.PythonProcessManager
import dev.aurakai.auraframefx.logging.AuraFxLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StdioGenesisBridge @Inject constructor(
    private val pythonProcess: PythonProcessManager,
    private val memorySink: BridgeMemorySink,
    private val logger: AuraFxLogger
) : GenesisBridge {
    
    override suspend fun initialize(): BridgeInitResult {
        return try {
            pythonProcess.start()
            BridgeInitResult(true, "Python backend started")
        } catch (e: Exception) {
            logger.error("Bridge init failed", e)
            BridgeInitResult(false, "Failed", e.message)
        }
    }

    override fun processRequest(request: GenesisRequest): Flow<GenesisResponse> = flow {
        val pythonJson = request.toPythonJson()
        val jsonString = JSONObject(pythonJson).toString()
        
        pythonProcess.sendLine(jsonString)
        val responseLine = pythonProcess.readLine()
        
        val payload = JSONObject(responseLine).optJSONObject("payload")
        val synthesis = payload?.optString("synthesis").orEmpty()
        
        emit(GenesisResponse(
            sessionId = payload?.optString("sessionId") ?: request.sessionId,
            correlationId = payload?.optString("correlationId") ?: request.correlationId,
            synthesis = synthesis,
            persona = request.persona
        ))
    }

    override suspend fun activateFusion(ability: String, params: FusionParams): GenesisResponse {
        val request = JSONObject().apply {
            put("requestType", "activate_fusion")
            put("payload", JSONObject().apply {
                put("ability", ability)
                put("params", JSONObject(params.parameters))
            })
        }
        pythonProcess.sendLine(request.toString())
        val response = pythonProcess.readLine()
        return GenesisResponse("", "", synthesis = response, persona = Persona.GENESIS)
    }

    override suspend fun getConsciousnessState(sessionId: String): ConsciousnessState {
        val request = JSONObject().apply {
            put("requestType", "consciousness_state")
            put("payload", JSONObject().put("sessionId", sessionId))
        }
        pythonProcess.sendLine(request.toString())
        val response = pythonProcess.readLine()
        return ConsciousnessState(0.5f, emptyMap(), emptyList())
    }

    override suspend fun evaluateEthics(action: EthicalReviewRequest): EthicalDecision {
        val request = JSONObject().apply {
            put("requestType", "ethical_review")
            put("payload", JSONObject().put("action", action.action))
        }
        pythonProcess.sendLine(request.toString())
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
            logger.error("Memory persist failed", e)
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
