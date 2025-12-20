package dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.local.dao.MemoryDao;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NexusMemoryRepositoryImpl_Factory implements Factory<NexusMemoryRepositoryImpl> {
  private final Provider<MemoryDao> memoryDaoProvider;

  private NexusMemoryRepositoryImpl_Factory(Provider<MemoryDao> memoryDaoProvider) {
    this.memoryDaoProvider = memoryDaoProvider;
  }

  @Override
  public NexusMemoryRepositoryImpl get() {
    return newInstance(memoryDaoProvider.get());
  }

  public static NexusMemoryRepositoryImpl_Factory create(Provider<MemoryDao> memoryDaoProvider) {
    return new NexusMemoryRepositoryImpl_Factory(memoryDaoProvider);
  }

  public static NexusMemoryRepositoryImpl newInstance(MemoryDao memoryDao) {
    return new NexusMemoryRepositoryImpl(memoryDao);
  }
}
