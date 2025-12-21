package dev.aurakai.auraframefx.agents.growthmetrics.identity.repository;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;

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

  private final Provider<Json> jsonProvider;

  private IdentityRepositoryImpl_Factory(Provider<DataStore<Preferences>> dataStoreProvider,
      Provider<Json> jsonProvider) {
    this.dataStoreProvider = dataStoreProvider;
    this.jsonProvider = jsonProvider;
  }

  @Override
  public IdentityRepositoryImpl get() {
    return newInstance(dataStoreProvider.get(), jsonProvider.get());
  }

  public static IdentityRepositoryImpl_Factory create(
      Provider<DataStore<Preferences>> dataStoreProvider, Provider<Json> jsonProvider) {
    return new IdentityRepositoryImpl_Factory(dataStoreProvider, jsonProvider);
  }

  public static IdentityRepositoryImpl newInstance(DataStore<Preferences> dataStore, Json json) {
    return new IdentityRepositoryImpl(dataStore, json);
  }
}
