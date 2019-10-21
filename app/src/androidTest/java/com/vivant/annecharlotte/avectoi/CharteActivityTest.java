package com.vivant.annecharlotte.avectoi;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Anne-Charlotte Vivant on 17/10/2019.
 */
public class CharteActivityTest {
    @Rule
    public ActivityTestRule<CharteActivity> mCharteActivityTestRule = new ActivityTestRule<>(CharteActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withId(R.id.charte_btn)).check(matches(isDisplayed()));
    }
}
