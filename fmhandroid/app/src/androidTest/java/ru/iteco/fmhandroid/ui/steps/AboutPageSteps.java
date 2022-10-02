package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.ui.page.AboutPageElements;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class AboutPageSteps {
    AboutPageElements aboutPageElements = new AboutPageElements();

    public void isAboutPage() {
        aboutPageElements.aboutPageTitle.check(matches(isDisplayed()));
        aboutPageElements.aboutPagePrivacyPolicy.check(matches(isDisplayed()));
        aboutPageElements.aboutPageTermsOfUse.check(matches(isDisplayed()));
        aboutPageElements.aboutCompanyInfo.check(matches(isDisplayed()));

    }

    public void openPrivacyPolicy() {
        aboutPageElements.aboutPrivacyPolicyValueTextView.check(matches(isDisplayed()));
        aboutPageElements.aboutPrivacyPolicyValueTextView.perform(click());
    }

    public void openTermsOfUse() {
        aboutPageElements.aboutTermsOfUseValueTextView.check(matches(isDisplayed()));
        aboutPageElements.aboutTermsOfUseValueTextView.perform(click());
    }
}
