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
 * Created by Anne-Charlotte Vivant on 21/10/2019.
 */
public class InviteActivityTest {
    @Rule
    public ActivityTestRule<InviteActivity> mInviteActivityTestRule = new ActivityTestRule<>(InviteActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText(mInviteActivityTestRule.getActivity().getResources().getString(R.string.invite_text))).check(matches(isDisplayed()));
        onView(withText(mInviteActivityTestRule.getActivity().getResources().getString(R.string.invite_text2))).check(matches(isDisplayed()));
        onView(withText(mInviteActivityTestRule.getActivity().getResources().getString(R.string.invite_text3))).check(matches(isDisplayed()));
        onView(withText(mInviteActivityTestRule.getActivity().getResources().getString(R.string.invite_btn))).check(matches(isDisplayed()));

        onView(withId(R.id.invite_logo)).check(matches(isDisplayed()));
        onView(withId(R.id.invite_hero_img)).check(matches(isDisplayed()));
    }
}
