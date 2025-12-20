package dev.aurakai.auraframefx.genesis.bridge

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.domain.repository.NexusMemoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BridgeModule {
    
    @Binds
    @Singleton
    abstract fun bindBridgeMemorySink(impl: NexusMemoryBridgeSink): BridgeMemorySink
    
    @Binds
    @Singleton
    abstract fun bindGenesisBridge(impl: StdioGenesisBridge): GenesisBridge
}

@Module
@InstallIn(SingletonComponent::class)
object BridgeProviderModule {
    
    @Provides
    @Singleton
    fun provideNexusMemoryBridgeSink(
        nexusMemoryRepository: NexusMemoryRepository
    ): NexusMemoryBridgeSink {
        return NexusMemoryBridgeSink(nexusMemoryRepository)
    }
    
    @Provides
    @Singleton
    fun provideStdioGenesisBridge(
        @ApplicationContext context: Context,
        memorySink: BridgeMemorySink
    ): StdioGenesisBridge {
        return StdioGenesisBridge(context, memorySink)
    }
}
