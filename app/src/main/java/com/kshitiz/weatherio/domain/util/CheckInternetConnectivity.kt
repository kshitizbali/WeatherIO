package com.kshitiz.weatherio.domain.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun CheckInternetConnectivity() {
    val isConnected = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    val networkCapabilities = connectivityManager.activeNetwork ?: return

    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return

    isConnected.value = when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}