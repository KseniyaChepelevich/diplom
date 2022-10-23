package ru.iteco.fmhandroid.ui.page;

import android.view.View;
import androidx.test.espresso.ViewInteraction;
import org.hamcrest.core.IsInstanceOf;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.data.ViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

public class AuthPageElements {
    public ViewInteraction screenName = TestUtils.onViewWithTimeout(allOf(withText("Authorization"), withParent(withParent(withId(R.id.nav_host_fragment)))));
    //onView(isRoot()).perform(ViewActions.waitElement(allOf(withText("Authorization"), withParent(withParent(withId(R.id.nav_host_fragment)))), 10000));
            //onView(allOf(withText("Authorization"), withParent(withParent(withId(R.id.nav_host_fragment)))));
    public ViewInteraction loginField =TestUtils.onViewWithTimeout(allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout)))));
    //onView(isRoot()).perform(ViewActions.waitElement(withHint("Login"), 10000));
            //onView(allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout)))));
    public ViewInteraction passField = TestUtils.onViewWithTimeout(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    //onView(isRoot()).perform(ViewActions.waitElement(withHint("Password"), 10000));
            //onView(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    public ViewInteraction signBtn = TestUtils.onViewWithTimeout(allOf(withId(R.id.enter_button), withText("SIGN IN"), withContentDescription("Save"), withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))));
    //onView(isRoot()).perform(ViewActions.waitElement(withText("SIGN IN"), 10000));
            //onView(allOf(withId(R.id.enter_button), withText("SIGN IN"), withContentDescription("Save"), withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))));

    /*public ViewInteraction screenName() {
        return onView(isRoot()).perform(ViewActions.waitElement(withText("Authorization"), 10000));
    }

    public ViewInteraction loginField() {
        return onView(isRoot()).perform(ViewActions.waitElement(withHint("Login"), 10000));
    }

    public ViewInteraction passField() {
       return onView(isRoot()).perform(ViewActions.waitElement(withHint("Password"), 10000));

    }

    public ViewInteraction signBtn() {
        return onView(isRoot()).perform(ViewActions.waitElement(withText("SIGN IN"), 10000));
    }*/

    public ViewInteraction emptyToast(int id) {
        return onView(withText(id)).inRoot(new DataHelper.ToastMatcher());
    }
}
