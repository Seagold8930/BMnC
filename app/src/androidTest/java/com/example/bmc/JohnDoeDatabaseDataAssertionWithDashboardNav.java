package com.example.bmc;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.bmc.auxiliary.User;
import com.example.bmc.db.DB_Handler;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class JohnDoeDatabaseDataAssertionWithDashboardNav {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void johnDoeDatabaseDataAssertion() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction checkBox = onView(
                allOf(withId(R.id.remember_me),
                        childAtPosition(
                                allOf(withId(R.id.credentials_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                2),
                        isDisplayed()));
        checkBox.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.sign_in_button),
                        childAtPosition(
                                allOf(withId(R.id.credentials_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                3),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        appCompatAutoCompleteTextView.perform(scrollTo(), typeText("john.doe001"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), typeText("John.Doe001"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_in_button), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.credentials_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_one),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), typeText("Wellington2019"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password_two),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText3.perform(scrollTo(), typeText("Wellington2019"), closeSoftKeyboard());

        appCompatButton = onView(
                allOf(withId(R.id.change_password_button), withText("Change Password"),
                        childAtPosition(
                                allOf(withId(R.id.credentials_change_password_form),
                                        childAtPosition(
                                                withId(R.id.change_password_form),
                                                0)),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 1 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("BM&C Test Building 1 Name")));

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 3 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("BM&C Test Building 3 Name")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 5 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("BM&C Test Building 5 Name")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 7 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                3),
                        isDisplayed()));
        textView4.check(matches(withText("BM&C Test Building 7 Name")));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 9 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                4),
                        isDisplayed()));
        textView5.check(matches(withText("BM&C Test Building 9 Name")));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_building_names),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.my_app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("BM&C Test Building 1 Name")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("BM&C Test Building 1 Address")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("BM&C Test Building 1 Location")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.contents), withText("1990"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView14.check(matches(withText("1990")));

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Dashboard"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //BUILDING 3
        appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_building_names),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(1);
        appCompatTextView.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.my_app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        imageView2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        textView11 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 3 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("BM&C Test Building 3 Name")));

        textView12 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 3 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("BM&C Test Building 3 Address")));

        textView13 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 3 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("BM&C Test Building 3 Location")));

        textView14 = onView(
                allOf(withId(R.id.contents), withText("1992"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView14.check(matches(withText("1992")));

        imageButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Dashboard"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //BUILDING 5
        appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_building_names),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(2);
        appCompatTextView.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.my_app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        imageView2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        textView11 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 5 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("BM&C Test Building 5 Name")));

        textView12 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 5 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("BM&C Test Building 5 Address")));

        textView13 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 5 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("BM&C Test Building 5 Location")));

        textView14 = onView(
                allOf(withId(R.id.contents), withText("1994"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView14.check(matches(withText("1994")));

        imageButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Dashboard"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //BUILDING 7
        appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_building_names),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(3);
        appCompatTextView.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.my_app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        imageView2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        textView11 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 7 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("BM&C Test Building 7 Name")));

        textView12 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 7 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("BM&C Test Building 7 Address")));

        textView13 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 7 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("BM&C Test Building 7 Location")));

        textView14 = onView(
                allOf(withId(R.id.contents), withText("1996"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView14.check(matches(withText("1996")));

        imageButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Dashboard"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //BUILDING 9
        appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.lv_building_names),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(4);
        appCompatTextView.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.my_app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        imageView2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        textView11 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 9 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("BM&C Test Building 9 Name")));

        textView12 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 9 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("BM&C Test Building 9 Address")));

        textView13 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 9 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("BM&C Test Building 9 Location")));

        textView14 = onView(
                allOf(withId(R.id.contents), withText("1998"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView14.check(matches(withText("1998")));

        imageButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Dashboard"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.title), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        Assert.assertTrue( new DB_Handler().updatePassword( "John.Doe001", new User( "John Doe", "John.Doe001" ) ) );

        checkBox = onView(
                allOf(withId(R.id.remember_me),
                        childAtPosition(
                                allOf(withId(R.id.credentials_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                2),
                        isDisplayed()));
        checkBox.perform(click());

        appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("John.Doe001"), closeSoftKeyboard());

        appCompatButton = onView(
                allOf(withId(R.id.sign_in_button), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.credentials_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
