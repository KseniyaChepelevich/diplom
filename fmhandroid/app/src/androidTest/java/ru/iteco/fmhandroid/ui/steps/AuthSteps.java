package ru.iteco.fmhandroid.ui.steps;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.data.ViewActions;
import ru.iteco.fmhandroid.ui.page.AuthPageElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

public class AuthSteps {
    //AuthPageElements authPageElements = new AuthPageElements();

    public ViewInteraction loginField =TestUtils.waitView(allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout)))));
    public ViewInteraction passField = TestUtils.waitView(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    public ViewInteraction signBtn = TestUtils.waitView(allOf(withId(R.id.enter_button), withText("SIGN IN"), withContentDescription("Save"), withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))));

    public ViewInteraction emptyToast(int id) {
        return onView(withText(id)).inRoot(new DataHelper.ToastMatcher());
    }

    public void isAuthScreen() {
        /*onView(isRoot()).perform(ViewActions.waitElement(withText("Authorization"), 10000));
        onView(isRoot()).perform(ViewActions.waitElement(withHint("Login"), 10000));
        onView(isRoot()).perform(ViewActions.waitElement(withHint("Password"), 10000));
        onView(isRoot()).perform(ViewActions.waitElement(withText("SIGN IN"), 10000));*/
        TestUtils.waitView(allOf(withText("Authorization"), withParent(withParent(withId(R.id.nav_host_fragment))))).check(matches(isDisplayed()));
        loginField.check(matches(isDisplayed()));
        passField.check(matches(isDisplayed()));
        signBtn.check(matches(isDisplayed()));
    }

    public void authWithValidData (DataHelper.AuthInfo info) {
        isAuthScreen();
        loginField.perform(replaceText(info.getLogin()));
        passField.perform(replaceText(info.getPass()));
        signBtn.check(matches(isClickable()));
        signBtn.perform(click());
    }

    public void authWithInvalidData (DataHelper.AuthInfo invalidInfo) {
        isAuthScreen();
        loginField.perform(replaceText(invalidInfo.getLogin()));
        passField.perform(replaceText(invalidInfo.getPass()));
        signBtn.check(matches(isClickable()));
        signBtn.perform(click());

    }

    public void authWithEmptyFields () {
        isAuthScreen();
        loginField.perform(replaceText(""));
        passField.perform(replaceText(""));
        signBtn.check(matches(isClickable()));
        signBtn.perform(click());

    }

    public void checkToast(int id, boolean visible) {
        if (visible) {
            emptyToast(id).check(matches(isDisplayed()));
        } else {
            emptyToast(id).check(matches(not(isDisplayed())));
        }
    }

}
