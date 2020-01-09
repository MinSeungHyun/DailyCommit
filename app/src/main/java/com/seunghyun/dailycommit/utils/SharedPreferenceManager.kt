package com.seunghyun.dailycommit.utils

import android.content.Context
import com.seunghyun.dailycommit.MIN_NOTIFICATION_INTERVAL

class SharedPreferenceManager(context: Context) {
    private val preference = context.getSharedPreferences("app_preference", Context.MODE_PRIVATE)
    private val editor = preference.edit()

    fun saveUserName(userName: String) = editor.run {
        putString("userName", userName)
        apply()
    }

    fun loadUserName(): String = preference.getString("userName", "")!!

    fun saveGoalCommit(goalCommit: Int) = editor.run {
        putInt("goalCommit", goalCommit)
        apply()
    }

    fun loadGoalCommit(): Int = preference.getInt("goalCommit", 0)

    fun saveNotificationInterval(interval: Int) = editor.run {
        putInt("notificationInterval", interval)
        apply()
    }

    fun loadNotificationInterval(): Int = preference.getInt("notificationInterval", MIN_NOTIFICATION_INTERVAL)
}