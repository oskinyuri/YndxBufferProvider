package ru.yandex.bufferprovider.player

import android.os.Handler
import android.os.Looper

class YandexPlayerImpl : YandexPlayer {

    private var duration = 80L

    private var position: Long = 0
    private val positionChanger = object: Runnable {
        override fun run() {
            if (position < duration) {
                position++
            }
            handler.postDelayed(this, 1000L)
        }
    }
    private val handler = Handler(Looper.getMainLooper())

    fun start() {
        handler.removeCallbacks(positionChanger)
        handler.post(positionChanger)
    }

    fun pause() {
        handler.removeCallbacks(positionChanger)
    }
    fun setPosition(position: Long) {
        this.position = position
    }
    override fun getDuration(): Long {
        return duration
    }

    override fun getPosition(): Long {
        return position
    }
}