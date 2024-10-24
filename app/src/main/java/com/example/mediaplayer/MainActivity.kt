package com.example.mediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null
    private var songList = mutableListOf(R.raw.music, R.raw.six, R.raw.five, R.raw.three)
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            currentIndex+=1
        }

        binding.previousButton.setOnClickListener {
            currentIndex-=1
        }

        playSound(songList[currentIndex])

    }

    private fun playSound(song: Int) {
        binding.playButton.setOnClickListener{
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this,song)
                initialiseSeekbar()
            }
            mediaPlayer?.start()
        }
        binding.pauseButton.setOnClickListener {
            if (mediaPlayer != null) mediaPlayer?.pause()
        }
        binding.stopButton.setOnClickListener {
            stopMusic()
        }

        binding.seekbar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) mediaPlayer?.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    private fun stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun initialiseSeekbar() {

        binding.seekbar.max = mediaPlayer!!.duration
        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {

                try {
                    binding.seekbar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this,1000)
                } catch (e: Exception) {
                    binding.seekbar.progress = 0
                }
            }
        }, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}