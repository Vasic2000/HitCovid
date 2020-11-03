package ru.vasic2000.hitcovid

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.System.exit
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var score : Int = 0
    var imageArray = ArrayList<ImageView>()
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable {}

    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    lateinit var sounds : SoundPool

    var aaa : Int = 0
    var hit1 : Int = 0
    var hit2 : Int = 0
    var hit3 : Int = 0
    var hit4 : Int = 0
    var hit5 : Int = 0
    var hit6 : Int = 0
    var hit7 : Int = 0
    var hit8 : Int = 0
    var hit9 : Int = 0

    var covidKillSound : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volumeControlStream = AudioManager.STREAM_MUSIC

        startMenu()
    }

    private fun startMenu() {
        initialize()

        for(i in 0 until imageArray.size) {
            imageArray[i].visibility = View.INVISIBLE
        }

        startButton.visibility = View.VISIBLE
        replayButton.visibility = View.INVISIBLE
        exitButton.visibility = View.INVISIBLE
        scoreText.visibility = View.INVISIBLE
        timeText.visibility = View.INVISIBLE
        Thread.sleep(1000)
    }

    fun game(view: View) {
        startButton.visibility = View.INVISIBLE
        replayButton.visibility = View.INVISIBLE
        exitButton.visibility = View.INVISIBLE
        scoreText.visibility = View.VISIBLE
        timeText.visibility = View.VISIBLE

        background.visibility = View.INVISIBLE

        initImages()

        object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeText.text = getString(R.string.timeWord) + millisUntilFinished/1000
            }

            override fun onFinish() {
                timeText.text = getString(R.string.endOfTime)
                handler.removeCallbacks(runnable)
                replayOrExit()
            }
        }.start()

        hideImages()
    }

    private fun replayOrExit() {
        score = 0
        for(i in 0 until imageArray.size) {
            imageArray[i].visibility = View.INVISIBLE
        }
        startButton.visibility = View.INVISIBLE
        replayButton.visibility = View.VISIBLE
        exitButton.visibility = View.VISIBLE
    }

    private fun initialize() {
        score = 0

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sounds = SoundPool.Builder()
                .setMaxStreams(2)
                .build()
        } else {
            sounds = SoundPool(2, AudioManager.STREAM_MUSIC, 1)
        }

        aaa = sounds.load(applicationContext, R.raw.aaa, 1)

        hit1 = sounds.load(applicationContext, R.raw.ay, 1)
        hit2 = sounds.load(applicationContext, R.raw.oi3, 1)
        hit3 = sounds.load(applicationContext, R.raw.hu, 1)
        hit4 = sounds.load(applicationContext, R.raw.ao, 1)
        hit5 = sounds.load(applicationContext, R.raw.aaa, 1)
        hit6 = sounds.load(applicationContext, R.raw.otr, 1)
        hit7 = sounds.load(applicationContext, R.raw.ao, 1)
        hit8 = sounds.load(applicationContext, R.raw.puk, 1)
        hit9 = sounds.load(applicationContext, R.raw.o_o, 1)

        imageArray = arrayListOf(
            imageView1, imageView2, imageView3, imageView4,
            imageView5, imageView6, imageView7, imageView8,
            imageView9, imageView10, imageView11, imageView12,
            imageView13, imageView14, imageView15, imageView16
        )
    }

    private fun initImages() {
        val rnd = Random()
        val covidPicIndex = rnd.nextInt(9)
        var covidImageName : Int

        when (covidPicIndex) {
            1 -> {
                covidImageName = R.drawable.covid1
                covidKillSound = hit1
            }
            2 -> {
                covidImageName = R.drawable.covid2
                covidKillSound = hit2
            }
            3 -> {
                covidImageName = R.drawable.covid3
                covidKillSound = hit3
            }
            4 -> {
                covidImageName = R.drawable.covid4
                covidKillSound = hit4
            }
            5 -> {
                covidImageName = R.drawable.covid5
                covidKillSound = hit5
            }
            6 -> {
                covidImageName = R.drawable.covid6
                covidKillSound = hit6
            }
            7 -> {
                covidImageName = R.drawable.covid7
                covidKillSound = hit7
            }
            8 -> {
                covidImageName = R.drawable.covid8
                covidKillSound = hit8
            }
            else -> {
                covidImageName = R.drawable.covid9
                covidKillSound = hit9
            }
        }

        for(i in 0 until imageArray.size) {
            imageArray[i].setImageResource(covidImageName)
        }
    }

    fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                val rnd = Random()
                val indexVisible = rnd.nextInt(15 - 0)
                for(i in 0 until imageArray.size) {
                    imageArray[i].visibility = View.INVISIBLE
                }
                imageArray[indexVisible].visibility = View.VISIBLE
                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)
    }

    fun increaseScore(view: View) {
        score++
        scoreText.text = "Score: " + score
        sounds.play(covidKillSound, 1f, 1f, 0, 0, 0f)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            return
        } else {
            backToast = Toast.makeText(this, "Press Back to exit again", Toast.LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    fun exitMain(view: View) {
        exit(1)
    }
}