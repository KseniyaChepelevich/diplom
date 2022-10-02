package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.not;

public class AuthSteps {
    AuthPageElements authPageElements = new AuthPageElements();

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

    public void authWithInvalidData (DataHelper.AuthInfo invalidInfo) {
        isAuthScreen();
        authPageElements.loginField.perform(replaceText(invalidInfo.getLogin()));
        authPageElements.passField.perform(replaceText(invalidInfo.getPass()));
        authPageElements.signBtn.check(matches(isClickable()));
        authPageElements.signBtn.perform(click());

    }

    public void authWithEmptyFields () {
        isAuthScreen();
        authPageElements.loginField.perform(replaceText(""));
        authPageElements.passField.perform(replaceText(""));
        authPageElements.signBtn.check(matches(isClickable()));
        authPageElements.signBtn.perform(click());

    }

    public void checkToast(int id, boolean visible) {
        if (visible) {
            authPageElements.emptyToast(id).check(matches(isDisplayed()));
        } else {
            authPageElements.emptyToast(id).check(matches(not(isDisplayed())));
        }
    }

}
