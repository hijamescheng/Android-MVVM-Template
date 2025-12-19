package com.example.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeModel
@Inject
constructor() : ViewModel() {
    private val _uistate = MutableStateFlow<Int>(0)
    val uistate =
        _uistate.map {
            Repository.fetchNumber()
        }.flowOn(Dispatchers.IO).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = 0,
        )

    fun loadNumber() {
        viewModelScope.launch {
            _uistate.emit((1..100).random())
        }
    }
}
