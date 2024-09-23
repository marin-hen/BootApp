package com.example.bootapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bootapp.domain.BootModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BootDao {
    @Query(QUERY_ALL)
    fun getAllBootsAsFlow(): Flow<List<BootEntity>>

    @Query(QUERY_ALL)
    suspend fun getAllBoots(): List<BootEntity>

    @Query(QUERY_GET_LAST_TWO)
    suspend fun getLastTwoBoots(): List<BootEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(boot: BootEntity)

    //todo to const
    @Query("SELECT strftime('%Y-%m-%d', datetime(${BootEntity.TIMESTAMP_ID} / 1000, 'unixepoch')) AS day, COUNT(*) AS bootCount FROM ${BootEntity.TABLE_NAME} GROUP BY day ORDER BY day DESC")
    fun getBootsGroupedByDay(): Flow<List<BootDayGroup>>

    companion object {
        internal const val QUERY_ALL = """
        SELECT * FROM ${BootEntity.TABLE_NAME}
        """

        internal const val QUERY_GET_LAST_TWO = """
        SELECT * FROM ${BootEntity.TABLE_NAME} ORDER BY ${BootEntity.TIMESTAMP_ID} DESC LIMIT 2 
        """
    }
}