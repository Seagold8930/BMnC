package com.example.bmc;


import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
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
public class UploadComplianceDataWithErrorMessages {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void uploadComplianceDataWithErrorMessages() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        appCompatAutoCompleteTextView.perform(scrollTo(), typeText("jane.smith001"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), typeText("MyPass1000"), closeSoftKeyboard());

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
            Thread.sleep(1000);
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
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 2 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("BM&C Test Building 2 Name")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 3 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("BM&C Test Building 3 Name")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 4 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                3),
                        isDisplayed()));
        textView4.check(matches(withText("BM&C Test Building 4 Name")));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 5 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                4),
                        isDisplayed()));
        textView5.check(matches(withText("BM&C Test Building 5 Name")));

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 6 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                5),
                        isDisplayed()));
        textView6.check(matches(withText("BM&C Test Building 6 Name")));

        ViewInteraction textView7 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 7 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                6),
                        isDisplayed()));
        textView7.check(matches(withText("BM&C Test Building 7 Name")));

        ViewInteraction textView8 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 8 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                7),
                        isDisplayed()));
        textView8.check(matches(withText("BM&C Test Building 8 Name")));

        ViewInteraction textView9 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 9 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                8),
                        isDisplayed()));
        textView9.check(matches(withText("BM&C Test Building 9 Name")));

        ViewInteraction textView10 = onView(
                allOf(withId(android.R.id.text1), withText("BM&C Test Building 10 Name"),
                        childAtPosition(
                                allOf(withId(R.id.lv_building_names),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                9),
                        isDisplayed()));
        textView10.check(matches(withText("BM&C Test Building 10 Name")));

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
            Thread.sleep(1000);
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
                allOf(withId(R.id.list_title), withText("Building Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("Building Name")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("BM&C Test Building 1 Name")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.list_title), withText("Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                0),
                        isDisplayed()));
        textView13.check(matches(withText("Address")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView14.check(matches(withText("BM&C Test Building 1 Address")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.list_title), withText("Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                0),
                        isDisplayed()));
        textView15.check(matches(withText("Location")));

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView16.check(matches(withText("BM&C Test Building 1 Location")));

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.list_title), withText("Year Built"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                0),
                        isDisplayed()));
        textView17.check(matches(withText("Year Built")));

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.contents), withText("1990"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView18.check(matches(withText("1990")));

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton4.check(matches(isDisplayed()));

        ViewInteraction imageView3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction editText0 = onView(
                allOf(withId(R.id.date),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.date_input),
                                        0),
                                0),
                        isDisplayed()));
        editText0.check(matches(isDisplayed()));

        editText2 = onView(
                allOf(withId(R.id.finding),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.finding_input),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.description_input),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.compliance_image),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                5),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction imageButton5 = onView(
                allOf(withId(R.id.cancel_button),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                6),
                        isDisplayed()));
        imageButton5.check(matches(isDisplayed()));

        ViewInteraction imageButton6 = onView(
                allOf(withId(R.id.submit_button),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                7),
                        isDisplayed()));
        imageButton6.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.submit_button),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                7)));
        floatingActionButton2.perform(scrollTo(), click());

        onView(withId(R.id.finding)).check(matches(hasErrorText( "This field is required" )));

        appCompatEditText = onView(
                allOf(withId(R.id.finding),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.finding_input),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), typeText("Test finding API " + Build.VERSION.SDK_INT), closeSoftKeyboard());


        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.submit_button),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                7)));
        floatingActionButton3.perform(scrollTo(), click());

        onView(withId(R.id.description)).check(matches(hasErrorText( "This field is required" )));


        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.description_input),
                                        0),
                                0)));

        appCompatEditText2.perform(scrollTo(), typeText("Test description API " + Build.VERSION.SDK_INT), closeSoftKeyboard());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.submit_button),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                7)));
        floatingActionButton4.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.status),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                3)));
        appCompatSpinner.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ( Build.VERSION.SDK_INT >= 23 ) {
            DataInteraction appCompatTextView2 = onData(anything())
                    .inAdapterView(childAtPosition(
                            withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                            0))
                    .atPosition(1);
            appCompatTextView2.perform(click());
        } else {
            onView(withText("Open"))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(click());
        }


        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.submit_button),
                        childAtPosition(
                                allOf(withId(R.id.compliance_inspection_form),
                                        childAtPosition(
                                                withId(R.id.compliance_form),
                                                0)),
                                7)));
        floatingActionButton5.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton7 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.my_app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton7.check(matches(isDisplayed()));

        ViewInteraction imageView5 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed()));

        ViewInteraction textView19 = onView(
                allOf(withId(R.id.list_title), withText("Building Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                0),
                        isDisplayed()));
        textView19.check(matches(withText("Building Name")));

        ViewInteraction textView20 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        0),
                                1),
                        isDisplayed()));
        textView20.check(matches(withText("BM&C Test Building 1 Name")));

        ViewInteraction textView21 = onView(
                allOf(withId(R.id.list_title), withText("Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                0),
                        isDisplayed()));
        textView21.check(matches(withText("Address")));

        ViewInteraction textView22 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        1),
                                1),
                        isDisplayed()));
        textView22.check(matches(withText("BM&C Test Building 1 Address")));

        ViewInteraction textView23 = onView(
                allOf(withId(R.id.list_title), withText("Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                0),
                        isDisplayed()));
        textView23.check(matches(withText("Location")));

        ViewInteraction textView24 = onView(
                allOf(withId(R.id.contents), withText("BM&C Test Building 1 Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        2),
                                1),
                        isDisplayed()));
        textView24.check(matches(withText("BM&C Test Building 1 Location")));

        ViewInteraction textView25 = onView(
                allOf(withId(R.id.list_title), withText("Year Built"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                0),
                        isDisplayed()));
        textView25.check(matches(withText("Year Built")));

        ViewInteraction textView26 = onView(
                allOf(withId(R.id.contents), withText("1990"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lv_general_info),
                                        3),
                                1),
                        isDisplayed()));
        textView26.check(matches(withText("1990")));

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

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.title), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());
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
