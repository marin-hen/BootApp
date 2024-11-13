package com.example.bootapp.data

import com.example.bootapp.domain.BootDayGroupModel
import com.example.bootapp.domain.BootModel
import com.example.bootapp.domain.BootNotificationModel
import com.example.bootapp.domain.BootRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootRepositoryImpl @Inject constructor(
    private val bootDao: BootDao
) : BootRepository {

    override suspend fun saveBootToDB(timestamp: Long) {
        bootDao.insert(BootEntity(timestamp = timestamp))
    }

    override suspend fun getAllBoots(): List<BootModel> {
        return bootDao.getAllBoots().map { it.toDomain() }
    }

    override suspend fun getLastTwoBoots(): List<BootNotificationModel> {
        return bootDao.getLastTwoBoots().map { BootNotificationModel(it.timestamp) }
    }

    override fun getBootsAsFlow(): Flow<List<BootModel>> {

        return bootDao.getAllBootsAsFlow()
            .mapNotNull { bootEntities -> bootEntities.map { it.toDomain() } }
    }

    override fun getBootsByDayAsFlow(): Flow<List<BootDayGroupModel>> {
        return bootDao.getBootsGroupedByDay()
            .mapNotNull { dayGroup -> dayGroup.map { it.toDomain() } }
    }
}