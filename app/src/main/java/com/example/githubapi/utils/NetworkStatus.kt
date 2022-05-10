package com.example.githubapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import java.lang.ref.WeakReference

class NetworkStatus(_context: Context) {

    private val context = WeakReference(_context)
    private val connectivityManager = context.get()?.getSystemService<ConnectivityManager>()

    private val networkStatus = MutableLiveData<Boolean>()

    fun networkObserver(): Boolean = networkStatus.value ?: false

    init {
        val request = NetworkRequest.Builder().build()
        connectivityManager?.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    networkStatus.postValue(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    networkStatus.postValue(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    networkStatus.postValue(false)
                }
            }
        )
    }
}
