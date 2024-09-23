package com.example.bootapp.domain

import kotlinx.coroutines.flow.Flow

interface BootRepository {
    suspend fun saveBootToDB(timestamp: Long)
    suspend fun getAllBoots(): List<BootModel>
    suspend fun getLastTwoBoots(): List<BootNotificationModel>
    fun getBootsAsFlow(): Flow<List<BootModel>>
    fun getBootsByDayAsFlow(): Flow<List<BootDayGroupModel>>
}