package com.seunghyun.dailycommit.utils

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "https://github.com/"

@WorkerThread
class Github(private val userName: String) {
    fun getTodayCommitCount(): Int {
        val element = getProfilePage().select("rect").last()
        val dateString = element.attr("data-date")
        if (!isToday(dateString)) return 0
        return element.attr("data-count").toInt()
    }

    @SuppressLint("SimpleDateFormat")
    internal fun isToday(dateString: String, dateFormat: String = "yyyy-MM-dd"): Boolean {
        val today = Calendar.getInstance()
        val anotherDay = Calendar.getInstance().apply { time = SimpleDateFormat(dateFormat).parse(dateString)!! }
        return today.get(Calendar.DAY_OF_YEAR) == anotherDay.get(Calendar.DAY_OF_YEAR)
    }

    private fun getProfilePage(): Document = Jsoup.connect("$BASE_URL$userName").get()
}