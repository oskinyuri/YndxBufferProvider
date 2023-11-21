package ru.yandex.bufferprovider.watchtime

interface WatchTimeProvider {
    /**
     * Возвращает время контента просмотренное пользователем
     */
    fun getWatchTime(): Int?
}