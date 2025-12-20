package dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.local.dao.MemoryDao;
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.domain.repository.NexusMemoryRepository;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NexusMemoryModule_ProvideNexusMemoryRepositoryFactory implements Factory<NexusMemoryRepository> {
  private final Provider<MemoryDao> memoryDaoProvider;

  private NexusMemoryModule_ProvideNexusMemoryRepositoryFactory(
      Provider<MemoryDao> memoryDaoProvider) {
    this.memoryDaoProvider = memoryDaoProvider;
  }

  @Override
  public NexusMemoryRepository get() {
    return provideNexusMemoryRepository(memoryDaoProvider.get());
  }

  public static NexusMemoryModule_ProvideNexusMemoryRepositoryFactory create(
      Provider<MemoryDao> memoryDaoProvider) {
    return new NexusMemoryModule_ProvideNexusMemoryRepositoryFactory(memoryDaoProvider);
  }

  public static NexusMemoryRepository provideNexusMemoryRepository(MemoryDao memoryDao) {
    return Preconditions.checkNotNullFromProvides(NexusMemoryModule.INSTANCE.provideNexusMemoryRepository(memoryDao));
  }
}
