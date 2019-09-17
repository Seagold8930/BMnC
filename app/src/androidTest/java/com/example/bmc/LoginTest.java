package com.example.bmc;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginTest {

    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule = new IntentsTestRule<>(LoginActivity.class, true, false);

}
