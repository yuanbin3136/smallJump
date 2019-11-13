package com.wind.yuanbin.smallJump

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.accessibilityservice.GestureDescription
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Path
import android.os.Build
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.*

class MyAccessibilityService : AccessibilityService() {
    internal val TAG = this.toString()

    private var keyWords = arrayOf("跳过", "skip","Hello")
    private var keyWordList: ArrayList<String>? = ArrayList()
    override fun onCreate() {
        L.o("oncreate")
        keyWordList?.add("")
        keyWordList?.add("Hello")
        keyWordList?.addAll(listOf(*keyWords))
        super.onCreate()
    }
    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var asi: AccessibilityServiceInfo? = null

    override fun onServiceConnected() {
        L.o("onServiceConnected")
        asi = serviceInfo
        asi?.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        serviceInfo = asi
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        L.o("onAccessibilityEvent" + event.eventType)
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                //
                var packageName = event.packageName

                var className = event.className

                L.o("$packageName className:$className")
                val root = rootInActiveWindow
                findSkipButtonByText(root)
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                var packageName = event.packageName

                var className = event.className

                L.o("TYPE_WINDOW_CONTENT_CHANGED $packageName className:$className")
                val source = event.source
                findSkipButtonByText(source)
            }
        }
    }

    /**
     * 自动查找启动广告的
     * “跳过”的控件
     */
    private fun findSkipButtonByText(nodeInfo: AccessibilityNodeInfo?) {
        if (nodeInfo == null) return
        for (s in keyWordList!!) {
            val list = nodeInfo.findAccessibilityNodeInfosByText(s)
            //TO DO
            if (list.isNotEmpty()) {
                for (e in list) {
                    L.o("find:" + e.text)
                    TODO("处理点击事件")
//                    if (!e.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
//                        if (!e.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
//                            val rect = Rect()
//                            e.getBoundsInScreen(rect)
//                            //                            click(rect.centerX(), rect.centerY(), 0, 20);
//                        }
//                    }
                }
                return
            }

        }
    }

    /**
     * 模拟点击
     */
    private fun click(X: Int, Y: Int, start_time: Long, duration: Long): Boolean {
        val path = Path()
        path.moveTo(X.toFloat(), Y.toFloat())
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, start_time, duration))
            dispatchGesture(builder.build(), null, null)
        } else {
            false
        }
    }

    companion object {
        @Suppress("DEPRECATION")
        fun isRunning(context: Context): Boolean {
            val name = MyAccessibilityService::class.java.name
            if (TextUtils.isEmpty(name)) {
                return false
            }
            val myManager = context
                    .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningService = myManager
                    .getRunningServices(Int.MAX_VALUE) as ArrayList<ActivityManager.RunningServiceInfo>
            myManager.runningAppProcesses[0].processName
            for (i in runningService.indices) {
                if (runningService[i].service.className == name) {
                    return true
                }
            }
            return false
        }
    }

    override fun onUnbind(intent: Intent): Boolean {
        L.o("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        L.o("onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }
    internal val PACKAGESYS = "com.android.systemui"
}