package dev.aurakai.auraframefx.agents.growthmetrics.identity.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.agents.growthmetrics.identity.repository.IdentityRepository;
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
public final class IdentityModule_ProvideIdentityRepositoryFactory implements Factory<IdentityRepository> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  private IdentityModule_ProvideIdentityRepositoryFactory(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public IdentityRepository get() {
    return provideIdentityRepository(dataStoreProvider.get());
  }

  public static IdentityModule_ProvideIdentityRepositoryFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new IdentityModule_ProvideIdentityRepositoryFactory(dataStoreProvider);
  }

  public static IdentityRepository provideIdentityRepository(DataStore<Preferences> dataStore) {
    return Preconditions.checkNotNullFromProvides(IdentityModule.INSTANCE.provideIdentityRepository(dataStore));
  }
}
