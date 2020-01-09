package com.seunghyun.dailycommit.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.seunghyun.dailycommit.R
import java.net.SocketTimeoutException

const val CHECK_COMMIT_WORK_NAME = "CHECK_COMMIT_WORK"
const val USER_NAME_KEY = "userName"
const val GOAL_COMMIT_KEY = "goalCommit"

class CheckCommitWorker(private val context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    private val userName = workerParameters.inputData.getString(USER_NAME_KEY)!!
    private val github = Github(userName)
    private val goalCommit = workerParameters.inputData.getInt(GOAL_COMMIT_KEY, 1)

    override fun doWork(): Result {
        val todayCommit = try {
            github.getTodayCommitCount()
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            return Result.failure()
        }
        todayCommit.log()
        if (todayCommit >= goalCommit) return Result.success()
        pushNotification(todayCommit)
        return Result.success()
    }

    private fun pushNotification(commitCount: Int) {
        val content = if (commitCount == 0)
            context.getString(R.string.commit_notification_content_zero).format(goalCommit)
        else context.resources.getQuantityString(R.plurals.commit_notification_content, commitCount, commitCount, goalCommit)
        NotificationUtils(context).pushCommitNotification(content, userName)
    }
}