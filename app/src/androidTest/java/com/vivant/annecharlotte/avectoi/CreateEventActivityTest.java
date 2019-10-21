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
 * Test CreateEventActivity display
 */
public class CreateEventActivityTest {
        @Rule
        public ActivityTestRule<CreateEventActivity> mCreateActivityTestRule = new ActivityTestRule<>(CreateEventActivity.class);

        @Test
        public void listGoesOverTheFold() {
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.create_event))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_theme_title))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_description_title))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_town_title))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_town_expl))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_date))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_numberHero))).check(matches(isDisplayed()));
            onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.event_car))).check(matches(isDisplayed()));

            onView(withId(R.id.event_theme_spinner)).check(matches(isDisplayed()));
            onView(withId(R.id.event_date_open)).check(matches(isDisplayed()));
            onView(withId(R.id.event_car_switch)).check(matches(isDisplayed()));
        }
}
