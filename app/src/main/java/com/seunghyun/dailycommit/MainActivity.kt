package com.seunghyun.dailycommit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.WorkManager
import com.seunghyun.dailycommit.databinding.ActivityMainBinding
import com.seunghyun.dailycommit.utils.MainViewController

class MainActivity : AppCompatActivity(), MainViewController {
    private val viewModel = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            vm = viewModel
        }
    }

    override fun getWorkManagerInstance() = WorkManager.getInstance(this)
    override fun showToast(stringRes: Int, toastLength: Int) = Toast.makeText(this, stringRes, toastLength).show()
}
