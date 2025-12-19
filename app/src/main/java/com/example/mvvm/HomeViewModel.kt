package com.example.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.MovieRepository
import com.example.mvvm.data.Row
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        val repository: MovieRepository,
    ) : ViewModel() {
        val homeState: StateFlow<HomeScreenState> =
            repository.observeHomePage().map { result ->
                when {
                    result.isSuccess && result.getOrNull() != null -> HomeScreenState.SuccessState(result.getOrNull()!!)
                    result.isFailure -> HomeScreenState.ErrorState
                    else -> HomeScreenState.EmptyState
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeScreenState.LoadingState,
            )

        sealed class HomeScreenState {
            object LoadingState : HomeScreenState()

            object EmptyState : HomeScreenState()

            object ErrorState : HomeScreenState()

            class SuccessState(
                val homeList: List<Row>,
            ) : HomeScreenState()
        }
    }
