package com.vivant.annecharlotte.avectoi;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Anne-Charlotte Vivant on 14/10/2019.
 */
public class WelcomeActivityTest {
    @Rule
    public ActivityTestRule<WelcomeActivity> mWelcomeActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    // Test tab layout
    @Test
    public void listGoesOverTheFold() {
        onView(withText(mWelcomeActivityTestRule.getActivity().getResources().getString(R.string.new_user))).check(matches(isDisplayed()));
        onView(withText(mWelcomeActivityTestRule.getActivity().getResources().getString(R.string.app_title))).check(matches(isDisplayed()));
        onView(withText(mWelcomeActivityTestRule.getActivity().getResources().getString(R.string.welcome_title_with))).check(matches(isDisplayed()));
        onView(withText(mWelcomeActivityTestRule.getActivity().getResources().getString(R.string.main_activity_description))).check(matches(isDisplayed()));
        onView(withText(mWelcomeActivityTestRule.getActivity().getResources().getString(R.string.connexion))).check(matches(isDisplayed()));
    }
}
