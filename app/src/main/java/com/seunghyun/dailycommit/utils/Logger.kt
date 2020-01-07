package com.seunghyun.dailycommit.utils

import android.util.Log

private const val TAG = "DailyCommit"
fun Any?.log() = Log.d(TAG, this.toString())