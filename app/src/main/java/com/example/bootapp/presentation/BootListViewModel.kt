package com.example.bootapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bootapp.domain.BootRepository
import com.example.bootapp.domain.BootWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BootListViewModel @Inject constructor(
    bootRepository: BootRepository,
    private val bootWorkUseCase: BootWorkUseCase
) : ViewModel() {

    init {
        bootWorkUseCase.schedulePeriodicBootWork()
    }

    private val _state: Flow<BootListUiState> = bootRepository.getBootsByDayAsFlow()
        .map { bootList -> BootListUiState(boots = bootList) }

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BootListUiState()
    ).asLiveData()
}