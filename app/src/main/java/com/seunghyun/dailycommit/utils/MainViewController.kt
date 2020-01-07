package com.seunghyun.dailycommit.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.work.WorkManager

interface MainViewController {
    fun getWorkManagerInstance(): WorkManager
    fun showToast(@StringRes stringRes: Int, toastLength: Int = Toast.LENGTH_LONG)
}