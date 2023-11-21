package ru.yandex.bufferprovider

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import ru.yandex.bufferprovider.buffer.TargetBufferDurationProviderImpl
import ru.yandex.bufferprovider.network.NetworkTypeProviderImpl
import ru.yandex.bufferprovider.player.YandexPlayerImpl
import ru.yandex.bufferprovider.watchtime.WatchTimeProviderImpl

class MainActivity : AppCompatActivity() {

    private var targetBuffer: TextView? = null
    private var position: TextView? = null
    private var watchTime: TextView? = null
    private var networkType: TextView? = null
    private var duration: TextView? = null
    private var startButton: Button? = null
    private var pauseButton: Button? = null

    private val handler = Handler(Looper.getMainLooper())

    private val player = YandexPlayerImpl()
    private val watchTimeProvider = WatchTimeProviderImpl()
    private val networkTypeProvider = NetworkTypeProviderImpl()
    private val targetBufferDurationProvider = TargetBufferDurationProviderImpl(
        watchTimeProvider, networkTypeProvider, player
    )
    private val updateInfo = object: Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            networkType?.text = "Network type: ${networkTypeProvider.getNetworkType().name}"
            watchTime?.text = "Watched time sec: ${ watchTimeProvider.getWatchTime().toString()}"
            targetBuffer?.text = "Target buffer sec: ${targetBufferDurationProvider.getTargetBuffer()}"
            position?.text = "Position sec: ${player.getPosition()}"
            duration?.text = "Duration sec: ${player.getDuration()}"
            handler.postDelayed(this, 1000L)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        targetBuffer = findViewById(R.id.targetBuffer)
        position = findViewById(R.id.position)
        duration = findViewById(R.id.duration)
        watchTime = findViewById(R.id.watchTime)
        networkType = findViewById(R.id.networkType)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)

        startButton?.setOnClickListener {
            player.start()
            watchTimeProvider.start()
        }

        pauseButton?.setOnClickListener {
            player.pause()
            watchTimeProvider.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        handler.post(updateInfo)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateInfo)
    }
}