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
 * Created by Anne-Charlotte Vivant on 17/10/2019.
 */
public class CreateUserActivityTest {
    @Rule
    public ActivityTestRule<CreateUserActivity> mCreateActivityTestRule = new ActivityTestRule<>(CreateUserActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.create_phone_intro))).check(matches(isDisplayed()));
        onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.create_community))).check(matches(isDisplayed()));
        onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.create_description_title))).check(matches(isDisplayed()));
        onView(withText(mCreateActivityTestRule.getActivity().getResources().getString(R.string.create_SP))).check(matches(isDisplayed()));
    }
}
