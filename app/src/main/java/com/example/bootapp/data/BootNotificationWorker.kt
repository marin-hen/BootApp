package com.example.bootapp.data

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bootapp.R
import com.example.bootapp.domain.BootNotificationModel
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

        Log.d("KMary1", "  doWork")
        val list = bootRepository.getLastTwoBoots()
        Log.d("KMary1", "  getAllBoots() items ${list.joinToString() }")

        // todo add the dismissPendingIntent action to notification

        notificationManager.notify(NOTIFICATION_ID, buildNotification(list.buildBody()))

        // todo setForeground if it's necessary
        //   setForeground(ForegroundInfo(NOTIFICATION_ID, buildNotification("hello")))
        return Result.success()
    }

    private fun buildNotification(body: String) =
        // todo the builder may be extracted to di
        NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setContentTitle("Boots Notification")
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

    private fun List<BootNotificationModel>.buildBody(): String {
            return when (size) {
            0 -> "No boots detected"
            1 -> {
                val singleBootEvent = this[0]
                "The boot was detected = ${singleBootEvent.timestamp.formatTimestamp()}"
            }
            else -> {
                val lastBootEvent = this[0]
                val priorBootEvent = this[1]
                val timeDelta = calculateTimeDelta(priorBootEvent.timestamp, lastBootEvent.timestamp)
                "Last boots time delta = $timeDelta"
            }
        }
    }

    fun calculateTimeDelta(previous: Long, recent: Long): String {
        val deltaMillis = recent - previous
        val seconds = (deltaMillis / 1000) % 60
        val minutes = (deltaMillis / (1000 * 60)) % 60
        val hours = (deltaMillis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL = "Boot Notifications"
        private const val NOTIFICATION_ID = 1
    }
}