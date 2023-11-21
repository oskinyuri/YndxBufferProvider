package ru.yandex.bufferprovider.buffer

import ru.yandex.bufferprovider.network.NetworkType
import ru.yandex.bufferprovider.network.NetworkTypeProvider
import ru.yandex.bufferprovider.player.YandexPlayer
import ru.yandex.bufferprovider.watchtime.WatchTimeProvider

class TargetBufferDurationProviderImpl(
    private val watchTimeProvider: WatchTimeProvider,
    private val networkTypeProvider: NetworkTypeProvider,
    private val yandexPlayer: YandexPlayer,
) : TargetBufferDurationProvider {
    override fun getTargetBuffer(): Long {
        val watchTime = watchTimeProvider.getWatchTime()
        val networkType = networkTypeProvider.getNetworkType()
        val duration = yandexPlayer.getDuration()
        val position = yandexPlayer.getPosition()
        val remainDuration = duration - position

        if (watchTime == null || watchTime == 0) return 0
        var targetBuffer: Long

        targetBuffer = when (networkType) {
            NetworkType.WIFI -> WIFI_TARGET_BUFFER
            NetworkType.CIRCULAR -> CIRCULAR_TARGET_BUFFER
        }

        if (watchTime <= MEDIUM_TIME_WATCH) {
            targetBuffer = MEDIUM_TIME_WATCH_TARGET_BUFFER
        }

        if (watchTime <= SMALL_TIME_WATCH) {
            targetBuffer = SMALL_TIME_WATCH_TARGET_BUFFER
        }

        return maxOf(remainDuration, targetBuffer)
    }

    private companion object {
        const val MEDIUM_TIME_WATCH = 10
        const val MEDIUM_TIME_WATCH_TARGET_BUFFER = 15L
        const val SMALL_TIME_WATCH = 5
        const val SMALL_TIME_WATCH_TARGET_BUFFER = 10L
        const val WIFI_TARGET_BUFFER = 50L
        const val CIRCULAR_TARGET_BUFFER = 30L
    }
}