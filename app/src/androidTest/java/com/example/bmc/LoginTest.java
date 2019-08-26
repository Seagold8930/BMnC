package com.example.bmc;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginTest {

    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule = new IntentsTestRule<>(LoginActivity.class, true, false);

}
