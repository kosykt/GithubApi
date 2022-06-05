package com.example.githubapi.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.utils.AppState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val mutableStateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow = mutableStateFlow.asStateFlow()

    protected fun tryLaunch(
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit,
    ): LaunchBuilder = LaunchBuilder(scope, block)

    protected inner class LaunchBuilder(
        private val scope: CoroutineScope,
        private val block: suspend CoroutineScope.() -> Any,
    ) {
        fun start(dispatcher: CoroutineDispatcher): Job = scope.launch(dispatcher) {
            try {
                coroutineScope(block)
            } catch (t: Throwable) {
                errorResponse(t)
            }
        }
    }

    protected fun errorResponse(it: Throwable) {
        mutableStateFlow.value = AppState.Error(it)
        Log.e(this::class.java.simpleName, it.message.toString())
    }
}