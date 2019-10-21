package com.vivant.annecharlotte.avectoi;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test MainActivity display
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText(mMainActivityTestRule.getActivity().getResources().getString(R.string.app_name))).check(matches(isDisplayed()));

        onView(withId(R.id.all_events_button)).check(matches(isDisplayed()));
        onView(withId(R.id.add_new_event_button)).check(matches(isDisplayed()));
    }
}
