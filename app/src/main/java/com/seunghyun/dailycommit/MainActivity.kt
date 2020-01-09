package com.seunghyun.dailycommit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.WorkManager
import com.seunghyun.dailycommit.databinding.ActivityMainBinding
import com.seunghyun.dailycommit.utils.MainViewController
import com.seunghyun.dailycommit.utils.SharedPreferenceManager

class MainActivity : AppCompatActivity(), MainViewController {
    private val preferenceManager by lazy { SharedPreferenceManager(this) }
    private val viewModel by lazy { MainViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            vm = viewModel
        }
    }

    override fun getWorkManagerInstance() = WorkManager.getInstance(this)
    override fun showToast(stringRes: Int, toastLength: Int) = Toast.makeText(this, stringRes, toastLength).show()

    override fun saveUserName(userName: String) = preferenceManager.saveUserName(userName)
    override fun getUserName() = preferenceManager.loadUserName()
    override fun saveGoalCommit(goalCommit: Int) = preferenceManager.saveGoalCommit(goalCommit)
    override fun loadGoalCommit() = preferenceManager.loadGoalCommit()
    override fun saveNotificationInterval(interval: Int) = preferenceManager.saveNotificationInterval(interval)
    override fun loadNotificationInterval() = preferenceManager.loadNotificationInterval()
}
