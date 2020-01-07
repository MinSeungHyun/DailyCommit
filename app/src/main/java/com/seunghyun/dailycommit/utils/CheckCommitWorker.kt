package com.seunghyun.dailycommit.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.seunghyun.dailycommit.R

const val CHECK_COMMIT_WORK_NAME = "CHECK_COMMIT_WORK"
const val USER_NAME_KEY = "userName"
const val GOAL_COMMIT_KEY = "goalCommit"

class CheckCommitWorker(private val context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    private val github = Github(workerParameters.inputData.getString(USER_NAME_KEY)!!)
    private val goalCommit = workerParameters.inputData.getInt(GOAL_COMMIT_KEY, 1)

    override fun doWork(): Result {
        val todayCommit = github.getTodayCommitCount()
        todayCommit.log()
        if (todayCommit >= goalCommit) return Result.success()
        pushNotification(todayCommit)
        return Result.success()
    }

    private fun pushNotification(commitCount: Int) {
        val title = context.getString(R.string.commit_notification_title)
        val content = context.resources.getQuantityString(R.plurals.commit_notification_content, commitCount, commitCount, goalCommit)
        NotificationUtils(context).pushNotification(title, content)
    }
}