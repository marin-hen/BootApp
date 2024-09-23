package com.example.bootapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bootapp.data.BootNotificationWorker
import com.example.bootapp.domain.BootRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class BootListViewModel @Inject constructor(
    bootRepository: BootRepository,
    private val workManager: WorkManager
) : ViewModel() {

    init {
        //todo move to usecase or to the startapp
        val workRequest =
            PeriodicWorkRequestBuilder<BootNotificationWorker>(15, TimeUnit.MINUTES).build()

        workManager
            .enqueueUniquePeriodicWork(
                "BootWorkPeriodically",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )

//        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<BootNotificationWorker>().build()
//        workManager
//            .enqueueUniqueWork(
//                "BootWork",
//                ExistingWorkPolicy.KEEP,
//                oneTimeWorkRequest
//            )
    }

    private val _state: Flow<BootListUiState> = bootRepository.getBootsByDayAsFlow()
        .map { bootList -> BootListUiState(boots = bootList) }

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BootListUiState()
    ).asLiveData()

    override fun onCleared() {
        super.onCleared()
        //workManager.cancelUniqueWork("BootWork")
    }
}