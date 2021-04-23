package com.example.indoortracking;

import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class DownloadMapPlans {
    private View decorView;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loadDecorView() {
        activityRule.getScenario().onActivity(
                activity -> decorView = activity.getWindow().getDecorView()
        );
    }

    @Test
    public void downloadPlans() throws InterruptedException {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.navigation_floor_plans),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view2),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction help = onView(withId(R.id.floorplan_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, DownloadAction.download(R.id.downloadButton, click())));

        appCompatImageButton.perform(click());
        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.navigation_mapping),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view2),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        onView(withId(R.id.scanButton)).perform(click());

        TimeUnit.SECONDS.sleep(7);

        onView(withId(R.id.scan_text)).check(matches(not(withText(""))));

        for (int i = 0; i < 12; i++) {
            onView(withId(R.id.image_mapping)).perform(click());
            TimeUnit.SECONDS.sleep(2);
        }
        onView(withId(R.id.uploadButton)).perform(click());

        onView(allOf(withId(R.id.navigation_testing), withContentDescription("Testing"),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.nav_view),
                                0),
                        1),
                isDisplayed())).perform(click());

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.fragment_testing)).check(matches(isDisplayed()));

        onView(withId(R.id.scanButton2)).perform(click());

        TimeUnit.SECONDS.sleep(7);

        onView(withId(R.id.text_testing)).check(matches(not(withText(""))));

        onView(withId(R.id.locationButton)).perform(click());

        onView(withId(R.id.image_dot)).check(matches(isDisplayed()));

        TimeUnit.SECONDS.sleep(3);
    }
    @Test
    public void mapFail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.uploadButton)).perform(click());

        onView(withText(R.string.map_first)).inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void mapFailScan() throws InterruptedException {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.navigation_floor_plans),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view2),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction help = onView(withId(R.id.floorplan_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, DownloadAction.download(R.id.downloadButton, click())));

        appCompatImageButton.perform(click());
        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.navigation_mapping),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view2),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        TimeUnit.SECONDS.sleep(2);
        onView(withId(R.id.image_mapping)).perform(click());

        onView(withText(R.string.scan_first)).inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testFailDownload(){
        onView(allOf(withId(R.id.navigation_testing), withContentDescription("Testing"),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.nav_view),
                                0),
                        1),
                isDisplayed())).perform(click());

        onView(withId(R.id.locationButton)).perform(click());

        onView(withText(R.string.download)).inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testFailScan(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.navigation_floor_plans),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view2),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction help = onView(withId(R.id.floorplan_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, DownloadAction.download(R.id.downloadButton, click())));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.navigation_testing),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view2),
                                                0)),
                                2),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        onView(withId(R.id.locationButton)).perform(click());

        onView(withText(R.string.scan_first)).inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
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

    static class DownloadAction {
        public static ViewAction download(int childId, ViewAction viewAction) {
            return new ViewAction() {
                public Matcher<View> getConstraints() {
                    return allOf(isAssignableFrom(TextView.class), isAssignableFrom(Button.class));
                }

                @Override
                public String getDescription() {
                    return "Click Download Button";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    Button button = view.findViewById(R.id.downloadButton);
                    viewAction.perform(uiController, button);
                }
            };
        }
    }
}
