package com.example.paintu;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityEspressoTest {
    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityTestRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void isSharedPrefChanged(){
        boolean oldValue = settingsActivityTestRule.getActivity().getSharedPref().getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);
        onView(withText("Colorblind Mode")).perform(click());
        if(!oldValue)
            assertTrue(settingsActivityTestRule.getActivity().getSharedPref().getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false));
        else
            assertFalse(settingsActivityTestRule.getActivity().getSharedPref().getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false));

    }

}