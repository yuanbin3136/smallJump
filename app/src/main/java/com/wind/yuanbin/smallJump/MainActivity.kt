package com.wind.yuanbin.smallJump

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!MyAccessibilityService.isRunning(this)) {
            toSetting()
        }
    }

    private fun toSetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        Toast.makeText(this, "请打开无障碍服务", Toast.LENGTH_LONG).show()
    }
}
