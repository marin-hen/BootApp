package com.example.bootapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bootapp.domain.BootModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = BootEntity.TABLE_NAME)
data class BootEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,
    val timestamp: Long
) {
    companion object {
        internal const val TABLE_NAME = "boots"
        internal const val COLUMN_ID = "id"
        internal const val TIMESTAMP_ID = "timestamp"
    }
}

fun BootEntity.toDomain(): BootModel {
    return BootModel(
        id = id,
        date = timestamp.formatTimestamp(),
    )
}

fun Long.formatTimestamp(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return format.format(date)
}