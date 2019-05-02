package com.example.timer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.os.Handler

class MainActivity : AppCompatActivity() {

    val handler = Handler()

    companion object {
        // 定数
        private const val TIME_0   : String = "00:00:00"
        private const val TIME_30S : String = "00:00:30"
        private const val TIME_1M  : String = "00:01:00"
        private const val TIME_3M  : String = "00:03:00"
    }

    // 変数
    private var time    : String = TIME_0
    private var nowTime : String = TIME_0
    private var seconds : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // テキストビュー(タイマーの時間を表示する領域)
        val textView = findViewById<TextView>(R.id.textView)

        val runnable = object : Runnable {
            override fun run() {

                seconds -= 1

                if (seconds > 0) {
                    textView.text = convertToTimeFormat(seconds)
                    handler.postDelayed(this, 1000)
                } else {
                    textView.text = TIME_0
                    handler.removeCallbacks(this)
                }
            }
        }

        // スタートボタン
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            handler.post(runnable)
        }

        // リセットボタン
        val resetButton = findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            nowTime = TIME_0
            textView.text = TIME_0
        }

        // 30秒タイマーセットボタン
        val time30sButton = findViewById<Button>(R.id.time30sButton)
        time30sButton.setOnClickListener {
            time          = TIME_30S
            seconds       = 30
            textView.text = time
        }

        // 1分タイマーセットボタン
        val time1mButton = findViewById<Button>(R.id.time1mButton)
        time1mButton.setOnClickListener {
            time          = TIME_1M
            seconds       = 60 // 1分
            textView.text = time
        }

        // 3分タイマーセットボタン
        val time3mButton = findViewById<Button>(R.id.time3mButton)
        time3mButton.setOnClickListener {
            time          = TIME_3M
            seconds       = 180 // 3分
            textView.text = time
        }
    }

    /*
     * 時間形式(HH:MM:SS)に変換する
     */
    fun convertToTimeFormat(seconds : Int): String {
        val h =   seconds / 3600
        val m = ( seconds % 3600) / 60
        val s = ((seconds % 3600) % 60)

        return String.format("%02d:%02d:%02d", h, m, s)
    }
}
