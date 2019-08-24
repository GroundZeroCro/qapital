package com.groundzero.qapital

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.groundzero.qapital.base.MainActivity
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityAndroidTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

}