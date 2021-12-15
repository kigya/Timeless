package com.teachmeskills.timeless

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton as MaterialButton1

class MainActivity : AppCompatActivity() {

    private var handlerAnimation = Handler(Looper.getMainLooper())
    private lateinit var timer: Chronometer
    private lateinit var actionButton: MaterialButton1
    private lateinit var stopState: AppCompatImageView
    private lateinit var workingState: AppCompatImageView
    private lateinit var finishButton: MaterialButton1
    private lateinit var quitButton: MaterialButton1
    private lateinit var imgAnimation1: ImageView
    private lateinit var imgAnimation2: ImageView
    private var pauseAt: Long = 0
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeless)

        timer = findViewById(R.id.timer)
        actionButton = findViewById(R.id.stateButton)
        stopState = findViewById(R.id.stop)
        workingState = findViewById(R.id.work)
        finishButton = findViewById(R.id.finish)
        quitButton = findViewById(R.id.quit)
        imgAnimation1 = findViewById(R.id.imgAnimation1)
        imgAnimation2 = findViewById(R.id.imgAnimation2)

        actionButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimerUI()
                timer.base = SystemClock.elapsedRealtime() - pauseAt
                hideStopState()
                showWorkState()
                startPulse()
                showAnimation()
            } else {
                stopTimerUI()
                pauseAt = SystemClock.elapsedRealtime() - timer.base
                hideWorkState()
                showStopState()
                stopPulse()
                hideAnimation()
            }
        }

        finishButton.setOnClickListener {
            stopTimerUI()
            timer.base = SystemClock.elapsedRealtime()
            pauseAt = 0
            hideWorkState()
            hideStopState()
            stopPulse()
            hideAnimation()
        }

        quitButton.setOnClickListener {
            this.finishAffinity()
        }
    }

    private fun stopTimerUI() {
        timer.stop()
        actionButton.setIconResource(R.drawable.ic_play_button)
        isTimerRunning = false
    }

    private fun startTimerUI() {
        timer.start()
        actionButton.setIconResource(R.drawable.ic_stop_button)
        isTimerRunning = true

    }

    private fun hideStopState() {
        stopState.isVisible = false
    }

    private fun hideWorkState() {
        workingState.isVisible = false
    }

    private fun showStopState() {
        stopState.isVisible = true
    }

    private fun showWorkState() {
        workingState.isVisible = true
    }

    private fun showAnimation() {
        imgAnimation1.isVisible = true
        imgAnimation2.isVisible = true
    }

    private fun hideAnimation() {
        imgAnimation1.isVisible = false
        imgAnimation2.isVisible = false
    }

    private fun startPulse() {
        runnable.run()
    }

    private fun stopPulse() {
        handlerAnimation.removeCallbacks(runnable)
    }

    private var runnable = object : Runnable {
        override fun run() {
            imgAnimation1.animate().scaleX(6f).scaleY(4f).alpha(0f).setDuration(1000)
                    .withEndAction {
                        imgAnimation1.scaleX = 0f
                        imgAnimation1.scaleY = 1f
                        imgAnimation1.alpha = 1f
                    }

            imgAnimation2.animate().scaleX(6f).scaleY(4f).alpha(0f).setDuration(500)
                    .withEndAction {
                        imgAnimation2.scaleX = 0f
                        imgAnimation2.scaleY = 1f
                        imgAnimation2.alpha = 1f
                    }

            handlerAnimation.postDelayed(this, 1500)
        }
    }
}