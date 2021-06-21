package kz.ticketon.test

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var splashScreen: ImageView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.apply {
            hide()
        }
        splashScreen = findViewById<ImageView>(R.id.splashScreen)
        val timer = object : CountDownTimer(2500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                splashScreen.visibility = View.GONE
                supportActionBar?.apply {
                    show()
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame_main,
                        SearchFragment(),
                        SearchFragment::class.java.simpleName
                    )
                    .commitAllowingStateLoss()
            }
        }
        timer.start()
    }
}