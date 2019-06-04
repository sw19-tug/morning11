package com.example.paintu;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.SeekBar;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

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

    @Test
    public void drawingViewVisible() {
        onView(withId(R.id.drawing_view)).check(matches(isDisplayed()));
        assertNotNull(mainActivityTestRule.getActivity().drawingView);
    }

    @Test
    public void bitmapExists() {
        assertNotNull(mainActivityTestRule.getActivity().drawingView.bitmap);
    }

    @Test
    public void canvasExists() {
        assertNotNull(mainActivityTestRule.getActivity().drawingView.canvas);
    }

    @Test
    public void testPointButton() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_point)).perform(click());
        try {
            onView(withId(R.id.drawing_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } catch (AssertionFailedError e) {
            // View not displayed
            assertEquals(mainActivityTestRule.getActivity().drawingView.tool, 1);
        }

    }

    @Test
    public void testLineButton() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_line)).perform(click());
        try {
            onView(withId(R.id.drawing_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } catch (AssertionFailedError e) {
            // View not displayed
            assertEquals(mainActivityTestRule.getActivity().drawingView.tool, 2);

        }

    }

    @Test
    public void testPathButton() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_path)).perform(click());
        try {
            onView(withId(R.id.drawing_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } catch (AssertionFailedError e) {
            // View not displayed
            assertEquals(mainActivityTestRule.getActivity().drawingView.tool, 3);
        }

    }

    @Test
    public void testEraserButton() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_eraser)).perform(click());
        try {
            onView(withId(R.id.drawing_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } catch (AssertionFailedError e) {
            // View not displayed
            assertEquals(mainActivityTestRule.getActivity().drawingView.tool, 5);
        }

    }

    @Test
    public void testDrawPointExists() {
        assertNotNull(mainActivityTestRule.getActivity().drawingView.drawPoint);
    }

    @Test
    public void testDrawLineExists() {
        assertNotNull(mainActivityTestRule.getActivity().drawingView.drawLine);
    }

    @Test
    public void testDrawPathExists() {
        assertNotNull(mainActivityTestRule.getActivity().drawingView.drawPath);
    }

    @Test
    public void testEraserExists() {
        assertNotNull(mainActivityTestRule.getActivity().drawingView.eraser);
    }

    @Test
    public void testColorsVisible() {
        onView(withId(R.id.colors)).check(matches(isDisplayed()));
    }

    @Test
    public void testChosenColor() {
        onView(withId(R.id.color_blue)).perform(click());
        assertEquals(mainActivityTestRule.getActivity().drawingView.getPaintColor(), mainActivityTestRule.getActivity().getResources().getColor(R.color.option_color_blue));
        assertEquals(mainActivityTestRule.getActivity().drawingView.getDrawPaint().getColor(), mainActivityTestRule.getActivity().getResources().getColor(R.color.option_color_blue));
    }

    @Test
    public void testSliderVisible() {
        onView(withId(R.id.stroke)).check(matches(withText("10")));
        onView(withId(R.id.stroke_options)).check(matches(isDisplayed()));
    }

    @Test
    public void testStrokeSlider() {
        onView(withId(R.id.stroke_seekbar)).perform(setProgress(30));
        assertEquals(mainActivityTestRule.getActivity().drawingView.getDrawPaint().getStrokeWidth(), 40, 0.00000001f);
        onView(withId(R.id.stroke)).check(matches(withText("40")));
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }

    // https://stackoverflow.com/questions/26469661/how-to-click-on-android-gallery-with-espresso
    // gallery and camera is hard to test, need mockito to mock camera and gallery
    // simple "if exists" tests
    @Test
    public void testImportFromGallery() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_import_gallery)).check(matches(isDisplayed()));
        //onView(withId(R.id.tool_import_gallery)).perform(click());
        //intending(toPackage("com.android.gallery"));
    }

    //@Test
    public void testImportFromGallery2()
    {
        // Stub the Uri returned by the gallery intent.
        Uri uri = Uri.parse("uri_string");

        // Build a result to return when the activity is launched.
        Intent resultData = new Intent();
        resultData.setData(uri);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to "choose photo" is seen.
        intending(toPackage("com.android.gallery")).respondWith(result);

        onView(withId(R.id.tool_import_gallery)).check(matches(withText("Gallery")));
        onView(withId(R.id.tool_import_gallery)).perform(click());

        // New activity should be launched
        intended(toPackage("com.android.gallery"));
        Intents.assertNoUnverifiedIntents();
    }

    @Test
    public void testImportFromCamera() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_import_camera)).check(matches(isDisplayed()));
        //onView(withId(R.id.tool_import_camera)).perform(click());
        //intending(toPackage("com.android.camera"));
    }

    //@Test
    public void testImportFromCamera2()
    {
        // Stub the Uri returned by the gallery intent.
        Uri uri = Uri.parse("uri_string");

        // Build a result to return when the activity is launched.
        Intent resultData = new Intent();
        resultData.setData(uri);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub result for camera intent.
        intending(toPackage("com.android.camera")).respondWith(result);

        onView(withId(R.id.tool_import_camera)).check(matches(isDisplayed()));
        onView(withId(R.id.tool_import_camera)).perform(click());

        onView(withId(R.id.tool_import_camera)).check(matches(withText("Camera")));
        onView(withId(R.id.tool_import_camera)).perform(click());

        intended(toPackage("com.android.camera"));
        Intents.assertNoUnverifiedIntents();
    }

    @Test
    public void testSaveButtonDisplayed() {
        onView(withId(R.id.save_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveButtonClicked() {
        onView(withId(R.id.save_btn)).perform(click());
        onView(withText("Save drawing to device Gallery?")).check(matches(isDisplayed()));
    }

    @Test
    public void testFilters() {
        onView(withId(R.id.bt_tools_chooser)).perform(click());
        onView(withId(R.id.tool_filters)).check(matches(isDisplayed()));
    }

}
