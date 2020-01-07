package com.seunghyun.dailycommit

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.workDataOf
import com.seunghyun.dailycommit.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

private const val MIN_NOTIFICATION_INTERVAL = 15

class MainViewModel(private val viewController: MainViewController) {
    val userName = ObservableField<String>("")
    val goalCommit = ObservableInt()
    val notificationSeekBarProgress = ObservableInt()
    val notificationInterval = ObservableInt(MIN_NOTIFICATION_INTERVAL)

    init {
        notificationSeekBarProgress.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                notificationInterval.set(notificationSeekBarProgress.get() + MIN_NOTIFICATION_INTERVAL)
            }
        })
    }

    fun onStartButtonClick() = CoroutineScope(Dispatchers.Main).launch {
        val isExistUser = withContext(Dispatchers.IO) {
            try {
                isExistUser(userName.get()!!)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                false
            }
        }
        if (!isExistUser) {
            viewController.showToast(R.string.user_not_found)
            return@launch
        }

        val workRequest = PeriodicWorkRequestBuilder<CheckCommitWorker>(notificationInterval.get().toLong(), TimeUnit.MINUTES)
                .setInputData(workDataOf(USER_NAME_KEY to userName.get(), GOAL_COMMIT_KEY to goalCommit.get()))
                .build()
        viewController.getWorkManagerInstance()
                .enqueueUniquePeriodicWork(CHECK_COMMIT_WORK_NAME, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

        viewController.showToast(R.string.service_started)
    }
}