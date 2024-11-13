package com.example.bootapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bootapp.domain.BootRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bootRepository: BootRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {

            val pendingResult = goAsync()

            CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, exception ->
                Log.e("KMary1", "Error occurred: ${exception.localizedMessage}")
            }) {
                try {
                    bootRepository.saveBootToDB(System.currentTimeMillis())
                } finally {
                    pendingResult.finish()
                }
            }
            Log.d("KMary1", "Boot event received")
        }
    }
}