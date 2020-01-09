package com.seunghyun.dailycommit.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.work.WorkManager

interface MainViewController {
    fun getWorkManagerInstance(): WorkManager
    fun showToast(@StringRes stringRes: Int, toastLength: Int = Toast.LENGTH_LONG)

    fun saveUserName(userName: String)
    fun getUserName(): String
    fun saveGoalCommit(goalCommit: Int)
    fun loadGoalCommit(): Int
    fun saveNotificationInterval(interval: Int)
    fun loadNotificationInterval(): Int
}