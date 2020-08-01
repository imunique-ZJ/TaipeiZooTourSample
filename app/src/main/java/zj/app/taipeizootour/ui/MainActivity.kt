package zj.app.taipeizootour.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import zj.app.taipeizootour.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }

}