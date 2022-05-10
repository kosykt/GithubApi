package com.example.githubapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.lang.ref.WeakReference

fun myNetworkStatus(_context: Context): Flow<Boolean> {
    val context = WeakReference(_context)
    val networkStatus = MutableStateFlow(false)
    val connectivityManager = context.get()?.getSystemService<ConnectivityManager>()
    val request = NetworkRequest.Builder().build()
    connectivityManager?.registerNetworkCallback(
        request,
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkStatus.value = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkStatus.value = false
            }
        })
    return flow { emit(networkStatus.value) }
}
