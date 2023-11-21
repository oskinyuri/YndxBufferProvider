package ru.yandex.bufferprovider.network

import android.os.Handler
import android.os.Looper

class NetworkTypeProviderImpl : NetworkTypeProvider {

    private val handler = Handler(Looper.getMainLooper())
    private var networkType: NetworkType = NetworkType.WIFI
    private val updateTask = object: Runnable {
        override fun run() {
            networkType = networks.random()
            handler.postDelayed(this, 1000L)
        }
    }
    init {
        handler.post(updateTask)
    }

    private val networks = listOf(
        NetworkType.WIFI,
        NetworkType.WIFI,
        NetworkType.CIRCULAR,
        NetworkType.WIFI,
        NetworkType.CIRCULAR,
    )
    override fun getNetworkType(): NetworkType {
        return networkType
    }
}