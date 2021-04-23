package com.example.indoortracking;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class LogInActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginTestFalseEmpty(){
        onView(withId(R.id.email)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
    }
    @Test
    public void loginTestFalseInvalidUserName(){
        onView(withId(R.id.email)).perform(typeText("projectadnin"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
    }
    @Test
    public void loginTestFalseInvalidPassword(){
        onView(withId(R.id.email)).perform(typeText("projectadmin"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("122456"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
    }
    @Test
    public void loginTestTrue(){
        Intents.init();
        onView(withId(R.id.email)).perform(typeText("projectadmin"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        Intents.intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

}