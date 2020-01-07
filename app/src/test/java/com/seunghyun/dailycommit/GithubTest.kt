package com.seunghyun.dailycommit

import com.seunghyun.dailycommit.utils.Github
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GithubTest {
    private val github = Github("MinSeungHyun")

    @Test
    fun isTodayTrue() {
        val today = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        assertTrue(github.isToday(today))
    }

    @Test
    fun isTodayFalse() {
        assertFalse(github.isToday("1234-12-12"))
    }

    @Test
    fun isTodayError() {
        assertThrows(ParseException::class.java) {
            github.isToday("")
        }
    }

    @Test
    fun getTodayCommitCountTest() {
        assertDoesNotThrow {
            github.getTodayCommitCount()
        }
    }

    @Test
    fun getTodayCommitCountUserNotFound() {
        assertThrows(NullPointerException::class.java) {
            Github("").getTodayCommitCount()
        }
    }
}