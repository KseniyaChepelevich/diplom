package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.page.AboutPageElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

public class AboutPageSteps {
    //AboutPageElements aboutPageElements = new AboutPageElements();
    public Matcher<View> aboutBackImageBut = withId(R.id.about_back_image_button);

    public void isAboutPage() {
        TestUtils.waitView(withText("Version:")).check(matches(isDisplayed()));
        TestUtils.waitView(withText("Privacy Policy:")).check(matches(isDisplayed()));
        TestUtils.waitView(withText("Terms of use:")).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.about_company_info_label_text_view)).check(matches(isDisplayed()));
    }

    public void openPrivacyPolicy() {
        TestUtils.waitView(withId(R.id.about_privacy_policy_value_text_view)).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.about_privacy_policy_value_text_view)).perform(click());
    }

    public void openTermsOfUse() {
        TestUtils.waitView(withId(R.id.about_terms_of_use_value_text_view)).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.about_terms_of_use_value_text_view)).perform(click());
    }
}
