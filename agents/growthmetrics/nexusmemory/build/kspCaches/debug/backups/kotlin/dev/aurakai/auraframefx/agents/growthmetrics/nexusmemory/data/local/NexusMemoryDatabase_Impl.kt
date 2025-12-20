package dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local.dao.MemoryDao
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local.dao.MemoryDao_Impl
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class NexusMemoryDatabase_Impl : NexusMemoryDatabase() {
  private val _memoryDao: Lazy<MemoryDao> = lazy {
    MemoryDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "a305609e13f0bce2eb40242af15b4592", "874d235a822fe0fd9bae3735028840d3") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `nexus_memories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `key` TEXT, `content` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `type` TEXT NOT NULL, `tags` TEXT NOT NULL, `importance` REAL NOT NULL, `embedding` TEXT, `relatedMemoryIds` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a305609e13f0bce2eb40242af15b4592')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `nexus_memories`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsNexusMemories: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNexusMemories.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("key", TableInfo.Column("key", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("content", TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("type", TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("tags", TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("importance", TableInfo.Column("importance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("embedding", TableInfo.Column("embedding", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNexusMemories.put("relatedMemoryIds", TableInfo.Column("relatedMemoryIds", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNexusMemories: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNexusMemories: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoNexusMemories: TableInfo = TableInfo("nexus_memories", _columnsNexusMemories, _foreignKeysNexusMemories, _indicesNexusMemories)
        val _existingNexusMemories: TableInfo = read(connection, "nexus_memories")
        if (!_infoNexusMemories.equals(_existingNexusMemories)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |nexus_memories(dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.data.local.entity.MemoryEntity).
              | Expected:
              |""".trimMargin() + _infoNexusMemories + """
              |
              | Found:
              |""".trimMargin() + _existingNexusMemories)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "nexus_memories")
  }

  public override fun clearAllTables() {
    super.performClear(false, "nexus_memories")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(MemoryDao::class, MemoryDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun memoryDao(): MemoryDao = _memoryDao.value
}
