package dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.local.NexusMemoryDatabase;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class NexusMemoryModule_ProvideNexusMemoryDatabaseFactory implements Factory<NexusMemoryDatabase> {
  private final Provider<Context> contextProvider;

  private NexusMemoryModule_ProvideNexusMemoryDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NexusMemoryDatabase get() {
    return provideNexusMemoryDatabase(contextProvider.get());
  }

  public static NexusMemoryModule_ProvideNexusMemoryDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new NexusMemoryModule_ProvideNexusMemoryDatabaseFactory(contextProvider);
  }

  public static NexusMemoryDatabase provideNexusMemoryDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(NexusMemoryModule.INSTANCE.provideNexusMemoryDatabase(context));
  }
}
