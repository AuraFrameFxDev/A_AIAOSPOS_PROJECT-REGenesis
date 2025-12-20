package dev.aurakai.auraframefx.agents.growthmetrics.identity.repository;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class IdentityRepositoryImpl_Factory implements Factory<IdentityRepositoryImpl> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  private IdentityRepositoryImpl_Factory(Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public IdentityRepositoryImpl get() {
    return newInstance(dataStoreProvider.get());
  }

  public static IdentityRepositoryImpl_Factory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new IdentityRepositoryImpl_Factory(dataStoreProvider);
  }

  public static IdentityRepositoryImpl newInstance(DataStore<Preferences> dataStore) {
    return new IdentityRepositoryImpl(dataStore);
  }
}
