package com.example.bootapp.domain

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bootapp.data.BootNotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BootWorkUseCase @Inject constructor(private val workManager: WorkManager) {

    fun schedulePeriodicBootWork() {
        val workRequest =
            PeriodicWorkRequestBuilder<BootNotificationWorker>(15, TimeUnit.MINUTES).build()

        workManager.enqueueUniquePeriodicWork(
            "BootWorkPeriodically",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun scheduleOneTimeBootWork() {
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<BootNotificationWorker>().build()

        workManager.enqueueUniqueWork(
            "BootWork",
            ExistingWorkPolicy.KEEP,
            oneTimeWorkRequest
        )
    }
}