package dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local.Converters
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local.entity.MemoryEntity
import dev.aurakai.auraframefx.agents.growthmetrics.nexusmemory.`data`.local.entity.MemoryType
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class MemoryDao_Impl(
  __db: RoomDatabase,
) : MemoryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMemoryEntity: EntityInsertAdapter<MemoryEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfMemoryEntity: EntityDeleteOrUpdateAdapter<MemoryEntity>

  private val __updateAdapterOfMemoryEntity: EntityDeleteOrUpdateAdapter<MemoryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfMemoryEntity = object : EntityInsertAdapter<MemoryEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `nexus_memories` (`id`,`key`,`content`,`timestamp`,`type`,`tags`,`importance`,`embedding`,`relatedMemoryIds`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MemoryEntity) {
        statement.bindLong(1, entity.id)
        val _tmpKey: String? = entity.key
        if (_tmpKey == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpKey)
        }
        statement.bindText(3, entity.content)
        statement.bindLong(4, entity.timestamp)
        val _tmp: String = __converters.fromMemoryType(entity.type)
        statement.bindText(5, _tmp)
        val _tmp_1: String = __converters.fromStringList(entity.tags)
        statement.bindText(6, _tmp_1)
        statement.bindDouble(7, entity.importance.toDouble())
        val _tmpEmbedding: List<Float>? = entity.embedding
        val _tmp_2: String? = __converters.fromFloatList(_tmpEmbedding)
        if (_tmp_2 == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmp_2)
        }
        val _tmp_3: String = __converters.fromLongList(entity.relatedMemoryIds)
        statement.bindText(9, _tmp_3)
      }
    }
    this.__deleteAdapterOfMemoryEntity = object : EntityDeleteOrUpdateAdapter<MemoryEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `nexus_memories` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: MemoryEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfMemoryEntity = object : EntityDeleteOrUpdateAdapter<MemoryEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `nexus_memories` SET `id` = ?,`key` = ?,`content` = ?,`timestamp` = ?,`type` = ?,`tags` = ?,`importance` = ?,`embedding` = ?,`relatedMemoryIds` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: MemoryEntity) {
        statement.bindLong(1, entity.id)
        val _tmpKey: String? = entity.key
        if (_tmpKey == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpKey)
        }
        statement.bindText(3, entity.content)
        statement.bindLong(4, entity.timestamp)
        val _tmp: String = __converters.fromMemoryType(entity.type)
        statement.bindText(5, _tmp)
        val _tmp_1: String = __converters.fromStringList(entity.tags)
        statement.bindText(6, _tmp_1)
        statement.bindDouble(7, entity.importance.toDouble())
        val _tmpEmbedding: List<Float>? = entity.embedding
        val _tmp_2: String? = __converters.fromFloatList(_tmpEmbedding)
        if (_tmp_2 == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmp_2)
        }
        val _tmp_3: String = __converters.fromLongList(entity.relatedMemoryIds)
        statement.bindText(9, _tmp_3)
        statement.bindLong(10, entity.id)
      }
    }
  }

  public override suspend fun insertMemory(memory: MemoryEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfMemoryEntity.insertAndReturnId(_connection, memory)
    _result
  }

  public override suspend fun insertMemories(memories: List<MemoryEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfMemoryEntity.insert(_connection, memories)
  }

  public override suspend fun deleteMemory(memory: MemoryEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfMemoryEntity.handle(_connection, memory)
  }

  public override suspend fun updateMemory(memory: MemoryEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfMemoryEntity.handle(_connection, memory)
  }

  public override suspend fun getMemoryById(id: Long): MemoryEntity? {
    val _sql: String = "SELECT * FROM nexus_memories WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfImportance: Int = getColumnIndexOrThrow(_stmt, "importance")
        val _columnIndexOfEmbedding: Int = getColumnIndexOrThrow(_stmt, "embedding")
        val _columnIndexOfRelatedMemoryIds: Int = getColumnIndexOrThrow(_stmt, "relatedMemoryIds")
        val _result: MemoryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpKey: String?
          if (_stmt.isNull(_columnIndexOfKey)) {
            _tmpKey = null
          } else {
            _tmpKey = _stmt.getText(_columnIndexOfKey)
          }
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpType: MemoryType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toMemoryType(_tmp)
          val _tmpTags: List<String>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfTags)
          _tmpTags = __converters.toStringList(_tmp_1)
          val _tmpImportance: Float
          _tmpImportance = _stmt.getDouble(_columnIndexOfImportance).toFloat()
          val _tmpEmbedding: List<Float>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfEmbedding)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfEmbedding)
          }
          _tmpEmbedding = __converters.toFloatList(_tmp_2)
          val _tmpRelatedMemoryIds: List<Long>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfRelatedMemoryIds)
          _tmpRelatedMemoryIds = __converters.toLongList(_tmp_3)
          _result = MemoryEntity(_tmpId,_tmpKey,_tmpContent,_tmpTimestamp,_tmpType,_tmpTags,_tmpImportance,_tmpEmbedding,_tmpRelatedMemoryIds)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMemoryByKey(key: String): MemoryEntity? {
    val _sql: String = "SELECT * FROM nexus_memories WHERE `key` = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, key)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfImportance: Int = getColumnIndexOrThrow(_stmt, "importance")
        val _columnIndexOfEmbedding: Int = getColumnIndexOrThrow(_stmt, "embedding")
        val _columnIndexOfRelatedMemoryIds: Int = getColumnIndexOrThrow(_stmt, "relatedMemoryIds")
        val _result: MemoryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpKey: String?
          if (_stmt.isNull(_columnIndexOfKey)) {
            _tmpKey = null
          } else {
            _tmpKey = _stmt.getText(_columnIndexOfKey)
          }
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpType: MemoryType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toMemoryType(_tmp)
          val _tmpTags: List<String>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfTags)
          _tmpTags = __converters.toStringList(_tmp_1)
          val _tmpImportance: Float
          _tmpImportance = _stmt.getDouble(_columnIndexOfImportance).toFloat()
          val _tmpEmbedding: List<Float>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfEmbedding)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfEmbedding)
          }
          _tmpEmbedding = __converters.toFloatList(_tmp_2)
          val _tmpRelatedMemoryIds: List<Long>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfRelatedMemoryIds)
          _tmpRelatedMemoryIds = __converters.toLongList(_tmp_3)
          _result = MemoryEntity(_tmpId,_tmpKey,_tmpContent,_tmpTimestamp,_tmpType,_tmpTags,_tmpImportance,_tmpEmbedding,_tmpRelatedMemoryIds)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllMemories(): Flow<List<MemoryEntity>> {
    val _sql: String = "SELECT * FROM nexus_memories ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("nexus_memories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfImportance: Int = getColumnIndexOrThrow(_stmt, "importance")
        val _columnIndexOfEmbedding: Int = getColumnIndexOrThrow(_stmt, "embedding")
        val _columnIndexOfRelatedMemoryIds: Int = getColumnIndexOrThrow(_stmt, "relatedMemoryIds")
        val _result: MutableList<MemoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MemoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpKey: String?
          if (_stmt.isNull(_columnIndexOfKey)) {
            _tmpKey = null
          } else {
            _tmpKey = _stmt.getText(_columnIndexOfKey)
          }
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpType: MemoryType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toMemoryType(_tmp)
          val _tmpTags: List<String>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfTags)
          _tmpTags = __converters.toStringList(_tmp_1)
          val _tmpImportance: Float
          _tmpImportance = _stmt.getDouble(_columnIndexOfImportance).toFloat()
          val _tmpEmbedding: List<Float>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfEmbedding)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfEmbedding)
          }
          _tmpEmbedding = __converters.toFloatList(_tmp_2)
          val _tmpRelatedMemoryIds: List<Long>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfRelatedMemoryIds)
          _tmpRelatedMemoryIds = __converters.toLongList(_tmp_3)
          _item = MemoryEntity(_tmpId,_tmpKey,_tmpContent,_tmpTimestamp,_tmpType,_tmpTags,_tmpImportance,_tmpEmbedding,_tmpRelatedMemoryIds)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getMemoriesByType(type: MemoryType): Flow<List<MemoryEntity>> {
    val _sql: String = "SELECT * FROM nexus_memories WHERE type = ? ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("nexus_memories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __converters.fromMemoryType(type)
        _stmt.bindText(_argIndex, _tmp)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfImportance: Int = getColumnIndexOrThrow(_stmt, "importance")
        val _columnIndexOfEmbedding: Int = getColumnIndexOrThrow(_stmt, "embedding")
        val _columnIndexOfRelatedMemoryIds: Int = getColumnIndexOrThrow(_stmt, "relatedMemoryIds")
        val _result: MutableList<MemoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MemoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpKey: String?
          if (_stmt.isNull(_columnIndexOfKey)) {
            _tmpKey = null
          } else {
            _tmpKey = _stmt.getText(_columnIndexOfKey)
          }
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpType: MemoryType
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toMemoryType(_tmp_1)
          val _tmpTags: List<String>
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfTags)
          _tmpTags = __converters.toStringList(_tmp_2)
          val _tmpImportance: Float
          _tmpImportance = _stmt.getDouble(_columnIndexOfImportance).toFloat()
          val _tmpEmbedding: List<Float>?
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfEmbedding)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfEmbedding)
          }
          _tmpEmbedding = __converters.toFloatList(_tmp_3)
          val _tmpRelatedMemoryIds: List<Long>
          val _tmp_4: String
          _tmp_4 = _stmt.getText(_columnIndexOfRelatedMemoryIds)
          _tmpRelatedMemoryIds = __converters.toLongList(_tmp_4)
          _item = MemoryEntity(_tmpId,_tmpKey,_tmpContent,_tmpTimestamp,_tmpType,_tmpTags,_tmpImportance,_tmpEmbedding,_tmpRelatedMemoryIds)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchMemories(query: String): Flow<List<MemoryEntity>> {
    val _sql: String = "SELECT * FROM nexus_memories WHERE content LIKE '%' || ? || '%' ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("nexus_memories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfImportance: Int = getColumnIndexOrThrow(_stmt, "importance")
        val _columnIndexOfEmbedding: Int = getColumnIndexOrThrow(_stmt, "embedding")
        val _columnIndexOfRelatedMemoryIds: Int = getColumnIndexOrThrow(_stmt, "relatedMemoryIds")
        val _result: MutableList<MemoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MemoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpKey: String?
          if (_stmt.isNull(_columnIndexOfKey)) {
            _tmpKey = null
          } else {
            _tmpKey = _stmt.getText(_columnIndexOfKey)
          }
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpType: MemoryType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toMemoryType(_tmp)
          val _tmpTags: List<String>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfTags)
          _tmpTags = __converters.toStringList(_tmp_1)
          val _tmpImportance: Float
          _tmpImportance = _stmt.getDouble(_columnIndexOfImportance).toFloat()
          val _tmpEmbedding: List<Float>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfEmbedding)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfEmbedding)
          }
          _tmpEmbedding = __converters.toFloatList(_tmp_2)
          val _tmpRelatedMemoryIds: List<Long>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfRelatedMemoryIds)
          _tmpRelatedMemoryIds = __converters.toLongList(_tmp_3)
          _item = MemoryEntity(_tmpId,_tmpKey,_tmpContent,_tmpTimestamp,_tmpType,_tmpTags,_tmpImportance,_tmpEmbedding,_tmpRelatedMemoryIds)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getImportantMemories(minImportance: Float): Flow<List<MemoryEntity>> {
    val _sql: String = "SELECT * FROM nexus_memories WHERE importance >= ? ORDER BY importance DESC"
    return createFlow(__db, false, arrayOf("nexus_memories")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindDouble(_argIndex, minImportance.toDouble())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKey: Int = getColumnIndexOrThrow(_stmt, "key")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfImportance: Int = getColumnIndexOrThrow(_stmt, "importance")
        val _columnIndexOfEmbedding: Int = getColumnIndexOrThrow(_stmt, "embedding")
        val _columnIndexOfRelatedMemoryIds: Int = getColumnIndexOrThrow(_stmt, "relatedMemoryIds")
        val _result: MutableList<MemoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MemoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpKey: String?
          if (_stmt.isNull(_columnIndexOfKey)) {
            _tmpKey = null
          } else {
            _tmpKey = _stmt.getText(_columnIndexOfKey)
          }
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpType: MemoryType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toMemoryType(_tmp)
          val _tmpTags: List<String>
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfTags)
          _tmpTags = __converters.toStringList(_tmp_1)
          val _tmpImportance: Float
          _tmpImportance = _stmt.getDouble(_columnIndexOfImportance).toFloat()
          val _tmpEmbedding: List<Float>?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfEmbedding)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfEmbedding)
          }
          _tmpEmbedding = __converters.toFloatList(_tmp_2)
          val _tmpRelatedMemoryIds: List<Long>
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfRelatedMemoryIds)
          _tmpRelatedMemoryIds = __converters.toLongList(_tmp_3)
          _item = MemoryEntity(_tmpId,_tmpKey,_tmpContent,_tmpTimestamp,_tmpType,_tmpTags,_tmpImportance,_tmpEmbedding,_tmpRelatedMemoryIds)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
