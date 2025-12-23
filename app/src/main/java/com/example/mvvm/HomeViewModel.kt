package com.example.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.Row
import com.example.mvvm.domain.LoadHomePageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val loadHomePageUseCase: LoadHomePageUseCase,
) : ViewModel() {
    val homeState: StateFlow<HomeScreenState> =
        loadHomePageUseCase.execute().map { result ->
            when {
                result.isSuccess && result.getOrNull()
                    ?.isNotEmpty() == true -> HomeScreenState.SuccessState(result.getOrNull()!!)

                result.isFailure -> HomeScreenState.ErrorState
                result.isSuccess && result.getOrNull().isNullOrEmpty() -> HomeScreenState.EmptyState
                else -> HomeScreenState.LoadingState
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
