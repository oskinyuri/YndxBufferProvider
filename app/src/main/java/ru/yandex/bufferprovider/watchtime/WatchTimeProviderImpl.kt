package ru.yandex.bufferprovider.watchtime

import android.os.Handler
import android.os.Looper

class WatchTimeProviderImpl : WatchTimeProvider {

    private val handler = Handler(Looper.getMainLooper())
    private var watchedTime: Int = 0
    private val timer = object: Runnable {
        override fun run() {
            watchedTime++
            handler.postDelayed(this, 1000L)
        }
    }

    fun start() {
        handler.removeCallbacks(timer)
        handler.post(timer)
    }

    fun pause() {
        handler.removeCallbacks(timer)
    }

    override fun getWatchTime(): Int? {
        return watchedTime
    }
}