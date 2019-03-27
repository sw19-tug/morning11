package com.example.paintu;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testButtonVisible() {
        onView(withId(R.id.bt_tools_chooser)).check(matches(isDisplayed()));
    }

    @Test
    public void bottomSheetVisible() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());

        onView(withId(R.id.bottom_sheet)).check(matches(isDisplayed()));
    }
}
