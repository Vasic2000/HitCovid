package ru.vasic2000.hitcovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var score : Int = 0
    var imageArray = ArrayList<ImageView>()
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    fun game(view : View) {
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
            1 -> covidImageName = R.drawable.covid1
            2 -> covidImageName = R.drawable.covid2
            3 -> covidImageName = R.drawable.covid3
            4 -> covidImageName = R.drawable.covid4
            5 -> covidImageName = R.drawable.covid5
            6 -> covidImageName = R.drawable.covid6
            7 -> covidImageName = R.drawable.covid7
            8 -> covidImageName = R.drawable.covid8
            else -> covidImageName = R.drawable.covid9
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
    }
}