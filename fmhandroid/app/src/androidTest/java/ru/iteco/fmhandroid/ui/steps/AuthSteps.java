package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.AuthPage;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;

public class AuthSteps {

    private AuthPageElements authPageElements;

    public void isAuthScreen() {
        authPageElements.screenName.check(matches(isDisplayed()));
        authPageElements.loginField.check(matches(isDisplayed()));
        authPageElements.passField.check(matches(isDisplayed()));
        authPageElements.signBtn.check(matches(isDisplayed()));
    }

    public void authWithValidData (DataHelper.AuthInfo info) {
        isAuthScreen();
        authPageElements.loginField.perform(replaceText(info.getLogin()));
        authPageElements.passField.perform(replaceText(info.getPass()));
        authPageElements.signBtn.check(matches(isClickable()));
        authPageElements.signBtn.perform(click());
    }

}
