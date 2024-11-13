package com.example.bootapp.data

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bootapp.R
import com.example.bootapp.domain.BootRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class BootNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters,
    private val bootRepository: BootRepository
) : CoroutineWorker(context, parameters) {

    @Inject
    lateinit var notificationManager: NotificationManager

    override suspend fun doWork(): Result {

        val list = bootRepository.getLastTwoBoots()

        notificationManager.notify(
            NOTIFICATION_ID,
            buildNotification(list.last().timestamp.toString())
        )

        // todo setForeground if it's necessary
        //   setForeground(ForegroundInfo(NOTIFICATION_ID, buildNotification("hello")))
        return Result.success()
    }

    private fun buildNotification(body: String) =
        NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setContentTitle("Boots Notification")
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

    companion object {
        private const val NOTIFICATION_CHANNEL = "Boot Notifications"
        private const val NOTIFICATION_ID = 1
    }
}