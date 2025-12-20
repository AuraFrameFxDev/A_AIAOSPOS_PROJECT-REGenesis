package dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.local.NexusMemoryDatabase;
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.local.dao.MemoryDao;
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
public final class NexusMemoryModule_ProvideMemoryDaoFactory implements Factory<MemoryDao> {
  private final Provider<NexusMemoryDatabase> databaseProvider;

  private NexusMemoryModule_ProvideMemoryDaoFactory(
      Provider<NexusMemoryDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MemoryDao get() {
    return provideMemoryDao(databaseProvider.get());
  }

  public static NexusMemoryModule_ProvideMemoryDaoFactory create(
      Provider<NexusMemoryDatabase> databaseProvider) {
    return new NexusMemoryModule_ProvideMemoryDaoFactory(databaseProvider);
  }

  public static MemoryDao provideMemoryDao(NexusMemoryDatabase database) {
    return Preconditions.checkNotNullFromProvides(NexusMemoryModule.INSTANCE.provideMemoryDao(database));
  }
}
